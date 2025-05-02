package mage.cards.j;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAllTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class JawsOfDefeat extends CardImpl {

    public JawsOfDefeat(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{B}");

        // Whenever a creature you control enters, target opponent loses life equal to the difference between that creature's power and its toughness.
        Ability ability = new EntersBattlefieldAllTriggeredAbility(
                new JawsOfDefeatEffect(), StaticFilters.FILTER_CONTROLLED_A_CREATURE
        );
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);
    }

    private JawsOfDefeat(final JawsOfDefeat card) {
        super(card);
    }

    @Override
    public JawsOfDefeat copy() {
        return new JawsOfDefeat(this);
    }
}

class JawsOfDefeatEffect extends OneShotEffect {

    JawsOfDefeatEffect() {
        super(Outcome.Benefit);
        staticText = "target opponent loses life equal to the difference between that creature's power and its toughness";
    }

    private JawsOfDefeatEffect(final JawsOfDefeatEffect effect) {
        super(effect);
    }

    @Override
    public JawsOfDefeatEffect copy() {
        return new JawsOfDefeatEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(getTargetPointer().getFirst(game, source));
        Permanent permanent = (Permanent) getValue("permanentEnteringBattlefield");
        if (player == null || permanent == null) {
            return false;
        }
        int diff = Math.abs(permanent.getToughness().getValue() - permanent.getPower().getValue());
        return diff > 0 && player.loseLife(diff, game, source, false) > 0;
    }
}
