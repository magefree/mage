package mage.cards.d;

import mage.MageInt;
import mage.abilities.common.AttacksOrBlocksTriggeredAbility;
import mage.abilities.effects.common.SacrificeControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DaemogothTitan extends CardImpl {

    public DaemogothTitan(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{B/G}{B/G}{B/G}{B/G}");

        this.subtype.add(SubType.DEMON);
        this.power = new MageInt(11);
        this.toughness = new MageInt(10);

        // Whenever Daemogoth Titan attacks or blocks, sacrifice a creature.
        this.addAbility(new AttacksOrBlocksTriggeredAbility(new SacrificeControllerEffect(
                StaticFilters.FILTER_PERMANENT_CREATURE, 1, null
        ), false));
    }

    private DaemogothTitan(final DaemogothTitan card) {
        super(card);
    }

    @Override
    public DaemogothTitan copy() {
        return new DaemogothTitan(this);
    }
}
