package mage.cards.c;

import mage.MageInt;
import mage.abilities.common.BecomesTargetSourceTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.cost.SpellCostReductionSourceEffect;
import mage.abilities.hint.ConditionHint;
import mage.abilities.hint.Hint;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SetTargetPointer;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class Cactarantula extends CardImpl {

    private static final Condition condition = new PermanentsOnTheBattlefieldCondition(
            new FilterPermanent(SubType.DESERT, "you control a Desert")
    );

    private static final Hint hint = new ConditionHint(condition);

    public Cactarantula(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{G}{G}");

        this.subtype.add(SubType.PLANT);
        this.subtype.add(SubType.SPIDER);
        this.power = new MageInt(6);
        this.toughness = new MageInt(5);

        // This spell costs {1} less to cast if you control a Desert.
        this.addAbility(
                new SimpleStaticAbility(Zone.ALL, new SpellCostReductionSourceEffect(1, condition))
                        .setRuleAtTheTop(true)
                        .addHint(hint)
        );

        // Reach
        this.addAbility(ReachAbility.getInstance());

        // Whenever Cactarantula becomes the target of a spell or ability an opponent controls, you may draw a card.
        this.addAbility(new BecomesTargetSourceTriggeredAbility(
                new DrawCardSourceControllerEffect(1),
                StaticFilters.FILTER_SPELL_OR_ABILITY_OPPONENTS,
                SetTargetPointer.NONE, true
        ));
    }

    private Cactarantula(final Cactarantula card) {
        super(card);
    }

    @Override
    public Cactarantula copy() {
        return new Cactarantula(this);
    }
}
