
package mage.cards.o;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author spjspj
 */
public final class OddlyUneven extends CardImpl {

    public OddlyUneven(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{W}{W}");

        // Choose one --
        // * Destroy each creature with an odd number of words in its name. (Hyphenated words are one word.)
        this.getSpellAbility().addEffect(new OddOrEvenEffect(true));
        // * Destroy each creature with an even number of words in its name.
        Mode mode = new Mode(new OddOrEvenEffect(false));
        this.getSpellAbility().addMode(mode);
    }

    private OddlyUneven(final OddlyUneven card) {
        super(card);
    }

    @Override
    public OddlyUneven copy() {
        return new OddlyUneven(this);
    }
}

class OddOrEvenEffect extends OneShotEffect {

    private boolean odd = true;

    public OddOrEvenEffect(boolean odd) {
        super(Outcome.DestroyPermanent);
        this.odd = odd;
        this.staticText = "Destroy each creature with an " + (odd ? "odd" : "even") + " number of words in its name. (Hyphenated words are one word.)";
    }

    public OddOrEvenEffect(final OddOrEvenEffect effect) {
        super(effect);
        this.odd = effect.odd;
    }

    @Override
    public OddOrEvenEffect copy() {
        return new OddOrEvenEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            for (Permanent creature : game.getState().getBattlefield().getActivePermanents(StaticFilters.FILTER_PERMANENT_CREATURE, controller.getId(), game)) {
                // Check the number of words in the name (based on number of spaces)
                if (creature != null) {
                    String name = creature.getName();

                    if (name.equalsIgnoreCase("") && this.odd == false) {
                        creature.destroy(source, game, false);
                    } else {
                        int spaces = name.length() - name.replace(" ", "").length();
                        boolean nameIsOdd = (spaces % 2 == 0);
                        if (this.odd && nameIsOdd || !this.odd && !nameIsOdd) {
                            creature.destroy(source, game, false);
                        }
                    }
                }
            }
        }

        return false;
    }
}
