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
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.delayed.AtEndOfTurnDelayedTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.InspiredAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.AnotherPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.sets.tokens.EmptyToken;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.FixedTarget;
import mage.util.CardUtil;

/**
 *
 * @author Quercitron
 */
public class FelhideSpiritbinder extends CardImpl<FelhideSpiritbinder> {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("another creature");

    static {
        filter.add(new AnotherPredicate());
    }

    public FelhideSpiritbinder(UUID ownerId) {
        super(ownerId, 96, "Felhide Spiritbinder", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{3}{R}");
        this.expansionSetCode = "BNG";
        this.subtype.add("Minotaur");
        this.subtype.add("Shaman");

        this.color.setRed(true);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // <i>Inspired</i> - Whenever Felhide Spiritbinder becomes untapped, you may pay {1}{R}. If you do, put a token onto the battlefield that's a copy of another target creature except it's an enchantment in addition to its other types. It gains haste. Exile it at the beginning of the next end step.
        Ability ability = new InspiredAbility(new DoIfCostPaid(new FelhideSpiritbinderEffect(), new ManaCostsImpl("{1}{R}"),"Use effect of {source}?"));
        ability.addTarget(new TargetCreaturePermanent(filter, true));
        this.addAbility(ability);
    }

    public FelhideSpiritbinder(final FelhideSpiritbinder card) {
        super(card);
    }

    @Override
    public FelhideSpiritbinder copy() {
        return new FelhideSpiritbinder(this);
    }
}

class FelhideSpiritbinderEffect extends OneShotEffect<FelhideSpiritbinderEffect> {

    public FelhideSpiritbinderEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "put a token onto the battlefield that's a copy of another target creature except it's an enchantment in addition to its other types. It gains haste. Exile it at the beginning of the next end step";
    }

    public FelhideSpiritbinderEffect(final FelhideSpiritbinderEffect effect) {
        super(effect);
    }

    @Override
    public FelhideSpiritbinderEffect copy() {
        return new FelhideSpiritbinderEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        if (permanent == null) {
            permanent = (Permanent) game.getLastKnownInformation(source.getFirstTarget(), Zone.BATTLEFIELD);
        }
        if (permanent != null) {
            EmptyToken token = new EmptyToken();
            CardUtil.copyTo(token).from(permanent);

            if (!token.getCardType().contains(CardType.ENCHANTMENT)) {
                token.getCardType().add(CardType.ENCHANTMENT);
            }
            token.addAbility(HasteAbility.getInstance());
            token.putOntoBattlefield(1, game, source.getSourceId(), source.getControllerId());

            ExileTargetEffect exileEffect = new ExileTargetEffect();
            exileEffect.setTargetPointer(new FixedTarget(token.getLastAddedToken()));
            DelayedTriggeredAbility delayedAbility = new AtEndOfTurnDelayedTriggeredAbility(exileEffect);
            delayedAbility.setSourceId(source.getSourceId());
            delayedAbility.setControllerId(source.getControllerId());
            game.addDelayedTriggeredAbility(delayedAbility);

            return true;
        }

        return false;
    }
}
