package mage.cards.g;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.effects.common.cost.SpellsCostReductionControllerEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreaturePermanent;

import java.util.UUID;

/**
 * @author jonubuu
 */
public final class GoblinWarchief extends CardImpl {

    private static final FilterCard filterSpells = new FilterCard("Goblin spells");

    static {
        filterSpells.add(SubType.GOBLIN.getPredicate());
    }

    public GoblinWarchief(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}{R}");
        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.WARRIOR);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Goblin spells you cast cost {1} less to cast.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new SpellsCostReductionControllerEffect(filterSpells, 1)));

        // Goblins you control have haste.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GainAbilityControlledEffect(HasteAbility.getInstance(),
                Duration.WhileOnBattlefield, new FilterCreaturePermanent(SubType.GOBLIN, "Goblins"), false)));
    }

    private GoblinWarchief(final GoblinWarchief card) {
        super(card);
    }

    @Override
    public GoblinWarchief copy() {
        return new GoblinWarchief(this);
    }
}
