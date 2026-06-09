package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.keyword.ConniveSourceEffect;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class AtlanteanSkirmisher extends CardImpl {

    public AtlanteanSkirmisher(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}");

        this.subtype.add(SubType.MERFOLK);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // Whenever this creature attacks, it connives.
        this.addAbility(new AttacksTriggeredAbility(new ConniveSourceEffect()));
    }

    private AtlanteanSkirmisher(final AtlanteanSkirmisher card) {
        super(card);
    }

    @Override
    public AtlanteanSkirmisher copy() {
        return new AtlanteanSkirmisher(this);
    }
}
