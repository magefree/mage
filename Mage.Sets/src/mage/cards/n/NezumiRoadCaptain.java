package mage.cards.n;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.MenaceAbility;
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
public final class NezumiRoadCaptain extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent(SubType.VEHICLE, "Vehicles");

    public NezumiRoadCaptain(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT, CardType.CREATURE}, "");

        this.subtype.add(SubType.RAT);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);
        this.color.setBlack(true);
        this.nightCard = true;

        // Menace
        this.addAbility(new MenaceAbility(false));

        // Vehicles you control have menace.
        this.addAbility(new SimpleStaticAbility(new GainAbilityControlledEffect(
                new MenaceAbility(false), Duration.WhileOnBattlefield, filter
        )));
    }

    private NezumiRoadCaptain(final NezumiRoadCaptain card) {
        super(card);
    }

    @Override
    public NezumiRoadCaptain copy() {
        return new NezumiRoadCaptain(this);
    }
}
