package mage.cards.c;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.LoseLifeOpponentsEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterAttackingCreature;
import mage.filter.predicate.mageobject.AnotherPredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CommissarSeverinaRaine extends CardImpl {

    private static final FilterPermanent filter = new FilterAttackingCreature("other attacking creatures");

    static {
        filter.add(AnotherPredicate.instance);
    }

    private static final DynamicValue xValue = new PermanentsOnBattlefieldCount(filter, null);

    public CommissarSeverinaRaine(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Leading from the Front -- Whenever Commissar Severina Raine attacks, each opponent loses X life, where X is the number of other attacking creatures.
        this.addAbility(new AttacksTriggeredAbility(new LoseLifeOpponentsEffect(xValue))
                .withFlavorWord("Leading from the Front"));

        // Summary Execution -- {2}, Sacrifice another creature: You gain 2 life and draw a card.
        Ability ability = new SimpleActivatedAbility(new GainLifeEffect(2), new GenericManaCost(2));
        ability.addCost(new SacrificeTargetCost(StaticFilters.FILTER_CONTROLLED_ANOTHER_CREATURE));
        ability.addEffect(new DrawCardSourceControllerEffect(1).concatBy("and"));
        this.addAbility(ability.withFlavorWord("Summary Execution"));
    }

    private CommissarSeverinaRaine(final CommissarSeverinaRaine card) {
        super(card);
    }

    @Override
    public CommissarSeverinaRaine copy() {
        return new CommissarSeverinaRaine(this);
    }
}
