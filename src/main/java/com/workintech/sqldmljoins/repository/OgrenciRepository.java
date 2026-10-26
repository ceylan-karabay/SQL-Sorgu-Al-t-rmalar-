package com.workintech.sqldmljoins.repository;

import com.workintech.sqldmljoins.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
public interface OgrenciRepository extends JpaRepository<Ogrenci, Long> {

    // Kitap alan öğrenciler
    String QUESTION_2 =
            "SELECT * FROM ogrenci WHERE ogrno IN (SELECT ogrno FROM islem)";

    @Query(value = QUESTION_2, nativeQuery = true)
    List<Ogrenci> findStudentsWithBook();


    // Kitap almayan öğrenciler
    String QUESTION_3 = "SELECT * FROM ogrenci " +
            "WHERE ogrno NOT IN (SELECT ogrno FROM islem)";

    @Query(value = QUESTION_3, nativeQuery = true)
    List<Ogrenci> findStudentsWithNoBook();


    // 10A veya 10B sınıfındaki öğrencilerin sınıfı ve okuduğu kitap sayısı
    String QUESTION_4 = "SELECT o.sinif AS sinif, " +
            "COUNT(i.islemno) AS count " +
            "FROM ogrenci o " +
            "LEFT JOIN islem i ON o.ogrno = i.ogrno " +
            "WHERE o.sinif IN ('10A', '10B') " +
            "GROUP BY o.sinif";

    @Query(value = QUESTION_4, nativeQuery = true)
    List<KitapCount> findClassesWithBookCount();

    // 10A veya 10B sınıflarındaki öğrenci sayısı
    String QUESTION_5 = "SELECT COUNT(*) FROM ogrenci";

    @Query(value = QUESTION_5, nativeQuery = true)
    int findStudentCount();


    // Farklı öğrenci isimlerinin sayısı
    String QUESTION_6 = "SELECT COUNT(DISTINCT ad) FROM ogrenci";

    @Query(value = QUESTION_6, nativeQuery = true)
    Integer findUniqueStudentNameCount();


    // İsme göre öğrenci sayısı
    String QUESTION_7 = "SELECT ad, COUNT(*) AS count " +
            "FROM ogrenci " +
            "GROUP BY ad";

    @Query(value = QUESTION_7, nativeQuery = true)
    List<StudentNameCount> findStudentNameCount();


    // Her sınıftaki öğrenci sayısı
    String QUESTION_8 = "SELECT sinif, COUNT(*) AS count " +
            "FROM ogrenci " +
            "GROUP BY sinif";

    @Query(value = QUESTION_8, nativeQuery = true)
    List<StudentClassCount> findStudentClassCount();


    // Her öğrencinin ad-soyad ve okuduğu kitap sayısı
    String QUESTION_9 =
            "SELECT o.ad AS ad, o.soyad AS soyad, " +
                    "COUNT(i.islemno) AS count " +
                    "FROM ogrenci o " +
                    "INNER JOIN islem i ON o.ogrno = i.ogrno " +
                    "GROUP BY o.ogrno, o.ad, o.soyad " +
                    "ORDER BY o.ad";

    @Query(value = QUESTION_9, nativeQuery = true)
    List<StudentNameSurnameCount> findStudentNameSurnameCount();
}