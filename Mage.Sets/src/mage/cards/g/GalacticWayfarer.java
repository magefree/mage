package mage.cards.g;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.LanderToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GalacticWayfarer extends CardImpl {

    public GalacticWayfarer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SCOUT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // When this creature enters, create a Lander token.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new LanderToken())));
    }

    private GalacticWayfarer(final GalacticWayfarer card) {
        super(card);
    }

    @Override
    public GalacticWayfarer copy() {
        return new GalacticWayfarer(this);
    }
}
