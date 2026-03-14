package mage.cards.f;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.game.permanent.token.FoodToken;
import mage.abilities.common.LeavesBattlefieldTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class FeatherbrainedFilcher extends CardImpl {

    public FeatherbrainedFilcher(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{W}");

        this.subtype.add(SubType.BIRD);
        this.subtype.add(SubType.MUTANT);
        this.power = new MageInt(0);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When this creature leaves the battlefield, create a Food token.
        this.addAbility(new LeavesBattlefieldTriggeredAbility(new CreateTokenEffect(new FoodToken())));
    }

    private FeatherbrainedFilcher(final FeatherbrainedFilcher card) {
        super(card);
    }

    @Override
    public FeatherbrainedFilcher copy() {
        return new FeatherbrainedFilcher(this);
    }
}
