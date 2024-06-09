package mage.cards.t;

import mage.MageInt;
import mage.abilities.common.CardsLeaveGraveyardTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.PartnerAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.game.permanent.token.ZombieToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TormodTheDesecrator extends CardImpl {

    public TormodTheDesecrator(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(4);
        this.toughness = new MageInt(2);

        // Whenever one or more cards leave your graveyard, create a tapped 2/2 black Zombie creature token.
        this.addAbility(new CardsLeaveGraveyardTriggeredAbility(
                new CreateTokenEffect(new ZombieToken(), 1, true, false)
        ));

        // Partner
        this.addAbility(PartnerAbility.getInstance());
    }

    private TormodTheDesecrator(final TormodTheDesecrator card) {
        super(card);
    }

    @Override
    public TormodTheDesecrator copy() {
        return new TormodTheDesecrator(this);
    }
}
