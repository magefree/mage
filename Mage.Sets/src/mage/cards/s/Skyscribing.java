
package mage.cards.s;

import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.common.DrawCardAllEffect;
import mage.abilities.keyword.ForecastAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

import java.util.UUID;

/**
 *
 * @author LevelX2
 */
public final class Skyscribing extends CardImpl {

    public Skyscribing(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{X}{U}{U}");

        // Each player draws X cards.
        this.getSpellAbility().addEffect(new DrawCardAllEffect(ManacostVariableValue.REGULAR));
        // Forecast - {2}{U}, Reveal Skyscribing from your hand: Each player draws a card.
        this.addAbility(new ForecastAbility(new DrawCardAllEffect(1), new ManaCostsImpl<>("{2}{U}")));
    }

    private Skyscribing(final Skyscribing card) {
        super(card);
    }

    @Override
    public Skyscribing copy() {
        return new Skyscribing(this);
    }
}
