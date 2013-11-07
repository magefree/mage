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
package mage.sets.eventide;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.ManaType;
import static mage.constants.ManaType.BLACK;
import static mage.constants.ManaType.BLUE;
import static mage.constants.ManaType.GREEN;
import static mage.constants.ManaType.RED;
import static mage.constants.ManaType.WHITE;
import mage.constants.Rarity;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.Token;

/**
 *
 * @author jeffwadsworth
 *
 */
public class SpringjackShepherd extends CardImpl<SpringjackShepherd> {

    public SpringjackShepherd(UUID ownerId) {
        super(ownerId, 15, "Springjack Shepherd", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{3}{W}");
        this.expansionSetCode = "EVE";
        this.subtype.add("Kithkin");
        this.subtype.add("Wizard");

        this.color.setWhite(true);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // Chroma - When Springjack Shepherd enters the battlefield, put a 0/1 white Goat creature token onto the battlefield for each white mana symbol in the mana costs of permanents you control.
        Effect effect = new CreateTokenEffect(new GoatToken(), new ChromaSpringjackShepherdCount(ManaType.WHITE));
        effect.setText("<i>Chroma</i> - When Springjack Shepherd enters the battlefield, put a 0/1 white Goat creature token onto the battlefield for each white mana symbol in the mana costs of permanents you control.");
        this.addAbility(new EntersBattlefieldTriggeredAbility(effect, false, true));

    }

    public SpringjackShepherd(final SpringjackShepherd card) {
        super(card);
    }

    @Override
    public SpringjackShepherd copy() {
        return new SpringjackShepherd(this);
    }
}

class ChromaSpringjackShepherdCount implements DynamicValue {

    private ManaType chromaColor;

    public ChromaSpringjackShepherdCount(ManaType chromaColor) {
        this.chromaColor = chromaColor;
    }

    public ChromaSpringjackShepherdCount(final ChromaSpringjackShepherdCount dynamicValue) {
        this.chromaColor = dynamicValue.chromaColor;
    }

    @Override
    public int calculate(Game game, Ability sourceAbility) {
        int chroma = 0;
        for (Permanent permanent : game.getBattlefield().getAllActivePermanents(new FilterPermanent(), sourceAbility.getControllerId(), game)) {
            chroma += permanent.getManaCost().getMana().getWhite();
        }
        return chroma;
    }

    @Override
    public DynamicValue copy() {
        return new ChromaSpringjackShepherdCount(this);
    }

    @Override
    public String toString() {
        return "1";
    }

    @Override
    public String getMessage() {
        return "";
    }
}

class GoatToken extends Token {

    public GoatToken() {
        super("Goat", "a 0/1 white Goat creature token");
        cardType.add(CardType.CREATURE);
        color = ObjectColor.WHITE;
        subtype.add("Goat");
        power = new MageInt(0);
        toughness = new MageInt(1);
    }
}