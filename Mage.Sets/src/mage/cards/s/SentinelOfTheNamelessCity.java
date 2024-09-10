package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldOrAttacksSourceTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.MapToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SentinelOfTheNamelessCity extends CardImpl {

    public SentinelOfTheNamelessCity(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");

        this.subtype.add(SubType.MERFOLK);
        this.subtype.add(SubType.WARRIOR);
        this.subtype.add(SubType.SCOUT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Whenever Sentinel of the Nameless City enters the battlefield or attacks, create a Map token.
        this.addAbility(new EntersBattlefieldOrAttacksSourceTriggeredAbility(new CreateTokenEffect(new MapToken())));
    }

    private SentinelOfTheNamelessCity(final SentinelOfTheNamelessCity card) {
        super(card);
    }

    @Override
    public SentinelOfTheNamelessCity copy() {
        return new SentinelOfTheNamelessCity(this);
    }
}
