package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.CardsLeaveGraveyardTriggeredAbility;
import mage.abilities.effects.keyword.ScryEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class StoneboundMentor extends CardImpl {

    public StoneboundMentor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}{W}");

        this.subtype.add(SubType.SPIRIT);
        this.subtype.add(SubType.ADVISOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Whenever one or more cards leave your graveyard, scry 1.
        this.addAbility(new CardsLeaveGraveyardTriggeredAbility(new ScryEffect(1, false)));
    }

    private StoneboundMentor(final StoneboundMentor card) {
        super(card);
    }

    @Override
    public StoneboundMentor copy() {
        return new StoneboundMentor(this);
    }
}
