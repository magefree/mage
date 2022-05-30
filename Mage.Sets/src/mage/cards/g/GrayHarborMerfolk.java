package mage.cards.g;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.hint.ConditionHint;
import mage.abilities.hint.Hint;
import mage.abilities.keyword.CantBeBlockedSourceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreatureOrPlaneswalkerPermanent;
import mage.filter.predicate.mageobject.CommanderPredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GrayHarborMerfolk extends CardImpl {

    private static final FilterPermanent filter = new FilterCreatureOrPlaneswalkerPermanent();

    static {
        filter.add(TargetController.YOU.getControllerPredicate());
        filter.add(CommanderPredicate.instance);
    }

    private static final Condition condition = new PermanentsOnTheBattlefieldCondition(filter);
    private static final Hint hint = new ConditionHint(
            condition, "You control a commander that's a creature or planeswalker"
    );

    public GrayHarborMerfolk(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}");

        this.subtype.add(SubType.MERFOLK);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(0);
        this.toughness = new MageInt(3);

        // Gray Harbor Merfolk can't be blocked.
        this.addAbility(new CantBeBlockedSourceAbility());

        // Gray Harbor Merfolk gets +2/+0 as long as you control a commander that's a creature or planeswalker.
        this.addAbility(new SimpleStaticAbility(new ConditionalContinuousEffect(
                new BoostSourceEffect(2, 0, Duration.WhileOnBattlefield), condition,
                "{this} gets +2/+0 as long as you control a commander that's a creature or planeswalker"
        )));
    }

    private GrayHarborMerfolk(final GrayHarborMerfolk card) {
        super(card);
    }

    @Override
    public GrayHarborMerfolk copy() {
        return new GrayHarborMerfolk(this);
    }
}
