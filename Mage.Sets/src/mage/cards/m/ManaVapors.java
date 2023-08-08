
package mage.cards.m;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DontUntapInPlayersNextUntapStepAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterLandPermanent;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author ciaccona007
 */
public final class ManaVapors extends CardImpl {

    public ManaVapors(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{U}");

        // Lands target player controls don't untap during their next untap step.
        getSpellAbility().addEffect(new ManaVaporsEffect());
        getSpellAbility().addTarget(new TargetPlayer());
    }

    private ManaVapors(final ManaVapors card) {
        super(card);
    }

    @Override
    public ManaVapors copy() {
        return new ManaVapors(this);
    }
}

class ManaVaporsEffect extends OneShotEffect {

    private static final FilterPermanent filter = new FilterLandPermanent();

    ManaVaporsEffect() {
        super(Outcome.Detriment);
        this.staticText = "Lands target player controls don't untap during their next untap step";
    }

    ManaVaporsEffect(final ManaVaporsEffect effect) {
        super(effect);
    }

    @Override
    public ManaVaporsEffect copy() {
        return new ManaVaporsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getFirstTarget());

        if (player != null) {
            ContinuousEffect effect = new DontUntapInPlayersNextUntapStepAllEffect(filter);
            effect.setTargetPointer(new FixedTarget(player.getId()));
            game.addEffect(effect, source);
            return true;
        }
        return false;
    }
}
