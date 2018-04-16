# OTM Harjoitustyö – Reading List

Tämä on *kurssin* **Ohjelmistotekniikan menetelmät** harjoitustyö. 

Kyseessä on ohjelma, jonka avulla on helppo pitää kirjaa luettavista kirjoista, artikkeleista jne., sekä niiden sivumääristä ja deadlineistä. 

## Dokumentaatio

[Vaatimusmäärittely](https://github.com/sivosam/otm-harjoitustyo/blob/master/ReadingList/dokumentaatio/vaatimusmaarittely.md)

[Tuntikirjanpito](https://github.com/sivosam/otm-harjoitustyo/blob/master/ReadingList/dokumentaatio/tuntikirjanpito.md)

[Arkkitehtuuri](https://github.com/sivosam/otm-harjoitustyo/blob/master/ReadingList/dokumentaatio/arkkitehtuuri.md)


## Komentorivitoiminnot

### Testaus

Testit suoritetaan komennolla

```
mvn test
```

Testikattavuusraportti luodaan komennolla

```
mvn jacoco:report
```

Kattavuusraporttia voi tarkastella avaamalla selaimella tiedoston _target/site/jacoco/index.html_

### Suoritettavan jarin generointi

Komento

```
mvn package
```

generoi hakemistoon _target_ suoritettavan jar-tiedoston _ReadingList-1.0-SNAPSHOT.jar_. Ensimmäisellä käynnistyskerralla luo samaan hakemistoon tiedoston readinglist.db.

### Checkstyle

Tiedostoon [checkstyle.xml](https://github.com/sivosam/otm-harjoitustyo/blob/master/ReadingList/checkstyle.xml) määrittelemät tarkistukset suoritetaan komennolla

```
 mvn jxr:jxr checkstyle:checkstyle
```

Mahdolliset virheilmoitukset selviävät avaamalla selaimella tiedosto _target/site/checkstyle.html_
