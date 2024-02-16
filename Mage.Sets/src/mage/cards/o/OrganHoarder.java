package mage.cards.o;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.PutCards;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class OrganHoarder extends CardImpl {

    public OrganHoarder(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}");

        this.subtype.add(SubType.ZOMBIE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // When Organ Hoarder enters the battlefield, look at the top three cards of your library, then put one of them into your hand and the rest into you graveyard.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new LookLibraryAndPickControllerEffect(
                3, 1, PutCards.HAND, PutCards.GRAVEYARD
        ).setText("look at the top three cards of your library, then put one of them into your hand and the rest into your graveyard")));
    }

    private OrganHoarder(final OrganHoarder card) {
        super(card);
    }

    @Override
    public OrganHoarder copy() {
        return new OrganHoarder(this);
    }
}
