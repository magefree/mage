
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.PutCardFromOneOfTwoZonesOntoBattlefieldEffect;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.ComparisonType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author jeffwadsworth
 */
public final class SwiftWarkite extends CardImpl {

    private static final FilterCard filter = new FilterCard("creature card with mana value 3 or less from your hand or graveyard");

    static {
        filter.add(CardType.CREATURE.getPredicate());
        filter.add(new ManaValuePredicate(ComparisonType.FEWER_THAN, 4));
    }

    public SwiftWarkite(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{B}{R}");
        this.subtype.add(SubType.DRAGON);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Swift Warkite enters the battlefield, you may put a creature card with converted mana cost 3 or less from your hand or graveyard onto the battlefield. That creature gains haste. Return it to your hand at the beginning of the next end step.
        this.addAbility(new EntersBattlefieldTriggeredAbility(
                new PutCardFromOneOfTwoZonesOntoBattlefieldEffect(filter, false, new SwiftWarkiteEffect()),
                true)
        );
    }

    private SwiftWarkite(final SwiftWarkite card) {
        super(card);
    }

    @Override
    public SwiftWarkite copy() {
        return new SwiftWarkite(this);
    }
}

class SwiftWarkiteEffect extends OneShotEffect {

    SwiftWarkiteEffect() {
        super(Outcome.AddAbility);
        this.staticText = "that creature gains haste. Return it to your hand at the beginning of the next end step";
    }

    SwiftWarkiteEffect(final SwiftWarkiteEffect effect) {
        super(effect);
    }

    @Override
    public SwiftWarkiteEffect copy() {
        return new SwiftWarkiteEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent movedCreature = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (movedCreature == null) {
            return false;
        }

        ContinuousEffect gainHasteEffect = new GainAbilityTargetEffect(HasteAbility.getInstance(), Duration.Custom);
        gainHasteEffect.setTargetPointer(new FixedTarget(movedCreature.getId(), movedCreature.getZoneChangeCounter(game)));
        game.addEffect(gainHasteEffect, source);

        Effect returnToHandEffect = new ReturnToHandTargetEffect();
        returnToHandEffect.setTargetPointer(new FixedTarget(movedCreature.getId(), movedCreature.getZoneChangeCounter(game)));
        DelayedTriggeredAbility delayedAbility = new AtTheBeginOfNextEndStepDelayedTriggeredAbility(returnToHandEffect);
        game.addDelayedTriggeredAbility(delayedAbility, source);
        return true;
    }
}
