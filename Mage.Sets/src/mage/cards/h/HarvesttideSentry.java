package mage.cards.h;

import mage.MageInt;
import mage.abilities.condition.common.CovenCondition;
import mage.abilities.effects.common.combat.CantBeBlockedByCreaturesSourceEffect;
import mage.abilities.hint.common.CovenHint;
import mage.abilities.triggers.BeginningOfCombatTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.PowerPredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class HarvesttideSentry extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creatures with power 2 or less");

    static {
        filter.add(new PowerPredicate(ComparisonType.FEWER_THAN, 3));
    }

    public HarvesttideSentry(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // Coven — At the beginning of combat on your turn, if you control three or more creatures with different powers, Harvesttide Sentry can't be blocked by creatures with power 2 or less this turn.
        this.addAbility(new BeginningOfCombatTriggeredAbility(
                new CantBeBlockedByCreaturesSourceEffect(filter, Duration.EndOfTurn)
        ).withInterveningIf(CovenCondition.instance).addHint(CovenHint.instance).setAbilityWord(AbilityWord.COVEN));
    }

    private HarvesttideSentry(final HarvesttideSentry card) {
        super(card);
    }

    @Override
    public HarvesttideSentry copy() {
        return new HarvesttideSentry(this);
    }
}
