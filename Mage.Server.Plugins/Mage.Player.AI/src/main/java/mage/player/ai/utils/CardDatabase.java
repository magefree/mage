package mage.player.ai.utils;

import mage.cards.Card;
import org.apache.log4j.Logger;

import java.io.InputStream;
import java.util.*;


/**
 *
 * @author Administrator
 */
public class CardDatabase {
    
    public String cardDraftType;
    public int cardDraftScore;
    
    CardDatabase(String i, int j) {
        cardDraftType = i;
        cardDraftScore = j;
    }
    
}
