package mage.cards.a;

import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.permanent.token.ForestDryadToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AwakenTheWoods extends CardImpl {

    public AwakenTheWoods(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{X}{G}{G}");

        // Create X 1/1 green Forest Dryad land creature tokens.
        this.getSpellAbility().addEffect(new CreateTokenEffect(new ForestDryadToken(), ManacostVariableValue.REGULAR));
    }

    private AwakenTheWoods(final AwakenTheWoods card) {
        super(card);
    }

    @Override
    public AwakenTheWoods copy() {
        return new AwakenTheWoods(this);
    }
}
