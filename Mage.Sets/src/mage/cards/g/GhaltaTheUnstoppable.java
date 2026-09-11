package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.GreatestAmongPermanentsValue;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.effects.common.cost.SpellCostReductionSourceEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;

/**
 *
 * @author muz
 */
public final class GhaltaTheUnstoppable extends CardImpl {

    public GhaltaTheUnstoppable(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{8}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ELDER);
        this.subtype.add(SubType.DINOSAUR);
        this.power = new MageInt(8);
        this.toughness = new MageInt(8);

        // This spell costs {X} less to cast, where X is the greatest power among creatures you control.
        this.addAbility(new SimpleStaticAbility(
            Zone.ALL, new SpellCostReductionSourceEffect(GreatestAmongPermanentsValue.POWER_CONTROLLED_CREATURES)
        ).setRuleAtTheTop(true).addHint(GreatestAmongPermanentsValue.POWER_CONTROLLED_CREATURES.getHint()));

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Other creatures you control have trample.
        this.addAbility(new SimpleStaticAbility(new GainAbilityControlledEffect(
            TrampleAbility.getInstance(),
            Duration.WhileOnBattlefield,
            StaticFilters.FILTER_PERMANENT_CREATURES, true
        )));
    }

    private GhaltaTheUnstoppable(final GhaltaTheUnstoppable card) {
        super(card);
    }

    @Override
    public GhaltaTheUnstoppable copy() {
        return new GhaltaTheUnstoppable(this);
    }
}
