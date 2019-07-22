package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SerrasGuardian extends CardImpl {

    public SerrasGuardian(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{W}{W}");

        this.subtype.add(SubType.ANGEL);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Other creatures you control have vigilance.
        this.addAbility(new SimpleStaticAbility(
                new GainAbilityControlledEffect(
                        VigilanceAbility.getInstance(),
                        Duration.WhileOnBattlefield,
                        StaticFilters.FILTER_PERMANENT_CREATURES,
                        true
                )
        ));
    }

    public SerrasGuardian(final SerrasGuardian card) {
        super(card);
    }

    @Override
    public SerrasGuardian copy() {
        return new SerrasGuardian(this);
    }
}
