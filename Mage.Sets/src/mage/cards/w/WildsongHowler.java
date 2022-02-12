package mage.cards.w;

import mage.MageInt;
import mage.abilities.common.TransformsOrEntersTriggeredAbility;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.abilities.keyword.NightboundAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class WildsongHowler extends CardImpl {

    public WildsongHowler(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "");

        this.subtype.add(SubType.WEREWOLF);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);
        this.color.setGreen(true);
        this.nightCard = true;

        // Whenever this creature enters the battlefield or transforms into Wildsong Howler, look at the top six cards of your library. You may reveal a creature card from among them and put it into your hand. Put the rest on the bottom of your library in a random order.
        this.addAbility(new TransformsOrEntersTriggeredAbility(new LookLibraryAndPickControllerEffect(
                StaticValue.get(5), false, StaticValue.get(1),
                StaticFilters.FILTER_CARD_CREATURE, Zone.LIBRARY, false,
                true, false, Zone.HAND, true, false, false
        ).setText("look at the top six cards of your library. You may reveal a creature card from among them and put it into your hand. Put the rest on the bottom of your library in a random order"), false));

        // Nightbound
        this.addAbility(new NightboundAbility());
    }

    private WildsongHowler(final WildsongHowler card) {
        super(card);
    }

    @Override
    public WildsongHowler copy() {
        return new WildsongHowler(this);
    }
}
