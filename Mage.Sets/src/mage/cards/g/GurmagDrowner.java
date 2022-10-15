package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.ExploitCreatureTriggeredAbility;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.abilities.keyword.ExploitAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.PutCards;
import mage.constants.SubType;

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

        // When Gurmag Drowner exploits a creature, look at the top four cards of your library.
        // Put one of them into your hand and the rest into your graveyard.
        this.addAbility(new ExploitCreatureTriggeredAbility(
                new LookLibraryAndPickControllerEffect(4, 1, PutCards.HAND, PutCards.GRAVEYARD)));
    }

    private GurmagDrowner(final GurmagDrowner card) {
        super(card);
    }

    @Override
    public GurmagDrowner copy() {
        return new GurmagDrowner(this);
    }
}
