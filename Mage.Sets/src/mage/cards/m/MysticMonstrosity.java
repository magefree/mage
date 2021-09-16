package mage.cards.m;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.mana.AnyColorManaAbility;
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
public final class MysticMonstrosity extends CardImpl {

    public MysticMonstrosity(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "");

        this.subtype.add(SubType.CONSTRUCT);
        this.power = new MageInt(5);
        this.toughness = new MageInt(6);
        this.transformable = true;
        this.nightCard = true;

        // Lands you control have "{T}: Add one mana of any color."
        this.addAbility(new SimpleStaticAbility(new GainAbilityControlledEffect(
                new AnyColorManaAbility(), Duration.WhileOnBattlefield,
                StaticFilters.FILTER_LANDS, false
        )));
    }

    private MysticMonstrosity(final MysticMonstrosity card) {
        super(card);
    }

    @Override
    public MysticMonstrosity copy() {
        return new MysticMonstrosity(this);
    }
}
