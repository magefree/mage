package mage.cards.h;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.HorsemanshipAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.FilterPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class HeraldOfHoofbeats extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent(SubType.KNIGHT, "Knights");

    public HeraldOfHoofbeats(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Horsemanship
        this.addAbility(HorsemanshipAbility.getInstance());

        // Other Knights you control have horsemanship.
        this.addAbility(new SimpleStaticAbility(new GainAbilityControlledEffect(
                HorsemanshipAbility.getInstance(), Duration.WhileOnBattlefield, filter, true
        )));
    }

    private HeraldOfHoofbeats(final HeraldOfHoofbeats card) {
        super(card);
    }

    @Override
    public HeraldOfHoofbeats copy() {
        return new HeraldOfHoofbeats(this);
    }
}
