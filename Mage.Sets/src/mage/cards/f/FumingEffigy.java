package mage.cards.f;

import mage.MageInt;
import mage.abilities.common.CardsLeaveGraveyardTriggeredAbility;
import mage.abilities.effects.common.DamagePlayersEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FumingEffigy extends CardImpl {

    public FumingEffigy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}");

        this.subtype.add(SubType.SPIRIT);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // Whenever one or more cards leave your graveyard, Fuming Effigy deals 1 damage to each opponent.
        this.addAbility(new CardsLeaveGraveyardTriggeredAbility(new DamagePlayersEffect(1, TargetController.OPPONENT)));
    }

    private FumingEffigy(final FumingEffigy card) {
        super(card);
    }

    @Override
    public FumingEffigy copy() {
        return new FumingEffigy(this);
    }
}
