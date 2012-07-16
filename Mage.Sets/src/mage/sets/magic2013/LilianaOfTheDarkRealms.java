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
package mage.sets.magic2013;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Duration;
import mage.Constants.Layer;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.Constants.SubLayer;
import mage.Constants.TargetController;
import mage.Constants.Zone;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.GetEmblemEffect;
import mage.abilities.effects.common.continious.GainAbilityControlledEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.abilities.mana.SimpleManaAbility;
import mage.cards.CardImpl;
import mage.counters.CounterType;
import mage.filter.common.FilterLandCard;
import mage.filter.common.FilterLandPermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.game.Game;
import mage.game.command.Emblem;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author North
 */
public class LilianaOfTheDarkRealms extends CardImpl<LilianaOfTheDarkRealms> {

    private final static FilterLandCard filter = new FilterLandCard("Swamp card");

    static {
        filter.add(new SubtypePredicate("Swamp"));
    }

    public LilianaOfTheDarkRealms(UUID ownerId) {
        super(ownerId, 97, "Liliana of the Dark Realms", Rarity.MYTHIC, new CardType[]{CardType.PLANESWALKER}, "{2}{B}{B}");
        this.expansionSetCode = "M13";
        this.subtype.add("Liliana");

        this.color.setBlack(true);

        this.addAbility(new EntersBattlefieldAbility(new AddCountersSourceEffect(CounterType.LOYALTY.createInstance(3)), ""));

        // +1: Search your library for a Swamp card, reveal it, and put it into your hand. Then shuffle your library.
        this.addAbility(new LoyaltyAbility(new SearchLibraryPutInHandEffect(new TargetCardInLibrary(filter), true), 1));
        // -3: Target creature gets +X/+X or -X/-X until end of turn, where X is the number of Swamps you control.
        LoyaltyAbility ability = new LoyaltyAbility(new LilianaOfTheDarkRealmsEffect(), -3);
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
        // -6: You get an emblem with "Swamps you control have '{tap}: Add {B}{B}{B}{B} to your mana pool.'"
        this.addAbility(new LoyaltyAbility(new GetEmblemEffect(new LilianaOfTheDarkRealmsEmblem()), -6));
    }

    public LilianaOfTheDarkRealms(final LilianaOfTheDarkRealms card) {
        super(card);
    }

    @Override
    public LilianaOfTheDarkRealms copy() {
        return new LilianaOfTheDarkRealms(this);
    }
}

class LilianaOfTheDarkRealmsEffect extends ContinuousEffectImpl<LilianaOfTheDarkRealmsEffect> {

    private int amount;

    public LilianaOfTheDarkRealmsEffect() {
        super(Duration.EndOfTurn, Layer.PTChangingEffects_7, SubLayer.ModifyPT_7c, Outcome.BoostCreature);
        this.amount = 0;
        this.staticText = "Target creature gets +X/+X or -X/-X until end of turn, where X is the number of Swamps you control";
    }

    public LilianaOfTheDarkRealmsEffect(final LilianaOfTheDarkRealmsEffect effect) {
        super(effect);
        this.amount = effect.amount;
    }

    @Override
    public LilianaOfTheDarkRealmsEffect copy() {
        return new LilianaOfTheDarkRealmsEffect(this);
    }

    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);

        FilterLandPermanent filter = new FilterLandPermanent("Swamps");
        filter.add(new SubtypePredicate("Swamp"));
        filter.add(new ControllerPredicate(TargetController.YOU));
        this.amount = game.getBattlefield().count(filter, source.getControllerId(), game);

        Player player = game.getPlayer(source.getControllerId());

        String message = "Should the target creature get -X/-X instead of +X/+X?";
        if (player != null && player.chooseUse(Outcome.Neutral, message, game)) {
            this.amount *= -1;
        }

        targetPointer.init(game, source);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent target = game.getPermanent(source.getFirstTarget());
        if (target != null) {
            target.addPower(amount);
            target.addToughness(amount);
            return true;
        }
        return false;
    }
}

class LilianaOfTheDarkRealmsEmblem extends Emblem {

    private static final FilterLandPermanent filter = new FilterLandPermanent("Swamps");

    static {
        filter.add(new SubtypePredicate("Swamp"));
    }

    public LilianaOfTheDarkRealmsEmblem() {
        SimpleManaAbility manaAbility = new SimpleManaAbility(Zone.BATTLEFIELD, Mana.BlackMana(4), new TapSourceCost());
        Ability ability = new SimpleStaticAbility(Zone.COMMAND, new GainAbilityControlledEffect(manaAbility, Duration.WhileOnBattlefield, filter));
        this.getAbilities().add(ability);
    }
}
