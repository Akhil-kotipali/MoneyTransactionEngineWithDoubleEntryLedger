package com.akhil.paymoneytoday.service;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.Optional;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class RedisService {

    private final StringRedisTemplate redisTemplate;

    public RedisService(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void setBalance(Long walletId, BigDecimal balance) {
        try {
            if (walletId != null && balance != null) {
                redisTemplate.opsForValue().set("wallet:balance:" + walletId, balance.toPlainString(), Duration.ofMinutes(30));
            }
        } catch (Exception ignored) {
        }
    }

    public Optional<BigDecimal> getBalance(Long walletId) {
        try {
            if (walletId == null) {
                return Optional.empty();
            }
            String val = redisTemplate.opsForValue().get("wallet:balance:" + walletId);
            if (val != null) {
                return Optional.of(new BigDecimal(val));
            }
        } catch (Exception ignored) {
        }
        return Optional.empty();
    }

    public void evictBalance(Long walletId) {
        try {
            if (walletId != null) {
                redisTemplate.delete("wallet:balance:" + walletId);
            }
        } catch (Exception ignored) {
        }
    }

    public boolean checkAndAcquireIdempotency(String idempotencyKey) {
        try {
            if (idempotencyKey == null || idempotencyKey.isBlank()) {
                return true;
            }
            Boolean success = redisTemplate.opsForValue().setIfAbsent("idempotency:" + idempotencyKey.trim(), "ACQUIRED", Duration.ofHours(24));
            return Boolean.TRUE.equals(success);
        } catch (Exception ignored) {
            return true;
        }
    }

    public boolean ping() {
        try {
            String res = redisTemplate.getConnectionFactory().getConnection().ping();
            return "PONG".equalsIgnoreCase(res);
        } catch (Exception e) {
            return false;
        }
    }
}
