
package mage.cards.e;

import java.util.UUID;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.game.permanent.token.ThopterColorlessToken;

/**
 *
 * @author fireshoes
 */
public final class EfficientConstruction extends CardImpl {

    public EfficientConstruction(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{U}");

        // Whenever you cast an artifact spell, create a 1/1 colorless Thopter artifact creature token with flying.
        this.addAbility(new SpellCastControllerTriggeredAbility(new CreateTokenEffect(new ThopterColorlessToken()), StaticFilters.FILTER_SPELL_AN_ARTIFACT, false));
    }

    private EfficientConstruction(final EfficientConstruction card) {
        super(card);
    }

    @Override
    public EfficientConstruction copy() {
        return new EfficientConstruction(this);
    }
}
