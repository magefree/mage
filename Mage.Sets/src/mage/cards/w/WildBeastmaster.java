
package mage.cards.w;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.dynamicvalue.common.SourcePermanentPowerCount;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.filter.StaticFilters;

/**
 *
 * @author LevelX2
 */
public final class WildBeastmaster extends CardImpl {

    private static final String EFFECT_TEXT = "each other creature you control gets +X/+X until end of turn, where X is {this}'s power";

    public WildBeastmaster(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SHAMAN);
        this.color.setGreen(true);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Whenever Wild Beastmaster attacks, each other creature you control gets +X/+X until end of turn, where X is Wild Beastmaster's power.
        SourcePermanentPowerCount creaturePower = new SourcePermanentPowerCount();
        BoostControlledEffect effect = new BoostControlledEffect(creaturePower, creaturePower, Duration.EndOfTurn, StaticFilters.FILTER_PERMANENT_CREATURE, true);
        effect.setText(EFFECT_TEXT);
        this.addAbility(new AttacksTriggeredAbility(effect, false));
    }

    private WildBeastmaster(final WildBeastmaster card) {
        super(card);
    }

    @Override
    public WildBeastmaster copy() {
        return new WildBeastmaster(this);
    }
}
