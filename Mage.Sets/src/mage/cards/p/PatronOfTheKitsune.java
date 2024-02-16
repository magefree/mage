
package mage.cards.p;

import mage.MageInt;
import mage.abilities.common.AttacksAllTriggeredAbility;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.keyword.OfferingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.common.FilterControlledPermanent;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class PatronOfTheKitsune extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent(SubType.FOX, "Fox");

    public PatronOfTheKitsune(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{W}{W}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.SPIRIT);

        this.power = new MageInt(5);
        this.toughness = new MageInt(6);

        // Fox offering (You may cast this card any time you could cast an instant by sacrificing a Fox and paying the difference in mana costs between this and the sacrificed Fox. Mana cost includes color.)
        this.addAbility(new OfferingAbility(filter));

        // Whenever a creature attacks, you may gain 1 life.
        this.addAbility(new AttacksAllTriggeredAbility(new GainLifeEffect(1), true));
    }

    private PatronOfTheKitsune(final PatronOfTheKitsune card) {
        super(card);
    }

    @Override
    public PatronOfTheKitsune copy() {
        return new PatronOfTheKitsune(this);
    }
}
