/*
 *  Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without modification, are
 *  permitted provided that the following conditions are met:
 *
 *     1. Redistributions of source code must retain the above copyright notice, this list of
 *        conditions and the following disclaimer.
 *
 *     2. Redistributions in binary form must reproduce the above copyright notice, this list
 *        of conditions and the following disclaimer in the documentation and/or other materials
 *        provided with the distribution.
 *
 *  THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
 *  WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 *  FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
 *  CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 *  CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 *  SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 *  ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 *  NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 *  ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 *  The views and conclusions contained in the software and documentation are those of the
 *  authors and should not be interpreted as representing official policies, either expressed
 *  or implied, of BetaSteward_at_googlemail.com.
 */

package mage.cards.decks;

import java.io.Serializable;

/**
 *
 * @author LevelX2
 */
public class DeckCardInfo implements Serializable {

    private String cardName;
    private String setCode;
    private String cardNum;
    private int quantity;

    public DeckCardInfo(String cardName, String cardNum, String setCode) {
        this(cardName, cardNum, setCode, 1);
    }

    public DeckCardInfo(String cardName, String cardNum, String setCode, int quantity) {
        this.cardName = cardName;
        this.cardNum = cardNum;
        this.setCode = setCode;
        this.quantity = quantity;
    }

    public String getCardName() {
        return cardName;
    }

    public String getSetCode() {
        return setCode;
    }

    public String getCardNum() {
        return cardNum;
    }

    public int getQuantity() {
        return quantity;
    }

    public DeckCardInfo increaseQuantity() {
        quantity++;
        return this;
    }

    public String getCardKey() {
        return setCode + cardNum;
    }

}
