
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.CantBeCounteredSourceAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.CantBeCounteredControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.FlashAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.FilterSpell;
import mage.filter.StaticFilters;

/**
 *
 * @author LevelX2
 */
public final class SurrakDragonclaw extends CardImpl {

    private static final FilterSpell filterTarget = new FilterSpell("Creature spells you control");

    static {
        filterTarget.add(CardType.CREATURE.getPredicate());
    }

    public SurrakDragonclaw(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}{U}{R}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);

        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // Surrak Dragonclaw can't be countered.
        this.addAbility(new CantBeCounteredSourceAbility());

        // Creature spells you control can't be countered.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new CantBeCounteredControlledEffect(filterTarget, null, Duration.WhileOnBattlefield)));

        // Other creatures you control have trample.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD,
                new GainAbilityControlledEffect(TrampleAbility.getInstance(), Duration.WhileOnBattlefield, StaticFilters.FILTER_PERMANENT_CREATURES, true)));

    }

    private SurrakDragonclaw(final SurrakDragonclaw card) {
        super(card);
    }

    @Override
    public SurrakDragonclaw copy() {
        return new SurrakDragonclaw(this);
    }
}
