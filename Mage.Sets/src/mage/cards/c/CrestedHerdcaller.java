
package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.DinosaurToken;

/**
 *
 * @author LevelX2
 */
public final class CrestedHerdcaller extends CardImpl {

    public CrestedHerdcaller(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}{G}");

        this.subtype.add(SubType.DINOSAUR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // When Crested Herdcaller enters the battlefield, create a 3/3 green Dinosaur creature token with trample.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new DinosaurToken())));
    }

    private CrestedHerdcaller(final CrestedHerdcaller card) {
        super(card);
    }

    @Override
    public CrestedHerdcaller copy() {
        return new CrestedHerdcaller(this);
    }
}
