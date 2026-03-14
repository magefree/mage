package mage.cards.f;

import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.condition.common.OpponentsTurnCondition;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.permanent.token.SoldierFirebendingToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FireNationOccupation extends CardImpl {

    public FireNationOccupation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{B}");

        // When this enchantment enters, create a 2/2 red Soldier creature token with firebending 1.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new SoldierFirebendingToken())));

        // Whenever you cast a spell during an opponent's turn, create a 2/2 red Soldier creature token with firebending 1.
        this.addAbility(new SpellCastControllerTriggeredAbility(
                new CreateTokenEffect(new SoldierFirebendingToken()), false
        ).withTriggerCondition(OpponentsTurnCondition.instance));
    }

    private FireNationOccupation(final FireNationOccupation card) {
        super(card);
    }

    @Override
    public FireNationOccupation copy() {
        return new FireNationOccupation(this);
    }
}
