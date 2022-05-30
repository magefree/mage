
package mage.cards.e;

import java.util.UUID;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.MiracleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.permanent.token.AngelToken;

/**
 *
 * @author noxx
 *
 */
public final class EntreatTheAngels extends CardImpl {

    public EntreatTheAngels(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{X}{X}{W}{W}{W}");

        // Create X 4/4 white Angel creature tokens with flying.
        this.getSpellAbility().addEffect(new CreateTokenEffect(new AngelToken(), ManacostVariableValue.REGULAR));

        // Miracle {X}{W}{W}
        this.addAbility(new MiracleAbility(this, new ManaCostsImpl<>("{X}{W}{W}")));
    }

    private EntreatTheAngels(final EntreatTheAngels card) {
        super(card);
    }

    @Override
    public EntreatTheAngels copy() {
        return new EntreatTheAngels(this);
    }
}
