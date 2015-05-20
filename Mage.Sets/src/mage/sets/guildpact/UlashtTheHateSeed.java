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
package mage.sets.guildpact;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.filter.predicate.permanent.AnotherPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.SaprolingToken;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author emerald000
 */
public class UlashtTheHateSeed extends CardImpl {

    public UlashtTheHateSeed(UUID ownerId) {
        super(ownerId, 136, "Ulasht, the Hate Seed", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{2}{R}{G}");
        this.expansionSetCode = "GPT";
        this.supertype.add("Legendary");
        this.subtype.add("Hellion");
        this.subtype.add("Hydra");

        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Ulasht, the Hate Seed enters the battlefield with a +1/+1 counter on it for each other red creature you control and a +1/+1 counter on it for each other green creature you control.
        this.addAbility(new EntersBattlefieldAbility(new UlashtTheHateSeedEffect(), "with a +1/+1 counter on it for each other red creature you control and a +1/+1 counter on it for each other green creature you control."));
        
        // {1}, Remove a +1/+1 counter from Ulasht: Choose one - Ulasht deals 1 damage to target creature;
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DamageTargetEffect(1), new GenericManaCost(1));
        ability.addCost(new RemoveCountersSourceCost(CounterType.P1P1.createInstance()));
        ability.addTarget(new TargetCreaturePermanent());
        // or put a 1/1 green Saproling creature token onto the battlefield.
        Mode mode = new Mode();
        Effect effect = new CreateTokenEffect(new SaprolingToken());
        effect.setText("Put a 1/1 green Saproling creature token onto the battlefield.");
        mode.getEffects().add(effect);
        ability.addMode(mode);
        this.addAbility(ability);
    }

    public UlashtTheHateSeed(final UlashtTheHateSeed card) {
        super(card);
    }

    @Override
    public UlashtTheHateSeed copy() {
        return new UlashtTheHateSeed(this);
    }
}

class UlashtTheHateSeedEffect extends OneShotEffect {

    private static final FilterControlledCreaturePermanent filterGreen = new FilterControlledCreaturePermanent();
    private static final FilterControlledCreaturePermanent filterRed = new FilterControlledCreaturePermanent();
    
    static {
        filterGreen.add(new AnotherPredicate());
        filterGreen.add(new ColorPredicate(ObjectColor.GREEN));
        filterRed.add(new AnotherPredicate());
        filterRed.add(new ColorPredicate(ObjectColor.RED));
    }

    UlashtTheHateSeedEffect() {
        super(Outcome.BoostCreature);
        staticText = "{this} enters the battlefield with a +1/+1 counter on it for each other red creature you control and a +1/+1 counter on it for each other green creature you control.";
    }

    UlashtTheHateSeedEffect(final UlashtTheHateSeedEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (permanent != null && player != null) {
            int amount = game.getBattlefield().count(filterRed, source.getSourceId(), source.getControllerId(), game);
            amount += game.getBattlefield().count(filterGreen, source.getSourceId(), source.getControllerId(), game);
            if (amount > 0) {
                permanent.addCounters(CounterType.P1P1.createInstance(amount), game);
            }
            return true;
        }
        return false;
    }

    @Override
    public UlashtTheHateSeedEffect copy() {
        return new UlashtTheHateSeedEffect(this);
    }

}