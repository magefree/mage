package mage.cards.g;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 *
 * @author weirddan455
 */
public class GnarledGrovestrider extends CardImpl {

    public GnarledGrovestrider(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "");
        this.subtype.add(SubType.TREEFOLK);
        this.color.setGreen(true);

        // Back half of Dormant Grove
        this.nightCard = true;

        this.power = new MageInt(3);
        this.toughness = new MageInt(6);

        this.addAbility(VigilanceAbility.getInstance());

        // Other creatures you control have vigilance.
        this.addAbility(new SimpleStaticAbility(new GainAbilityControlledEffect(
                VigilanceAbility.getInstance(), Duration.WhileOnBattlefield,
                StaticFilters.FILTER_PERMANENT_CREATURES, true
        )));
    }

    private GnarledGrovestrider(final GnarledGrovestrider card) {
        super(card);
    }

    @Override
    public GnarledGrovestrider copy() {
        return new GnarledGrovestrider(this);
    }
}
