package mage.cards.s;

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
 * @author xenohedron
 */
public final class SageOfDays extends CardImpl {

    public SageOfDays(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");
        
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // When Sage of Days enters the battlefield, look at the top three cards of your library. You may put one of those cards back on top of your library. Put the rest into your graveyard.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new LookLibraryAndPickControllerEffect(
                3, 1, PutCards.TOP_ANY, PutCards.GRAVEYARD, true
        )));

    }

    private SageOfDays(final SageOfDays card) {
        super(card);
    }

    @Override
    public SageOfDays copy() {
        return new SageOfDays(this);
    }
}
