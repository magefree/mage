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
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.common.PlanswalkerEntersWithLoyalityCountersAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.GetEmblemEffect;
import mage.abilities.effects.common.PutTopCardOfLibraryIntoGraveControllerEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.Game;
import mage.game.command.Emblem;
import mage.game.permanent.token.ZombieToken;
import mage.players.Player;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author fireshoes
 */
public class LilianaTheLastHope extends CardImpl {

    public LilianaTheLastHope(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.PLANESWALKER},"{1}{B}{B}");
        this.subtype.add("Liliana");

        this.addAbility(new PlanswalkerEntersWithLoyalityCountersAbility(3));

        // +1: Up to one target creature gets -2/-1 until your next turn.
        Effect effect = new BoostTargetEffect(-2, -1, Duration.UntilYourNextTurn);
        effect.setText("Up to one target creature gets -2/-1 until your next turn");
        Ability ability = new LoyaltyAbility(effect, 1);
        ability.addTarget(new TargetCreaturePermanent(0, 1));
        this.addAbility(ability);

        // -2: Put the top two cards of your library into your graveyard, then you may return a creature card from your graveyard to your hand.
        ability = new LoyaltyAbility(new PutTopCardOfLibraryIntoGraveControllerEffect(2), -2);
        ability.addEffect(new LilianaTheLastHopeEffect());
        this.addAbility(ability);

        // -7: You get an emblem with "At the beginning of your end step, create X 2/2 black Zombie creature tokens,
        // where X is two plus the number of Zombies you control."
        this.addAbility(new LoyaltyAbility(new GetEmblemEffect(new LilianaTheLastHopeEmblem()), -7));
    }

    public LilianaTheLastHope(final LilianaTheLastHope card) {
        super(card);
    }

    @Override
    public LilianaTheLastHope copy() {
        return new LilianaTheLastHope(this);
    }
}

class LilianaTheLastHopeEffect extends OneShotEffect {

    public LilianaTheLastHopeEffect() {
        super(Outcome.ReturnToHand);
        this.staticText = ", then you may return a creature card from your graveyard to your hand";
    }

    public LilianaTheLastHopeEffect(final LilianaTheLastHopeEffect effect) {
        super(effect);
    }

    @Override
    public LilianaTheLastHopeEffect copy() {
        return new LilianaTheLastHopeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        TargetCardInYourGraveyard target = new TargetCardInYourGraveyard(new FilterCreatureCard("creature card from your graveyard"));
        target.setNotTarget(true);
        if (target.canChoose(source.getSourceId(), source.getControllerId(), game)
                && controller.chooseUse(outcome, "Return a creature card from your graveyard to hand?", source, game)
                && controller.choose(Outcome.ReturnToHand, target, source.getSourceId(), game)) {
            Card card = game.getCard(target.getFirstTarget());
            if (card != null) {
                controller.moveCards(card, Zone.HAND, source, game);
            }
        }
        return true;
    }
}
class LilianaTheLastHopeEmblem extends Emblem {

    public LilianaTheLastHopeEmblem() {
        this.setName("EMBLEM: Liliana, the Last Hope");
        Ability ability = new BeginningOfEndStepTriggeredAbility(Zone.COMMAND, new CreateTokenEffect(new ZombieToken(), new LilianaZombiesCount()),
                TargetController.YOU, null, false);
        this.getAbilities().add(ability);
    }
}

class LilianaZombiesCount implements DynamicValue {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent();

    static {
        filter.add(new SubtypePredicate("Zombie"));
    }

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        int amount = game.getBattlefield().countAll(filter, sourceAbility.getControllerId(), game) + 2;
        return amount;
    }

    @Override
    public DynamicValue copy() {
        return new LilianaZombiesCount();
    }

    @Override
    public String toString() {
        return "X";
    }

    @Override
    public String getMessage() {
        return "two plus the number of Zombies you control";
    }
}
