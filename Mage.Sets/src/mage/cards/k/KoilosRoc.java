package mage.cards.k;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.FlashAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.PowerstoneToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class KoilosRoc extends CardImpl {

    public KoilosRoc(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{U}");

        this.subtype.add(SubType.BIRD);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Koilos Roc enters the battlefield, create a tapped Powerstone token.
        this.addAbility(new EntersBattlefieldTriggeredAbility(
                new CreateTokenEffect(new PowerstoneToken(), 1, true)
        ));
    }

    private KoilosRoc(final KoilosRoc card) {
        super(card);
    }

    @Override
    public KoilosRoc copy() {
        return new KoilosRoc(this);
    }
}
