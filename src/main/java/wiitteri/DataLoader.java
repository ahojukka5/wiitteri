package wiitteri;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import wiitteri.models.Account;
import wiitteri.models.Connection;
import wiitteri.models.Image;
import wiitteri.models.Tweet;
import wiitteri.services.AccountService;
import wiitteri.services.ConnectionService;
import wiitteri.services.ImageService;
import wiitteri.services.SearchService;
import wiitteri.services.TweetService;

@Component
public class DataLoader implements ApplicationRunner {

    @Autowired
    private AccountService accountService;

    @Autowired
    private SearchService searchService;

    @Autowired
    private ConnectionService connectionService;

    @Autowired
    private ImageService imageService;

    @Autowired
    private TweetService tweetService;

    private Image addImage(Account account, String filename, String description) throws IOException {
        Resource resource = new ClassPathResource(filename);
        InputStream inputStream = resource.getInputStream();
        byte[] image = inputStream.readAllBytes();
        return imageService.addImage(account, image, description);
    }

    @Override
    public void run(ApplicationArguments args) throws IOException {
        // 1. Käyttäjien rekisteröinti
        // Create couple of users
        Account uolevi = accountService.createAccount("Uolevi Ulitamokara", "uolevi", "uolevi", "SuperUolevi");
        Account maija = accountService.createAccount("Maija Mehiläinen", "maija", "maija", "MMehiläinen");
        Account kaaleppi = accountService.createAccount("Kaaleppi Kolro", "kaaleppi", "kaaleppi", "Kolro");
        Account justiina = accountService.createAccount("Justiina Josera", "justiina", "justiina", "Justis");

        // 2. Käyttäjien etsiminen ja seuraaminen
        // Create connections (first one with search feature)
        Account uolevi2 = searchService.searchByName("uolevi").get(0);
        Connection c1 = connectionService.connect(uolevi2, justiina);
        Connection c2 = connectionService.connect(justiina, uolevi);
        Connection c3 = connectionService.connect(maija, justiina);
        Connection c4 = connectionService.connect(maija, uolevi);
        Connection c5 = connectionService.connect(kaaleppi, maija);
        Connection c6 = connectionService.connect(maija, kaaleppi);
        Connection c7 = connectionService.connect(justiina, maija);

        // Maija has been blocking uolevi (c4)
        connectionService.block(c4);

        // 3. Kuva-albumi
        // Add some photos to users
        Image i1 = addImage(uolevi, "images/uolevi.jpg", "Uolevin profiilikuva");
        Image i2 = addImage(uolevi, "images/uolevi2.jpg", "Outo Uolevi!");
        Image i3 = addImage(uolevi, "images/uolevi3.jpg", "Kuka tämä on?");
        Image i4 = addImage(uolevi, "images/uolevi4.jpg", "Sama mies!");
        Image i5 = addImage(maija, "images/maija.jpg", "Profiilikuvani");
        Image i6 = addImage(maija, "images/maija2.jpg", "Ajattelin rakentaa tällaisen saunan.");
        Image i7 = addImage(kaaleppi, "images/kaaleppi.jpg", "Minä ja pojat");
        Image i8 = addImage(kaaleppi, "images/kaaleppi2.jpg", "Karvattomat kissani");
        Image i9 = addImage(justiina, "images/justiina.jpg", "Profiilikuvani, seis tai lätty lätisee!");
        Image i10 = addImage(justiina, "images/justiina2.jpg", "Hieman uudempi kuva.");

        // 4. Profiilikuva
        // Uolevi, Maija and Justiina are having profile pictures, Kaaleppi doesn't.
        accountService.setProfileImage(uolevi, i1);
        accountService.setProfileImage(maija, i5);
        accountService.setProfileImage(justiina, i9);

        // 5. Henkilökohtainen etusivu
        // Add some "tweets" to personal pages
        Tweet t1 = tweetService.createTweet(uolevi, "Uolevi is back!");
        Tweet t2 = tweetService.createTweet(maija, "Hellou, mitä missasin?");
        Tweet t3 = tweetService.createTweet(kaaleppi, "Mikäs se tämä tämmönen palvelu oikein on?");
        Tweet t4 = tweetService.createTweet(justiina, "Onpas höpöhöpö appsi");
        Tweet t5 = tweetService.createTweet(uolevi,
                "Uolevi on peräisin Olavi-nimen ruotsinkielisistä muodoista, joissa o-vokaali on äännetty pitkäksi (esimerkiksi Ole) joko uu:na tai uo:na.");
        Tweet t6 = tweetService.createTweet(kaaleppi, "Kaaleppi on Suomessa miehen useimmiten toinen tai kolmas nimi.");
        Tweet t7 = tweetService.createTweet(maija,
                "Maija Mehiläinen (Die Biene Maja) on Waldemar Bonselsin vuonna 1912 julkaisema faabeli, joka kertoo Maija-nimisen mehiläisen kasvutarinan.");
        Tweet t8 = tweetService.createTweet(justiina,
                "Justiina Puupää on Ola Fogelbergin luoma sarjakuvahahmo, Pekka Puupään vaimo");

        // 6. Tykkääminen

        // Like tweets
        tweetService.addLike(t1, maija);
        tweetService.addLike(t2, justiina);
        tweetService.addLike(t3, kaaleppi);
        tweetService.addLike(t4, uolevi);
        tweetService.addLike(t5, kaaleppi);
        tweetService.addLike(t6, maija);
        tweetService.addLike(t7, maija);
        tweetService.addLike(t8, uolevi);

        // Like images
        imageService.addLike(i1, uolevi);
        imageService.addLike(i2, justiina);
        imageService.addLike(i3, maija);
        imageService.addLike(i4, kaaleppi);
        imageService.addLike(i5, justiina);
        imageService.addLike(i6, kaaleppi);
        imageService.addLike(i7, uolevi);
        imageService.addLike(i8, justiina);
        imageService.addLike(i9, justiina);
        imageService.addLike(i10, kaaleppi);

    }

}
