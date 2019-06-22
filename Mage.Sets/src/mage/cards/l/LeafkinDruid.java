package mage.cards.l;

import mage.MageInt;
import mage.Mana;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.decorator.ConditionalManaEffect;
import mage.abilities.effects.mana.BasicManaEffect;
import mage.abilities.mana.SimpleManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class LeafkinDruid extends CardImpl {

    private static final Condition condition = new PermanentsOnTheBattlefieldCondition(
            StaticFilters.FILTER_CONTROLLED_CREATURE, ComparisonType.MORE_THAN, 3
    );

    public LeafkinDruid(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");

        this.subtype.add(SubType.ELEMENTAL);
        this.subtype.add(SubType.DRUID);
        this.power = new MageInt(0);
        this.toughness = new MageInt(3);

        // {T}: Add {G}. If you control four or more creatures, add {G}{G} instead.
        this.addAbility(new SimpleManaAbility(
                Zone.BATTLEFIELD,
                new ConditionalManaEffect(
                        new BasicManaEffect(Mana.GreenMana(2)),
                        new BasicManaEffect(Mana.GreenMana(1)),
                        condition, "Add {G}. If you control " +
                        "four or more creatures, add {G}{G} instead."
                ), new TapSourceCost()
        ));
    }

    private LeafkinDruid(final LeafkinDruid card) {
        super(card);
    }

    @Override
    public LeafkinDruid copy() {
        return new LeafkinDruid(this);
    }
}
