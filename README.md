🛡️ AssetGuardian: Kurumsal Varlık Yönetim Sistemi
AssetGuardian; işletmelerin fiziksel ve dijital varlıklarını, personel üzerindeki zimmet süreçlerini ve varlık taleplerini uçtan uca yönetebilmeleri için geliştirilmiş bir Backend çözümüdür. Sistem, endüstri standardı olan JWT Auth ve kapsamlı bir rol tabanlı erişim kontrolü (RBAC) mimarisi üzerine inşa edilmiştir.

🚀 Teknolojik Altyapı
Çekirdek: Java 21 & Spring Boot 3.5.6

Veri Katmanı: Spring Data JPA & PostgreSQL

Güvenlik: Spring Security & JSON Web Token (JWT)

Dokümantasyon: SpringDoc OpenAPI (Swagger UI)

Konteynırlaştırma: Docker & Docker Compose

Kütüphaneler: Lombok, ModelMapper, JavaFaker

✨ Temel Modüller ve Yetenekler
🔑 Güvenlik ve Yetkilendirme (/api/v1/auth)
Güvenli kullanıcı kaydı ve JWT tabanlı stateless oturum yönetimi.

ADMIN, MANAGER ve USER rolleri ile modüler yetkilendirme katmanı.

📦 Envanter ve Varlık Kontrolü (/api/v1/assets)
Varlıkların tam yaşam döngüsü yönetimi (Ekleme, Güncelleme, Silme).

Yüksek hacimli veriler için Toplu Silme (Batch Delete) desteği.

📝 Talep ve Tahsis Mekanizması (/api/v1/demands & /api/v1/allocation)
Personelin ihtiyaç duyduğu varlıklar için dijital talep oluşturma süreci.

Taleplerin yönetici onayından geçmesi ve onaylı taleplere varlık zimmetleme (Allocation).

Zimmetlenen varlıkların iade takibi ve aktif envanter durumunun izlenmesi.

📜 Zimmet Kayıtları ve İzleme (/api/v1/assignments)
Kayıt Görüntüleme: Sistem üzerindeki tüm zimmetleme geçmişinin ve mevcut atamaların merkezi olarak listelenmesi.

Filtreleme: Yönetici ve müdürler için varlık ismine göre geçmiş kayıtlar içinde arama yapabilme imkanı.

Yetkili Erişimi: Hassas zimmet verilerine sadece ADMIN ve MANAGER rollerine sahip kullanıcıların erişebilmesi.

⚙️ Kullanıcı ve Sistem Ayarları (/api/v1/settings)
Kullanıcı profil yönetimi ve güvenli şifre güncelleme mekanizması.

Profil Yönetimi: Kullanıcılar için Multipart File desteğiyle profil fotoğrafı yükleme altyapısı.

Admin Paneli: Kullanıcı rollerinin sistem üzerinden dinamik olarak güncellenmesi.

📦 Kurulum ve Çalıştırma
Projeyi Paketleyin:

Bash
./mvnw clean package
Sistemi Başlatın:

Bash
docker-compose up --build
📖 API Kullanımı
Sistemin sunduğu tüm endpoint'ler Swagger üzerinden interaktif olarak incelenebilir:

🔗 Swagger UI: http://localhost:8080/swagger-ui/index.html

🛠️ Teknik Mimari Notları
Veri Güvenliği: API uçları, PreAuthorize anotasyonları ile metot seviyesinde korunmaktadır.

Validasyon: @Validated ve @Positive gibi anotasyonlarla veri tutarlılığı giriş katmanında sağlanır.

Performans: DTO yapısı sayesinde gereksiz veri transferi önlenir.
