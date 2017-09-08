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
package mage.cards.i;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.condition.common.SourceOnBattlefieldOrCommandZoneCondition;
import mage.abilities.costs.common.TapTargetCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.abilities.effects.common.CreateTokenCopyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SetTargetPointer;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.filter.predicate.permanent.AnotherPredicate;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPlayer;
import mage.target.common.TargetControlledPermanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author TheElk801
 */
public class InallaArchmageRitualist extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("another nontoken Wizard");
    private static final FilterControlledPermanent filter2 = new FilterControlledPermanent("untapped Wizards you control");

    static {
        filter.add(new SubtypePredicate(SubType.WIZARD));
        filter.add(Predicates.not(new TokenPredicate()));
        filter.add(new AnotherPredicate());
        filter2.add(new SubtypePredicate(SubType.WIZARD));
        filter2.add(Predicates.not(new TappedPredicate()));
    }

    public InallaArchmageRitualist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}{B}{R}");

        addSuperType(SuperType.LEGENDARY);
        this.subtype.add("Human");
        this.subtype.add("Wizard");
        this.power = new MageInt(4);
        this.toughness = new MageInt(5);

        // Eminence - Whenever another nontoken Wizard enters the battlefield under your control, if Inalla, Archmage Ritualist is in the command zone or on the battlefield, you may pay {1}. If you do, create a token that's a copy of that Wizard. The token gains haste. Exile it at the beginning of the next end step.
        Ability ability = new ConditionalTriggeredAbility(
                new EntersBattlefieldControlledTriggeredAbility(Zone.ALL, new DoIfCostPaid(
                        new InallaArchmageRitualistEffect(), new ManaCostsImpl("{1}"), "Pay {1} to create a token copy?"),
                        filter, false, SetTargetPointer.PERMANENT, ""),
                SourceOnBattlefieldOrCommandZoneCondition.instance,
                "<i>Eminence</i> - Whenever another nontoken Wizard enters the battlefield under your control, "
                + "{this} is in the command zone or on the battlefield, "
                + "you may pay {1}. If you do, create a token that's a copy of that Wizard. "
                + "That token gains haste. Exile it at the beginning of the next end step");
        ability.setAbilityWord(AbilityWord.EMINENCE);
        this.addAbility(ability);

        // Tap five untapped Wizards you control: Target player loses 7 life.
        ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new LoseLifeTargetEffect(7), new TapTargetCost(new TargetControlledPermanent(5, 5, filter2, true)));
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);
    }

    public InallaArchmageRitualist(final InallaArchmageRitualist card) {
        super(card);
    }

    @Override
    public InallaArchmageRitualist copy() {
        return new InallaArchmageRitualist(this);
    }
}

class InallaArchmageRitualistEffect extends OneShotEffect {

    public InallaArchmageRitualistEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "create a token that's a copy of that Wizard. That token gains haste. Exile it at the beginning of the next end step";
    }

    public InallaArchmageRitualistEffect(final InallaArchmageRitualistEffect effect) {
        super(effect);
    }

    @Override
    public InallaArchmageRitualistEffect copy() {
        return new InallaArchmageRitualistEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = ((FixedTarget) getTargetPointer()).getTargetedPermanentOrLKIBattlefield(game);
        if (permanent != null) {
            CreateTokenCopyTargetEffect effect = new CreateTokenCopyTargetEffect(null, null, true);
            effect.setTargetPointer(getTargetPointer());
            if (effect.apply(game, source)) {
                for (Permanent tokenPermanent : effect.getAddedPermanent()) {
                    ExileTargetEffect exileEffect = new ExileTargetEffect();
                    exileEffect.setTargetPointer(new FixedTarget(tokenPermanent, game));
                    DelayedTriggeredAbility delayedAbility = new AtTheBeginOfNextEndStepDelayedTriggeredAbility(exileEffect);
                    game.addDelayedTriggeredAbility(delayedAbility, source);
                }
                return true;
            }
        }

        return false;
    }
}
