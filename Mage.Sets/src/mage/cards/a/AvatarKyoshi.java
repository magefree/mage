package mage.cards.a;

import mage.MageInt;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.dynamicvalue.common.GreatestAmongPermanentsValue;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.HexproofAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.abilities.mana.DynamicManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AvatarKyoshi extends CardImpl {

    public AvatarKyoshi(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.AVATAR);
        this.power = new MageInt(5);
        this.toughness = new MageInt(4);
        this.nightCard = true;

        // Lands you control have trample and hexproof.
        Ability ability = new SimpleStaticAbility(new GainAbilityControlledEffect(
                TrampleAbility.getInstance(), Duration.WhileOnBattlefield, StaticFilters.FILTER_LANDS
        ));
        ability.addEffect(new GainAbilityControlledEffect(
                HexproofAbility.getInstance(), Duration.WhileOnBattlefield, StaticFilters.FILTER_LANDS
        ).setText("and hexproof"));
        this.addAbility(ability);

        // {T}: Add X mana of any one color, where X is the greatest power among creatures you control.
        this.addAbility(new DynamicManaAbility(
                Mana.AnyMana(1), GreatestAmongPermanentsValue.POWER_CONTROLLED_CREATURES,
                new TapSourceCost(), "add X mana of any one color, " +
                "where X is the greatest power among creatures you control", true
        ).addHint(GreatestAmongPermanentsValue.POWER_CONTROLLED_CREATURES.getHint()));
    }

    private AvatarKyoshi(final AvatarKyoshi card) {
        super(card);
    }

    @Override
    public AvatarKyoshi copy() {
        return new AvatarKyoshi(this);
    }
}
