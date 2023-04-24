package mage.cards.h;

import mage.abilities.Ability;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CounterUnlessPaysEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetSpell;
import mage.target.common.TargetOpponent;
import mage.target.targetpointer.FixedTarget;

import java.util.List;
import java.util.UUID;

/**
 * @author Merlingilb
 */
public class Holochess extends CardImpl {
    public Holochess(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{U}");

        //Choose a number between 0 and 3. If the chosen number is less than the number of creatures an opponent controls,
        //draw X cards where X is the chosen number. Otherwise, counter target spell unless its controller pays {X}.
        this.getSpellAbility().addEffect(new HolochessEffect());
        this.getSpellAbility().addTarget(new TargetOpponent());
    }

    public Holochess(Holochess card) {
        super(card);
    }

    @Override
    public Holochess copy() {
        return new Holochess(this);
    }
}

class HolochessEffect extends OneShotEffect {

    HolochessEffect() {
        super(Outcome.Benefit);
        staticText = "Choose a number between 0 and 3. If the chosen number is less than the number of creatures an " +
                "opponent controls, draw X cards where X is the chosen number. Otherwise, counter target spell " +
                "unless its controller pays {X}.";
    }

    private HolochessEffect(final HolochessEffect effect) {
        super(effect);
    }

    @Override
    public HolochessEffect copy() {
        return new HolochessEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Player opponent = game.getPlayer(source.getFirstTarget());
        if (player == null || opponent == null) {
            return false;
        }
        int chosenNumber = player.getAmount(0, 3, "Choose a number between 0 and 3", game);
        List<Permanent> creaturesControlledByOpponent = game.getBattlefield().getActivePermanents(
                StaticFilters.FILTER_PERMANENT_CREATURES, opponent.getId(), game);
        if (chosenNumber < creaturesControlledByOpponent.size()) {
            player.drawCards(chosenNumber, source, game);
        }
        else {
            if (game.getStack().isEmpty()) {
                return false;
            }
            CounterUnlessPaysEffect counterUnlessPaysEffect = new CounterUnlessPaysEffect(
                    new GenericManaCost(chosenNumber));
            TargetSpell targetSpell = new TargetSpell();
            player.chooseTarget(Outcome.Benefit, targetSpell, source, game);
            if (targetSpell.getFirstTarget() == null) {
                return false;
            }
            counterUnlessPaysEffect.setTargetPointer(new FixedTarget(targetSpell.getFirstTarget()));
            return counterUnlessPaysEffect.apply(game, source);
        }
        return true;
    }
}
