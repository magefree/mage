
package mage.cards.t;

import mage.MageInt;
import mage.abilities.abilityword.LieutenantAbility;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author emerald000
 */
public final class ThunderfootBaloth extends CardImpl {

    public ThunderfootBaloth(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{G}{G}");
        this.subtype.add(SubType.BEAST);

        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Lieutenant - As long as you control your commander, Thunderfoot Baloth gets +2/+2 and other creatures you control get +2/+2 and have trample.
        this.addAbility(new LieutenantAbility(new BoostControlledEffect(
                2, 2, Duration.WhileOnBattlefield, true
        ), "and other creatures you control get +2/+2").addLieutenantEffect(new GainAbilityAllEffect(
                TrampleAbility.getInstance(), Duration.WhileOnBattlefield,
                StaticFilters.FILTER_CONTROLLED_CREATURES, true
        ), "and have trample"));
    }

    private ThunderfootBaloth(final ThunderfootBaloth card) {
        super(card);
    }

    @Override
    public ThunderfootBaloth copy() {
        return new ThunderfootBaloth(this);
    }
}
