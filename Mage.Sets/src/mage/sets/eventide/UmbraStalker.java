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
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continious.SetPowerToughnessSourceEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.ManaType;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author jeffwadsworth
 *
 */
public class UmbraStalker extends CardImpl<UmbraStalker> {

    public UmbraStalker(UUID ownerId) {
        super(ownerId, 48, "Umbra Stalker", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{4}{B}{B}{B}");
        this.expansionSetCode = "EVE";
        this.subtype.add("Elemental");

        this.color.setBlack(true);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Chroma - Umbra Stalker's power and toughness are each equal to the number of black mana symbols in the mana costs of cards in your graveyard.
        Effect effect = new SetPowerToughnessSourceEffect(new ChromaUmbraStalkerCount(ManaType.BLACK), Duration.WhileOnBattlefield);
        effect.setText("<i>Chroma</i> - Umbra Stalker's power and toughness are each equal to the number of black mana symbols in the mana costs of cards in your graveyard.");
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, effect));

    }

    public UmbraStalker(final UmbraStalker card) {
        super(card);
    }

    @Override
    public UmbraStalker copy() {
        return new UmbraStalker(this);
    }
}

class ChromaUmbraStalkerCount implements DynamicValue {

    private ManaType chromaColor;
    private int chroma;

    public ChromaUmbraStalkerCount(ManaType chromaColor) {
        this.chromaColor = chromaColor;
    }

    public ChromaUmbraStalkerCount(final ChromaUmbraStalkerCount dynamicValue) {
        this.chromaColor = dynamicValue.chromaColor;
    }

    @Override
    public int calculate(Game game, Ability sourceAbility) {
        chroma = 0;
        Player you = game.getPlayer(sourceAbility.getControllerId());
        if (you == null) {
            return 0;
        }
        for (Card card : you.getGraveyard().getCards(game)) {
            chroma += card.getManaCost().getMana().getBlack();
        }
        return chroma;
    }

    @Override
    public DynamicValue copy() {
        return new ChromaUmbraStalkerCount(this);
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
