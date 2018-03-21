package com.mycompany.unicafe;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class KassapaateTest {

    Kassapaate k;
    Maksukortti m;

    @Before
    public void setUp() {
        k = new Kassapaate();
        m = new Maksukortti(1000);
    }

    @Test
    public void uudenPaatteenRahatOikein() {
        assertEquals(k.kassassaRahaa(), 100000);
    }

    @Test
    public void uudenPaatteenMyynnitOikein() {
        assertEquals(k.edullisiaLounaitaMyyty(), 0);
        assertEquals(k.maukkaitaLounaitaMyyty(), 0);
    }

    @Test
    public void edullinenKateisostoToimii() {
        assertEquals(k.syoEdullisesti(500), 260);
        assertEquals(k.kassassaRahaa(), 100240);
    }

    @Test
    public void maukasKateisostoToimii() {
        assertEquals(k.syoMaukkaasti(500), 100);
        assertEquals(k.kassassaRahaa(), 100400);
    }

    @Test
    public void edullinenPalauttaaKateisetJosEiRiita() {
        assertEquals(k.syoEdullisesti(100), 100);
    }

    @Test
    public void maukasPalauttaaKateisetJosEiRiita() {
        assertEquals(k.syoMaukkaasti(100), 100);
    }

    @Test
    public void myytyjenEdullistenMaaraKasvaaOikein() {
        k.syoEdullisesti(500);
        assertEquals(k.edullisiaLounaitaMyyty(), 1);
    }

    @Test
    public void myytyjenMaukkaidenMaaraKasvaaOikein() {
        k.syoMaukkaasti(500);
        assertEquals(k.maukkaitaLounaitaMyyty(), 1);
    }

    @Test
    public void kassaEiMuutuJosRahatEiRiita() {
        k.syoEdullisesti(100);
        k.syoMaukkaasti(100);
        assertEquals(k.kassassaRahaa(), 100000);
    }

    @Test
    public void myydytLounaatEiMuutuJosRahatEiRiita() {
        k.syoEdullisesti(100);
        k.syoMaukkaasti(100);
        assertEquals(k.maukkaitaLounaitaMyyty(), 0);
        assertEquals(k.edullisiaLounaitaMyyty(), 0);
    }

    @Test
    public void korttiOstoEdullinenPalauttaaTrue() {
        assertTrue(k.syoEdullisesti(m));
    }

    @Test
    public void korttiOstoMaukasPalauttaaTrue() {
        assertTrue(k.syoMaukkaasti(m));
    }
    
    @Test
    public void edullinenVeloitetaanKortilta() {
        k.syoEdullisesti(m);
        assertEquals(m.saldo(), 1000-240);
    }

    @Test
    public void maukasVeloitetaanKortilta() {
        k.syoMaukkaasti(m);
        assertEquals(m.saldo(), 1000-400);
    }
    
    @Test
    public void korttiMaksuKasvattaaMyynteja() {
        k.syoEdullisesti(m);
        k.syoMaukkaasti(m);
        assertEquals(k.maukkaitaLounaitaMyyty(), 1);
        assertEquals(k.edullisiaLounaitaMyyty(), 1);
    }
    
    @Test
    public void josKortillaEiTarpeeksiRahaaPalautetaanFalse() {
        m.otaRahaa(900);
        assertFalse(k.syoEdullisesti(m));
        assertFalse(k.syoMaukkaasti(m));
    }
    
    @Test
    public void josKortillaEiTarpeeksiRahaaSaldoEiMuutu() {
        m.otaRahaa(900);
        k.syoEdullisesti(m);
        assertEquals(m.saldo(), 100);
        
        k.syoMaukkaasti(m);
        assertEquals(m.saldo(), 100);
    }
    
    @Test
    public void josKortillaEiTarpeeksiRahaaMyynnitEiMuutu() {
        m.otaRahaa(900);
        
        k.syoEdullisesti(m);
        k.syoMaukkaasti(m);
        
        assertEquals(k.edullisiaLounaitaMyyty(), 0);
        assertEquals(k.maukkaitaLounaitaMyyty(), 0);
    }
    
    @Test
    public void kassaEiMuutuKortillaOstaessa() {
        k.syoEdullisesti(m);
        
        assertEquals(k.kassassaRahaa(), 100000);
    }

    @Test
    public void kortilleVoiLadataArvoa() {
        k.lataaRahaaKortille(m, 1);
        assertEquals(m.saldo(), 1001);
        assertEquals(k.kassassaRahaa(), 100001);
    }
    
    @Test
    public void kortilleEiVoiLadataNegatiivistaArvoa() {
        k.lataaRahaaKortille(m, -1);
        assertEquals(m.saldo(), 1000);
        assertEquals(k.kassassaRahaa(), 100000);
    }
    
}
