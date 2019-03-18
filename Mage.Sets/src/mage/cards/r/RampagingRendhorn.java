package mage.cards.r;

import mage.MageInt;
import mage.abilities.keyword.RiotAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RampagingRendhorn extends CardImpl {

    public RampagingRendhorn(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{G}");

        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Riot
        this.addAbility(new RiotAbility());
    }

    private RampagingRendhorn(final RampagingRendhorn card) {
        super(card);
    }

    @Override
    public RampagingRendhorn copy() {
        return new RampagingRendhorn(this);
    }
}
