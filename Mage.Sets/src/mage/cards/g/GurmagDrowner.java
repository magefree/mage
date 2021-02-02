
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.ExploitCreatureTriggeredAbility;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.abilities.keyword.ExploitAbility;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.StaticFilters;

/**
 *
 * @author fireshoes
 */
public final class GurmagDrowner extends CardImpl {

    public GurmagDrowner(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}");
        this.subtype.add(SubType.NAGA);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // Exploit
        this.addAbility(new ExploitAbility());

        // When Gurmag Drowner exploits a creature, look at the top four cards of your library. Put one of them into your hand and the rest into your graveyard.
        this.addAbility(new ExploitCreatureTriggeredAbility(new LookLibraryAndPickControllerEffect(StaticValue.get(4), false, StaticValue.get(1),
                StaticFilters.FILTER_CARD, Zone.GRAVEYARD, false, false, false, Zone.HAND, false), false));
    }

    private GurmagDrowner(final GurmagDrowner card) {
        super(card);
    }

    @Override
    public GurmagDrowner copy() {
        return new GurmagDrowner(this);
    }
}
