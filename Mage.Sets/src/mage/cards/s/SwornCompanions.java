package mage.cards.s;

import java.util.UUID;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.permanent.token.SoldierLifelinkToken;

/**
 *
 * @author TheElk801
 */
public final class SwornCompanions extends CardImpl {

    public SwornCompanions(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{W}");

        // Creature two 1/1 white Soldier creature tokens with lifelink.
        this.getSpellAbility().addEffect(
                new CreateTokenEffect(new SoldierLifelinkToken(), 2)
        );
    }

    private SwornCompanions(final SwornCompanions card) {
        super(card);
    }

    @Override
    public SwornCompanions copy() {
        return new SwornCompanions(this);
    }
}
