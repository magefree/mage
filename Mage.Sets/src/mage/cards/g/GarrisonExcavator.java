package mage.cards.g;

import mage.MageInt;
import mage.abilities.common.CardsLeaveGraveyardTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.Spirit22RedWhiteToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GarrisonExcavator extends CardImpl {

    public GarrisonExcavator(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}");

        this.subtype.add(SubType.ORC);
        this.subtype.add(SubType.SORCERER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Menace
        this.addAbility(new MenaceAbility());

        // Whenever one or more cards leave your graveyard, create a 2/2 red and white Spirit creature token.
        this.addAbility(new CardsLeaveGraveyardTriggeredAbility(new CreateTokenEffect(new Spirit22RedWhiteToken())));
    }

    private GarrisonExcavator(final GarrisonExcavator card) {
        super(card);
    }

    @Override
    public GarrisonExcavator copy() {
        return new GarrisonExcavator(this);
    }
}
