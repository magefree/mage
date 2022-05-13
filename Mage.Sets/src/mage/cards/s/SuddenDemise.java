
package mage.cards.s;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.choices.ChoiceColor;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public final class SuddenDemise extends CardImpl {

    public SuddenDemise(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{X}{R}");

        // Choose a color. Sudden Demise deals X damage to each creature of the chosen color.
        this.getSpellAbility().addEffect(new SuddenDemiseDamageEffect());

    }

    private SuddenDemise(final SuddenDemise card) {
        super(card);
    }

    @Override
    public SuddenDemise copy() {
        return new SuddenDemise(this);
    }
}

class SuddenDemiseDamageEffect extends OneShotEffect {

    public SuddenDemiseDamageEffect() {
        super(Outcome.Damage);
        this.staticText = "Choose a color. {this} deals X damage to each creature of the chosen color";
    }

    public SuddenDemiseDamageEffect(final SuddenDemiseDamageEffect effect) {
        super(effect);
    }

    @Override
    public SuddenDemiseDamageEffect copy() {
        return new SuddenDemiseDamageEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        ChoiceColor choice = new ChoiceColor();
        if (controller != null && controller.choose(outcome, choice, game)) {
            final int damage = source.getManaCostsToPay().getX();
            FilterPermanent filter = new FilterCreaturePermanent();
            filter.add(new ColorPredicate(choice.getColor()));
            for (Permanent permanent : game.getBattlefield().getActivePermanents(filter, source.getControllerId(), source, game)) {
                permanent.damage(damage, source.getSourceId(), source, game, false, true);
            }
            return true;
        }
        return false;
    }
}
