package mage.cards.d;

import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.keyword.ConniveSourceEffect;
import mage.abilities.keyword.FlashAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DocOcksHenchmen extends CardImpl {

    public DocOcksHenchmen(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.VILLAIN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // Whenever this creature attacks, it connives.
        this.addAbility(new AttacksTriggeredAbility(new ConniveSourceEffect()));
    }

    private DocOcksHenchmen(final DocOcksHenchmen card) {
        super(card);
    }

    @Override
    public DocOcksHenchmen copy() {
        return new DocOcksHenchmen(this);
    }
}
