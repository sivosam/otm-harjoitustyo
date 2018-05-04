# Arkkitehtuurikuvaus

## Rakenne

Ohjelman pakkausrakenne:

<img src="https://github.com/sivosam/otm-harjoitustyo/blob/master/ReadingList/dokumentaatio/kuvat/pakkausrakenne.png" width="320">

Pakkausessa _readinglist.ui_ on sovelluksen käyttöliittymä, _readinglist.domain_ sisältää sovelluslogiikan ja _readinglist.database_ sisältää tietokantatoiminnallisuuden.

### Sovelluksen luokkakaavio
<img src="https://github.com/sivosam/otm-harjoitustyo/blob/master/ReadingList/dokumentaatio/kuvat/luokkakaavio.jpg" width="750">

## Käyttöliittymä

Käyttöliittymässä on yksi näkymä, josta löytyy:
- kentät uuden tekstin lisäämiseksi
- lista luettavana olevista teksteistä, joka sisältää tekstien
  - Nimen
  - Sivumäärän
  - Päivämäärän, johon mennessä tekstin tulee olla luettuna (eli tekstin deadlinen)
  - Napin poistamista varten

Lukulista päivittyy automaattisesti, kun uusi teksti lisätään, tai jos olemassaolevaa tekstiä muokataan.

 ### Tekstien muokkaaminen
 
Teksin nimeä, sivumäärää, sekä deadlinea voi muokata tuplaklikkaamalla haluttua elementtiä ja painamalla enteriä muokkauksen jälkeen. Sovellus ilmoittaa mahdollisista virhesyötöistä, eikä tällöin hyväksy muokkausta.


## Sovelluslogiikka

Sovelluslogiikasta vastaava luokka _BookService_ tarjoaa toiminnallisuuden tekstien lisäämiseen, muokkaamiseen ja poistamiseen. Havaitessaan virhesyötön, palauttaa luokka virheviestin käyttöliittymälle, eikä tallenna lisättyä/muokattua tekstiä. 

Esimerkiksi sivumäärän tulee olla muodossa x - y. Käyttäjän muokattaessa olemassaolevan tekstin sivumäärää virheellisesti, palauttaa BookServicen metodi _updateBookPages()_ virheviestin "Sivujen täytyy olla muodossa x - y". 

Muita luokan tarjoamia metodeita ovat mm. 
- _saveBook()_
- _deleteBook()_
- _updateBookName()_
- jne.

_BookService_ on yhteydessä konstruktorin perusteella määriteltyyn tietokantatiedostoon luokan _ReadingListDao_ kautta. 

Sovelluksen luokka/pakkauskaavio:

<img src="https://github.com/sivosam/otm-harjoitustyo/blob/master/ReadingList/dokumentaatio/kuvat/luokka:pakkauskaavio.png" width="450">

## Tietojen pysyväistallennus

Pakkauksessa _readinglist.database_ sijaitsevat luokat _ReadingListDao_ ja _Database_ ovat vastuussa tekstien pysyväistallennuksesta. 

Luokka _Database_ joko luo oletustietokantatiedoston _readinglist.db_ hakemistoon, jossa sovellus sijaitsee, tai käyttää konstruktorissa tarjottua tiedostoa. Luokan metodi _init()_ lisää muutaman esimerkkitekstin tietokantaan ensimmäisellä käynnistyskerralla, tai jos oletustietokantatiedosto _readinglist.db_ on poistettu. Sinänsä ainoa hyöty olla käyttämättä oletustietokantaa on sovelluksen testaaminen, jolloin käytössä on testitietokanta _test.db_. 

### Tietokanta

Sovellus luo käynnistyshakemistoon tietokannan nimeltä _readinglist.db_. Tietokannassa on yksi taulu, jonka schema on seuraavanlainen:

```
CREATE TABLE Book (
id Integer PRIMARY KEY,
name varchar(255),
pages varchar(20),
deadline varchar(10));
```


### Päätoiminnallisuudet


#### Uuden tekstin lisääminen

Sovelluksen vasemmalta puolelta löytyy kentät uuden tekstin lisäämiseksi. Sovellus vaatii teksteille nimeä, sivumäärää, sekä deadlinea. Virheellisistä syötöistä annetaan asianmukainen virheviesti, eikä uutta kirjaa tallenneta. 

Virheettömää tekstiä lisätessä ja painamalla nappia _addBookButton_ etenee sovellus seuraavasti:

<img src="https://github.com/sivosam/otm-harjoitustyo/blob/master/ReadingList/dokumentaatio/kuvat/uuden%20kirjan%20sekvenssikaavio.png" width="750">

Käyttöliittymän nappi siis kutsuu luokan _BookService_ metodia _saveBook()_. _BookService_ luo uuden Book-olion ja kutsuu _ReadingListDao_:n metodia _save()_. Kaiken onnistuessa ongelmitta _BookService_ palauttaa käyttöliittymälle tyhjän String:in, joka tarkoittaa, ettei virheitä esiintynyt. Tämän jälkeen käyttöliittymä kutsuu omaa metodiaan _redrawListView()_, joka listaa lukulistan alkiot uudestaan.

## Ohjelman rakenteeseen jääneet heikkoudet

### Book-olio

Book-olion muuttujat ovat ID:tä lukuunottamatta tallennettu String-muodossa. Tämä tekee listan muokkaamisesta helppoa, mutta hankaloittaa esimerkiksi listan järjestämistä sivumäärän tai deadlinen mukaan. Tästä johtuen toiminnallisuutta listan uudelleenjärjestämiseen ei ole toteutettu.
