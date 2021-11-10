package wiitteri;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;

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
        byte[] image = FileCopyUtils.copyToByteArray(inputStream);
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
        t1 = tweetService.addLike(t1, maija);
        t1 = tweetService.addLike(t1, justiina);
        t2 = tweetService.addLike(t2, justiina);
        t2 = tweetService.addLike(t2, uolevi);
        t3 = tweetService.addLike(t3, kaaleppi);
        t3 = tweetService.addLike(t3, maija);
        t4 = tweetService.addLike(t4, uolevi);
        t4 = tweetService.addLike(t4, maija);
        t5 = tweetService.addLike(t5, kaaleppi);
        t5 = tweetService.addLike(t5, uolevi);
        t6 = tweetService.addLike(t6, maija);
        t6 = tweetService.addLike(t6, uolevi);
        t7 = tweetService.addLike(t7, maija);
        t7 = tweetService.addLike(t7, kaaleppi);
        t8 = tweetService.addLike(t8, uolevi);
        t8 = tweetService.addLike(t8, maija);

        // Like images
        i1 = imageService.addLike(i1, uolevi);
        i1 = imageService.addLike(i1, maija);
        i2 = imageService.addLike(i2, justiina);
        i2 = imageService.addLike(i2, uolevi);
        i3 = imageService.addLike(i3, maija);
        i3 = imageService.addLike(i3, kaaleppi);
        i4 = imageService.addLike(i4, kaaleppi);
        i4 = imageService.addLike(i4, maija);
        i5 = imageService.addLike(i5, justiina);
        i5 = imageService.addLike(i5, uolevi);
        i6 = imageService.addLike(i6, kaaleppi);
        i6 = imageService.addLike(i6, maija);
        i7 = imageService.addLike(i7, uolevi);
        i7 = imageService.addLike(i7, justiina);
        i8 = imageService.addLike(i8, justiina);
        i8 = imageService.addLike(i8, maija);
        i9 = imageService.addLike(i9, justiina);
        i9 = imageService.addLike(i9, uolevi);
        i10 = imageService.addLike(i10, kaaleppi);
        i10 = imageService.addLike(i10, justiina);

        // 7. Kommentointi
        // Comment tweets
        t1 = tweetService.addComment(t1, maija, "Hienoa, tervetuloa takaisin Uolevi!");
        t1 = tweetService.addComment(t1, justiina, "Morjens Uolevi");
        t2 = tweetService.addComment(t2, uolevi, "Etpä paljoa mittään!");
        t2 = tweetService.addComment(t2, kaaleppi, "Minä kerroin juuri elämän tarkoituksen");
        t3 = tweetService.addComment(t3, uolevi, "Tämä on tämmönen teko twitteri");
        t3 = tweetService.addComment(t3, maija, "Todella hyvin pelaa ja nopea, ei mainoksia");
        t4 = tweetService.addComment(t4, maija, "Sitten kohti seuraavaa palvelua :)");
        t4 = tweetService.addComment(t4, uolevi, "Minusta tämä on ihan kiva");
        t5 = tweetService.addComment(t5, kaaleppi, "Hienoa, entä mistä nimi Kaaleppi tulee?");
        t5 = tweetService.addComment(t5, kaaleppi, "Minä googlettelin sen jo itse...");
        t6 = tweetService.addComment(t6, uolevi, "Nähtävästi tiesit itsekin ;)");
        t6 = tweetService.addComment(t6, kaaleppi, "Jep jep...");
        t7 = tweetService.addComment(t7, maija, "Mitäs minä tässä yksikseni höpisen");
        t7 = tweetService.addComment(t7, maija, "Mökkihöperyyttä??");
        t8 = tweetService.addComment(t8, kaaleppi, "Pekka Puupää on hieno hahmo");
        t8 = tweetService.addComment(t8, uolevi, "Vaimo vielä hienompi");

        // Comment images
        i1 = imageService.addComment(i1, maija, "Hieno profiilikuva!");
        i1 = imageService.addComment(i1, justiina, "Ihqua!");
        i2 = imageService.addComment(i2, justiina, "Repesin, olet sinä kyllä outo!");
        i2 = imageService.addComment(i2, kaaleppi, "Onko Uolevi tuo lintu?");
        i3 = imageService.addComment(i3, kaaleppi, "Se taitaa kuule olla se keihäsmatkojen mies!");
        i3 = imageService.addComment(i3, kaaleppi, "Joo, se se on.");
        i4 = imageService.addComment(i4, uolevi, "Paljon kanssa aina pärjää");
        i4 = imageService.addComment(i4, maija, "Lomaparoni");
        i5 = imageService.addComment(i5, justiina, ":heart:");
        i5 = imageService.addComment(i5, maija, "thx");
        i6 = imageService.addComment(i6, uolevi, "Ihan hyvä idea mutta mahtuuko tuo kiuas tuohon ...");
        i6 = imageService.addComment(i6, maija, "Kyllä sen pitäisi, näyttää vähän kuvassa vaan hassulta");
        i7 = imageService.addComment(i7, maija, "Siinäpä herrasmiehiä!");
        i7 = imageService.addComment(i7, uolevi, "Olenko minä tuossa oikealla");
        i7 = imageService.addComment(i7, kaaleppi, "@SuperUolevi et kyllä ole");
        i8 = imageService.addComment(i8, uolevi, "Hieno logo, mistä pöllit?");
        i8 = imageService.addComment(i8, kaaleppi, "En muista!");
        i9 = imageService.addComment(i9, kaaleppi, "Enpä alkaisi sinulle ryttyilemään!");
        i9 = imageService.addComment(i9, uolevi, "Pelottaa ... :/");
        i10 = imageService.addComment(i10, uolevi, "No ethän sinä ole tuo sama ihminen ollenkaan!");
        i10 = imageService.addComment(i10, maija, "Mites meni bb:ssä?");
    }

}
