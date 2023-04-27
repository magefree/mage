package mage.cards.t;

import mage.abilities.dynamicvalue.common.OpponentsCount;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.permanent.token.TyranidWarriorToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TyranidInvasion extends CardImpl {

    public TyranidInvasion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{G}");

        // Create a number of 3/3 green Tyranid Warrior creature tokens with trample equal to the number of opponents you have.
        this.getSpellAbility().addEffect(new CreateTokenEffect(new TyranidWarriorToken(), OpponentsCount.instance));
    }

    private TyranidInvasion(final TyranidInvasion card) {
        super(card);
    }

    @Override
    public TyranidInvasion copy() {
        return new TyranidInvasion(this);
    }
}
