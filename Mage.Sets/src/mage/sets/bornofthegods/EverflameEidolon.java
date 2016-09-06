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
package mage.sets.bornofthegods;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostEnchantedEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.BestowAbility;
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
 * @author Quercitron
 */
public class EverflameEidolon extends CardImpl {

    public EverflameEidolon(UUID ownerId) {
        super(ownerId, 92, "Everflame Eidolon", Rarity.UNCOMMON, new CardType[]{CardType.ENCHANTMENT, CardType.CREATURE}, "{1}{R}");
        this.expansionSetCode = "BNG";
        this.subtype.add("Spirit");

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Bestow {2}{R}
        this.addAbility(new BestowAbility(this, "{2}{R}"));
        // {R}: Everflame Eidolon gets +1/+0 until end of turn. If it's an Aura, enchanted creature gets +1/+0 until end of turn instead.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new EverflameEidolonEffect(), new ManaCostsImpl("{R}")));
        // Enchanted creature gets +1/+1.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostEnchantedEffect(1, 1, Duration.WhileOnBattlefield)));
    }

    public EverflameEidolon(final EverflameEidolon card) {
        super(card);
    }

    @Override
    public EverflameEidolon copy() {
        return new EverflameEidolon(this);
    }
}

class EverflameEidolonEffect extends OneShotEffect {

    public EverflameEidolonEffect() {
        super(Outcome.BoostCreature);
        this.staticText = "{this} gets +1/+0 until end of turn. If it's an Aura, enchanted creature gets +1/+0 until end of turn instead";
    }

    public EverflameEidolonEffect(final EverflameEidolonEffect effect) {
        super(effect);
    }

    @Override
    public EverflameEidolonEffect copy() {
        return new EverflameEidolonEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent sourceObject = game.getPermanentOrLKIBattlefield(source.getSourceId());
        if (sourceObject != null) {
            if (sourceObject.getSubtype(game).contains("Aura")) {
                new BoostEnchantedEffect(1, 0, Duration.EndOfTurn).apply(game, source);
            } else {
                game.addEffect(new BoostSourceEffect(1, 0, Duration.EndOfTurn), source);
            }
            return true;
        }
        return false;
    }
}
