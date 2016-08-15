/*
 * Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, are
 * permitted provided that the following conditions are met:
 *
 *    1. Redistributions of source code must retain the above copyright notice, this list of
 *       conditions and the following disclaimer.
 *
 *    2. Redistributions in binary form must reproduce the above copyright notice, this list
 *       of conditions and the following disclaimer in the documentation and/or other materials
 *       provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 * FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 * ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 * ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 * The views and conclusions contained in the software and documentation are those of the
 * authors and should not be interpreted as representing official policies, either expressed
 * or implied, of BetaSteward_at_googlemail.com.
 */
package mage.game.permanent.token;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import mage.MageInt;
import mage.constants.CardType;

/**
 *
 * @author LoneFox
 */
public class BeastToken2 extends Token {

    final static private List<String> tokenImageSets = new ArrayList<>();

    static {
        tokenImageSets.addAll(Arrays.asList("ZEN", "C14", "DDD", "C15", "DD3GVL"));
    }

    public BeastToken2() {
        this(null, 0);
    }

    public BeastToken2(String setCode) {
        this(setCode, 0);
    }

    public BeastToken2(String setCode, int tokenType) {
        super("Beast", "4/4 green Beast creature token");
        availableImageSetCodes = tokenImageSets;
        setOriginalExpansionSetCode(setCode);
        cardType.add(CardType.CREATURE);
        color.setGreen(true);
        subtype.add("Beast");
        power = new MageInt(4);
        toughness = new MageInt(4);
    }

    public BeastToken2(final BeastToken2 token) {
        super(token);
    }

    @Override
    public BeastToken2 copy() {
        return new BeastToken2(this);
    }

    @Override
    public void setExpansionSetCodeForImage(String code) {
        super.setExpansionSetCodeForImage(code);
        if (getOriginalExpansionSetCode().equals("C14") || getOriginalExpansionSetCode().equals("DDD") || getOriginalExpansionSetCode().equals("DD3GVL")) {
            this.setTokenType(2);
        }
    }
}
