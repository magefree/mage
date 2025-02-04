package mage.cards.a;

import mage.MageInt;
import mage.abilities.common.AttacksWhileSaddledTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.SaddleAbility;
import mage.abilities.meta.OrTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.permanent.token.ElephantToken;

import java.util.UUID;

/**
 * @author jackd149
 */
public final class AutarchMammoth extends CardImpl {

    public AutarchMammoth(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{G}{G}");

        this.subtype.add(SubType.ELEPHANT);
        this.subtype.add(SubType.MOUNT);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // When this creature enters and whenever it attacks while saddled, create a 3/3 green Elephant creature token.
        this.addAbility(new OrTriggeredAbility(
            Zone.BATTLEFIELD,
            new CreateTokenEffect(new ElephantToken(), 1),
            false,
            "When this creature enters and whenever it attacks while saddled, ",
            new EntersBattlefieldTriggeredAbility(null),
            new AttacksWhileSaddledTriggeredAbility(null)
        ));
        
        // Saddle 5
        this.addAbility(new SaddleAbility(5));
    }

    private AutarchMammoth(final AutarchMammoth card) {
        super(card);
    }

    @Override
    public AutarchMammoth copy() {
        return new AutarchMammoth(this);
    }
}
