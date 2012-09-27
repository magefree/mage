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

package mage.sets.scarsofmirrodin;

import java.util.UUID;

import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Duration;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.MageInt;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.DynamicManaEffect;
import mage.abilities.effects.common.UntapTargetEffect;
import mage.abilities.effects.common.continious.BecomesCreatureTargetEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.counters.CounterType;
import mage.filter.common.FilterLandPermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.Token;
import mage.target.common.TargetCreatureOrPlayer;
import mage.target.common.TargetLandPermanent;

/**
 *
 * @author Loki, North
 */
public class KothOfTheHammer extends CardImpl<KothOfTheHammer> {
    static final FilterLandPermanent filter = new FilterLandPermanent("Mountain");
    private static final FilterLandPermanent filterCount = new FilterLandPermanent("Mountain you control");

    static {
        filter.add(new SubtypePredicate("Mountain"));
        filter.add(new ControllerPredicate(Constants.TargetController.YOU));
        filterCount.add(new SubtypePredicate("Mountain"));
        filterCount.add(new ControllerPredicate(Constants.TargetController.YOU));
    }

    public KothOfTheHammer (UUID ownerId) {
        super(ownerId, 94, "Koth of the Hammer", Rarity.MYTHIC, new CardType[]{CardType.PLANESWALKER}, "{2}{R}{R}");
        this.expansionSetCode = "SOM";
        this.subtype.add("Koth");
        this.color.setRed(true);
        this.addAbility(new EntersBattlefieldAbility(new AddCountersSourceEffect(CounterType.LOYALTY.createInstance(3)), false));

        Ability ability = new LoyaltyAbility(new UntapTargetEffect(), 1);
        ability.addEffect(new BecomesCreatureTargetEffect(new KothOfTheHammerToken(), "land", Duration.EndOfTurn));
        ability.addTarget(new TargetLandPermanent(filter));
        this.addAbility(ability);
        this.addAbility(new LoyaltyAbility(new DynamicManaEffect(Mana.RedMana, new PermanentsOnBattlefieldCount(filterCount)), -2));
        this.addAbility(new LoyaltyAbility(new KothOfTheHammerThirdEffect(), -5));
    }

    public KothOfTheHammer (final KothOfTheHammer card) {
        super(card);
    }

    @Override
    public KothOfTheHammer copy() {
        return new KothOfTheHammer(this);
    }
}
class KothOfTheHammerToken extends Token {

    public KothOfTheHammerToken() {
        super("Elemental", "4/4 red Elemental");
        this.cardType.add(CardType.CREATURE);
        this.subtype.add("Elemental");

        this.color.setRed(true);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);
    }
}

class KothOfTheHammerThirdEffect extends ContinuousEffectImpl<KothOfTheHammerThirdEffect> {
    public KothOfTheHammerThirdEffect() {
        super(Duration.EndOfGame, Constants.Outcome.AddAbility);
        staticText = "You get an emblem with \"Mountains you control have '{T}: This land deals 1 damage to target creature or player.'\"";
    }

    public KothOfTheHammerThirdEffect(final KothOfTheHammerThirdEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Constants.Layer layer, Constants.SubLayer sublayer, Ability source, Game game) {
        switch (layer) {
            case AbilityAddingRemovingEffects_6:
                if (sublayer == Constants.SubLayer.NA) {
                    for (Permanent p : game.getBattlefield().getActivePermanents(KothOfTheHammer.filter, source.getControllerId(), game)) {
                        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DamageTargetEffect(1), new TapSourceCost());
                        ability.addTarget(new TargetCreatureOrPlayer());
                        p.addAbility(ability, game);
                    }
                }
                break;
        }
        return true;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public KothOfTheHammerThirdEffect copy() {
        return new KothOfTheHammerThirdEffect(this);
    }

    @Override
    public boolean hasLayer(Constants.Layer layer) {
        return layer == Constants.Layer.AbilityAddingRemovingEffects_6;
    }

}