
package mage.cards.a;

import mage.MageInt;
import mage.abilities.abilityword.LieutenantAbility;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
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
 * @author emerald000
 */
public final class AngelicFieldMarshal extends CardImpl {

    public AngelicFieldMarshal(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}{W}");
        this.subtype.add(SubType.ANGEL);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Lieutenant - As long as you control your commander, Angelic Field Marshal gets +2/+2 and creatures you control have vigilance.
        this.addAbility(new LieutenantAbility(new GainAbilityAllEffect(
                VigilanceAbility.getInstance(), Duration.WhileOnBattlefield,
                StaticFilters.FILTER_CONTROLLED_CREATURES
        ), "and creatures you control have vigilance"));
    }

    private AngelicFieldMarshal(final AngelicFieldMarshal card) {
        super(card);
    }

    @Override
    public AngelicFieldMarshal copy() {
        return new AngelicFieldMarshal(this);
    }
}
