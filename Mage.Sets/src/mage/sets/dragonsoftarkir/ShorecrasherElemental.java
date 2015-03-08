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
package mage.sets.dragonsoftarkir;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.MorphAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author jeffwadsworth
 */
public class ShorecrasherElemental extends CardImpl {

    public ShorecrasherElemental(UUID ownerId) {
        super(ownerId, 73, "Shorecrasher Elemental", Rarity.MYTHIC, new CardType[]{CardType.CREATURE}, "{U}{U}{U}");
        this.expansionSetCode = "DTK";
        this.subtype.add("Elemental");
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // {U}: Exile Shorecrasher Elemental, then return it to the battlefield face down under its owner's control.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new ShorecrasherElementalEffect(), new ManaCostsImpl("{U}")));

        // {1}: Shorecrasher Elemental gets +1/-1 or -1/+1 until end of turn.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new BoostSourceEffect(1, -1, Duration.EndOfTurn), new ManaCostsImpl("{1}"));
        Mode mode = new Mode();
        mode.getEffects().add(new BoostSourceEffect(-1, 1, Duration.EndOfTurn));
        ability.addMode(mode);
        this.addAbility(ability);

        // Megamorph {4}{U}
        this.addAbility(new MorphAbility(this, new ManaCostsImpl("{4}{U}"), true));

    }

    public ShorecrasherElemental(final ShorecrasherElemental card) {
        super(card);
    }

    @Override
    public ShorecrasherElemental copy() {
        return new ShorecrasherElemental(this);
    }
}

class ShorecrasherElementalEffect extends OneShotEffect {

    public ShorecrasherElementalEffect() {
        super(Outcome.Neutral);
        this.staticText = "Exile {this}, then return it to the battlefield face down under its owner's control";
    }

    public ShorecrasherElementalEffect(final ShorecrasherElementalEffect effect) {
        super(effect);
    }

    @Override
    public ShorecrasherElementalEffect copy() {
        return new ShorecrasherElementalEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent shorecrasherElemental = game.getPermanent(source.getSourceId());
        if (shorecrasherElemental != null) {
            if (shorecrasherElemental.moveToExile(source.getSourceId(), "Shorecrasher Elemental", source.getSourceId(), game)) {
                Card card = game.getExile().getCard(source.getSourceId(), game);
                if (card != null) {
                    card.setFaceDown(true);
                    return card.moveToZone(Zone.BATTLEFIELD, source.getSourceId(), game, false);
                }
            }
        }
        return false;
    }
}
