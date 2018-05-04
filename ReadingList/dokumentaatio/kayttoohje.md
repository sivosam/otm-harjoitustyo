# Käyttöohje

Lataa itse ohjelma [ReadingList.jar](https://github.com/sivosam/otm-harjoitustyo/releases/tag/viikko6)

## Ohjelman käynnistys

Ohjelman voi käynnistää tuplaklikkaamalla tiedostoa _ReadingList.jar_ tai komennolla 

```
java -jar readinglist.jar
```

Ohjelma tarvittaessa luo tietokantatiedoston _readinglist.db_ ja käynnistyy ikkunaan, jonka vasemmalla puolella löytyy toiminnallisuus uuden tekstin lisäämiseen ja oikealla itse lukulista alkioineen ja poistonappeineen. 

Ensimmäisellä käynnistyskerralla ohjelma asettaa muutaman esimerkkitekstin listalle.

<img src="https://github.com/sivosam/otm-harjoitustyo/blob/master/ReadingList/dokumentaatio/kuvat/kaytto1.png" width="600">

## Uuden tekstin lisääminen

Uuden tekstin voi lisätä vasemmalta puolelta. Kaikkien kohtien tulee olla asiallisesti täytettynä, jonka jälkeen "Lisää"- nappia painamalla teksti tallentuu tietokantaan ja näkyy listassa. Ohjelma ilmoittaa mahdollisista virheellisistä syötöistä. 

<img src="https://github.com/sivosam/otm-harjoitustyo/blob/master/ReadingList/dokumentaatio/kuvat/kaytto2.png" width="400">

## Tekstin muokkaaminen

Kaikkia listan tekstien arvoja pystyy muokkaamaan tuplaklikkaamalla haluttua kenttää, syöttämällä uuden arvon ja painamalla enter-näppäintä.

<img src="https://github.com/sivosam/otm-harjoitustyo/blob/master/ReadingList/dokumentaatio/kuvat/kaytto3.png" width="600">

 Tässäkin tilanteessa ohjelma ilmoittaa mahdollisista virheellisistä arvoista. 
 
 <img src="https://github.com/sivosam/otm-harjoitustyo/blob/master/ReadingList/dokumentaatio/kuvat/kaytto4.png" width="600">
 
 ## Tekstin poistaminen
 
 Tekstin pystyy poistamaan listalta painamalla teksin oikealla puolella olevaa X-nappia. Tällöin tekstin poistuu näkyvistä listalta ja poistuu myös tietokannasta.
