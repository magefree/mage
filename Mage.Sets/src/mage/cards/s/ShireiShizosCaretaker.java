package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.condition.common.SourceOnBattlefieldCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.PowerPredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ShireiShizosCaretaker extends CardImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent();

    static {
        filter.add(TargetController.YOU.getOwnerPredicate());
        filter.add(new PowerPredicate(ComparisonType.FEWER_THAN, 2));
    }

    private static final String rule1 = "you may return that card to the battlefield " +
            "at the beginning of the next end step if {this} is still on the battlefield";
    private static final String rule2 = "Whenever a creature with power 1 or less " +
            "is put into your graveyard from the battlefield, ";

    public ShireiShizosCaretaker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{B}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.SPIRIT);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever a creature with power 1 or less is put into your graveyard from the battlefield, you may return that card to the battlefield at the beginning of the next end step if Shirei, Shizo's Caretaker is still on the battlefield.
        this.addAbility(new DiesCreatureTriggeredAbility(new CreateDelayedTriggeredAbilityEffect(
                new AtTheBeginOfNextEndStepDelayedTriggeredAbility(new ConditionalOneShotEffect(
                        new ReturnFromGraveyardToBattlefieldTargetEffect(), SourceOnBattlefieldCondition.instance,
                        "you may return that card to the battlefield if {this} is still on the battlefield"
                ), TargetController.ANY, null, true)
        ).setText(rule1), false, filter, true).setTriggerPhrase(rule2));
    }

    private ShireiShizosCaretaker(final ShireiShizosCaretaker card) {
        super(card);
    }

    @Override
    public ShireiShizosCaretaker copy() {
        return new ShireiShizosCaretaker(this);
    }
}
