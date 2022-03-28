
package mage.cards.s;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.filter.common.FilterNonlandPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.PlayerList;
import mage.target.targetpointer.FixedTarget;
import mage.util.RandomUtil;

/**
 *
 * @author nantuko
 */
public final class Scrambleverse extends CardImpl {

    public Scrambleverse(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{6}{R}{R}");

        // For each nonland permanent, choose a player at random. Then each player gains control of each permanent for which they were chosen. Untap those permanents.
        this.getSpellAbility().addEffect(new ScrambleverseEffect());
    }

    private Scrambleverse(final Scrambleverse card) {
        super(card);
    }

    @Override
    public Scrambleverse copy() {
        return new Scrambleverse(this);
    }
}

class ScrambleverseEffect extends OneShotEffect {

    public ScrambleverseEffect() {
        super(Outcome.Damage);
        staticText = "For each nonland permanent, choose a player at random. Then each player gains control of each permanent for which they were chosen. Untap those permanents";
    }

    public ScrambleverseEffect(ScrambleverseEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        PlayerList players = game.getState().getPlayersInRange(source.getControllerId(), game);
        int count = players.size();
        for (Permanent permanent : game.getBattlefield().getActivePermanents(new FilterNonlandPermanent(), source.getControllerId(), source, game)) {
            ContinuousEffect effect = new GainControlTargetEffect(Duration.Custom, true, players.get(RandomUtil.nextInt(count)));
            effect.setTargetPointer(new FixedTarget(permanent, game));
            game.addEffect(effect, source);
            permanent.untap(game);
        }
        return true;
    }

    @Override
    public ScrambleverseEffect copy() {
        return new ScrambleverseEffect(this);
    }
}
