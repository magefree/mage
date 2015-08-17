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
package mage.sets.lorwyn;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.SacrificeTargetEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreatureCard;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInHand;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author LevelX2
 */
public class IncandescentSoulstoke extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Elemental creatures");

    static {
        filter.add(new SubtypePredicate("Elemental"));
    }

    public IncandescentSoulstoke(UUID ownerId) {
        super(ownerId, 178, "Incandescent Soulstoke", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{2}{R}");
        this.expansionSetCode = "LRW";
        this.subtype.add("Elemental");
        this.subtype.add("Shaman");
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Other Elemental creatures you control get +1/+1.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostControlledEffect(1, 1, Duration.WhileOnBattlefield, filter, true)));

        // {1}{R}, {T}: You may put an Elemental creature card from your hand onto the battlefield. That creature gains haste until end of turn. Sacrifice it at the beginning of the next end step.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new IncandescentSoulstokeEffect(), new ManaCostsImpl<>("{1}{R}"));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);

    }

    public IncandescentSoulstoke(final IncandescentSoulstoke card) {
        super(card);
    }

    @Override
    public IncandescentSoulstoke copy() {
        return new IncandescentSoulstoke(this);
    }
}

class IncandescentSoulstokeEffect extends OneShotEffect {

    private static final String choiceText = "Put an Elemental creature card from your hand onto the battlefield?";

    public IncandescentSoulstokeEffect() {
        super(Outcome.Benefit);
        this.staticText = "You may put an Elemental creature card from your hand onto the battlefield. That creature gains haste until end of turn. Sacrifice it at the beginning of the next end step";
    }

    public IncandescentSoulstokeEffect(final IncandescentSoulstokeEffect effect) {
        super(effect);
    }

    @Override
    public IncandescentSoulstokeEffect copy() {
        return new IncandescentSoulstokeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            if (controller.chooseUse(Outcome.PutCreatureInPlay, choiceText, source, game)) {
                FilterCard filter = new FilterCreatureCard();
                filter.add(new SubtypePredicate(("Elemental")));
                TargetCardInHand target = new TargetCardInHand(filter);
                if (controller.choose(Outcome.PutCreatureInPlay, target, source.getSourceId(), game)) {
                    Card card = game.getCard(target.getFirstTarget());
                    if (card != null) {
                        if (controller.putOntoBattlefieldWithInfo(card, game, Zone.HAND, source.getSourceId())) {
                            Permanent permanent = game.getPermanent(card.getId());
                            if (permanent != null) {
                                ContinuousEffect effect = new GainAbilityTargetEffect(HasteAbility.getInstance(), Duration.Custom);
                                effect.setTargetPointer(new FixedTarget(permanent, game));
                                game.addEffect(effect, source);
                                SacrificeTargetEffect sacrificeEffect = new SacrificeTargetEffect("sacrifice " + card.getName(), source.getControllerId());
                                sacrificeEffect.setTargetPointer(new FixedTarget(permanent, game));
                                DelayedTriggeredAbility delayedAbility = new AtTheBeginOfNextEndStepDelayedTriggeredAbility(sacrificeEffect);
                                delayedAbility.setSourceId(source.getSourceId());
                                delayedAbility.setControllerId(source.getControllerId());
                                delayedAbility.setSourceObject(source.getSourceObject(game), game);
                                game.addDelayedTriggeredAbility(delayedAbility);
                            }
                        }
                    }
                }
            }
            return true;
        }
        return false;
    }
}
