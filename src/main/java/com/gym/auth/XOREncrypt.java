package com.gym.auth;

import java.util.HashSet;
import java.util.Set;

public class XOREncrypt {

    public static void main(String[] args) {
        int userNo = 999890;
        int key = 2_000_123_456; // 20억

        System.out.println("=== 20억 키로 XOR 테스트 ===");
        System.out.printf("userNo: %,d\n", userNo);
        System.out.printf("key:    %,d (0x%08X)\n", key, key);
        // XOR 암호화
        int encrypted = encUserNo(userNo, key);
        System.out.printf("암호화: %,d (0x%08X)\n", encrypted, encrypted);
        // XOR 복호화
        int decrypted = decUserNo(key, encrypted);

        System.out.printf("복호화: %,d\n", decrypted);
        // 검증
        System.out.printf("원본과 같은가? %s\n", (userNo == decrypted));
        System.out.println("\n=== 32비트 이진 표현 ===");
        System.out.printf("userNo:   %s\n", toBinary32(userNo));
        System.out.printf("key:      %s\n", toBinary32(key));
        System.out.printf("encrypted:%s\n", toBinary32(encrypted));
        System.out.printf("decrypted:%s\n", toBinary32(decrypted));
        // 다양한 값들로 고유성 테스트
        System.out.println("\n=== 고유성 테스트 ===");
        testUniqueness(key);
        // 극한값 테스트
        System.out.println("\n=== 극한값 테스트 ===");
        testExtremeValues(key);
    }
    private static int decUserNo(int key, int encrypted) {
        int decrypted = encrypted ^ key;
        return decrypted;
    }
    private static int encUserNo(int userNo, int key) {
        int encrypted = userNo ^ key;
        return encrypted;
    }
    public static void testUniqueness(int key) {
        int[] testValues = {
                0, 1, 100, 999890, 1_000_000,
                Integer.MAX_VALUE, Integer.MIN_VALUE,
                -1, -100, -999890
        };
        Set<Integer> encryptedResults = new HashSet<>();
        boolean hasCollision = false;
        System.out.println("원본값 -> 암호화 결과");
        for (int value : testValues) {
            int encrypted = value ^ key;
            if (!encryptedResults.add(encrypted)) {
                hasCollision = true;
                System.out.printf("*** 충돌 발견! %,d\n", value);
            }
            System.out.printf("%,12d -> %,12d (0x%08X)\n",
                    value, encrypted, encrypted);
        }
        System.out.printf("충돌 발생? %s\n", hasCollision);
        System.out.printf("고유한 결과 개수: %d / %d\n",
                encryptedResults.size(), testValues.length);
    }
    public static void testExtremeValues(int key) {
        // 최대/최소값 경계 테스트
        int[] extremes = {
                Integer.MAX_VALUE,     // 2,147,483,647
                Integer.MAX_VALUE - 1, // 2,147,483,646
                Integer.MIN_VALUE,     // -2,147,483,648
                Integer.MIN_VALUE + 1, // -2,147,483,647
                0x7FFFFFFF,           // MAX_VALUE와 같음
                0x80000000,           // MIN_VALUE와 같음 (int로 캐스팅되면)
                -1,                   // 0xFFFFFFFF
                -2                    // 0xFFFFFFFE
        };
        System.out.println("극한값 -> 암호화 -> 복호화");
        for (int value : extremes) {
            int encrypted = value ^ key;
            int decrypted = encrypted ^ key;
            System.out.printf("%,12d -> %,12d -> %,12d (OK: %s)\n",
                    value, encrypted, decrypted, (value == decrypted));
        }
    }
    // 32비트 이진 표현 헬퍼
    public static String toBinary32(int value) {
        return String.format("%32s", Integer.toBinaryString(value)).replace(' ', '0');
    }

}
