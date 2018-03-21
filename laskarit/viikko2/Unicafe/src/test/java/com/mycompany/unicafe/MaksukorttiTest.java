package com.mycompany.unicafe;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class MaksukorttiTest {

    Maksukortti kortti;

    @Before
    public void setUp() {
        kortti = new Maksukortti(10);
    }

    @Test
    public void luotuKorttiOlemassa() {
        assertTrue(kortti!=null);      
    }
    
    @Test
    public void kortinSaldoAlussaOikein() {
        assertEquals(kortti.saldo(), 10);
    }
    
    @Test
    public void rahanLataaminenKasvattaaSaldoaOikein() {
        kortti.lataaRahaa(100);
        assertEquals("saldo: 1.10", kortti.toString());
    }
    
    @Test
    public void rahanOttaminenVahentaaSaldoaOikein() {
        kortti.otaRahaa(10);
        
        assertEquals("saldo: 0.0", kortti.toString());
    }
    
    @Test
    public void saldoEiMuutuJosRahaaEiTarpeeksi() {
        kortti.otaRahaa(100);
        
        assertEquals("saldo: 0.10", kortti.toString());
    }
    
    @Test
    public void palauttaaTrueJosRahatRiittavat() {
        assertTrue(kortti.otaRahaa(10));
    }
    
    @Test
    public void palauttaaFalseJosRahatEiRiita() {
        assertFalse(kortti.otaRahaa(1000));
    }
    
    
    
}
