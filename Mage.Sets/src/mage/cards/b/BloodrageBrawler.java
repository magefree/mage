
package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.discard.DiscardControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Styxo
 */
public final class BloodrageBrawler extends CardImpl {

    public BloodrageBrawler(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");
        
        this.subtype.add(SubType.MINOTAUR, SubType.WARRIOR);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // When Bloodfury Militant enters the battlefield, discard a card.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new DiscardControllerEffect(1)));
    }

    private BloodrageBrawler(final BloodrageBrawler card) {
        super(card);
    }

    @Override
    public BloodrageBrawler copy() {
        return new BloodrageBrawler(this);
    }
}
