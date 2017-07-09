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
package mage.cards.l;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.common.PlanswalkerEntersWithLoyalityCountersAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.GetEmblemEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterLandCard;
import mage.filter.common.FilterLandPermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.game.Game;
import mage.game.command.emblems.LilianaOfTheDarkRealmsEmblem;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author North
 */
public class LilianaOfTheDarkRealms extends CardImpl {

    private static final FilterLandCard filter = new FilterLandCard("Swamp card");

    static {
        filter.add(new SubtypePredicate(SubType.SWAMP));
    }

    public LilianaOfTheDarkRealms(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{2}{B}{B}");
        this.subtype.add("Liliana");

        this.addAbility(new PlanswalkerEntersWithLoyalityCountersAbility(3));

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

class LilianaOfTheDarkRealmsEffect extends ContinuousEffectImpl {

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
        filter.add(new SubtypePredicate(SubType.SWAMP));
        filter.add(new ControllerPredicate(TargetController.YOU));
        this.amount = game.getBattlefield().count(filter, source.getSourceId(), source.getControllerId(), game);

        Player player = game.getPlayer(source.getControllerId());

        String message = "Should the target creature get -X/-X instead of +X/+X?";
        if (player != null && player.chooseUse(Outcome.Neutral, message, source, game)) {
            this.amount *= -1;
        }
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
