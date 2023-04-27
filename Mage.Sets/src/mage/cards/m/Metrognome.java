
package mage.cards.m;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.DiscardedByOpponentTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.game.permanent.token.GnomeToken;

/**
 *
 * @author LoneFox
 */
public final class Metrognome extends CardImpl {

    public Metrognome(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{4}");

        // When a spell or ability an opponent controls causes you to discard Metrognome, create four 1/1 colorless Gnome artifact creature tokens.
        this.addAbility(new DiscardedByOpponentTriggeredAbility(new CreateTokenEffect(new GnomeToken(), 4)));

        // {4}, {tap}: Create a 1/1 colorless Gnome artifact creature token.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new CreateTokenEffect(new GnomeToken()), new ManaCostsImpl<>("{4}"));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    private Metrognome(final Metrognome card) {
        super(card);
    }

    @Override
    public Metrognome copy() {
        return new Metrognome(this);
    }
}
