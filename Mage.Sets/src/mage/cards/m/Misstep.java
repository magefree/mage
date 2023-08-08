
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
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author djbrez
 */
public final class Misstep extends CardImpl {
    
    public Misstep(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{1}{U}");

        // Creatures target player controls don't untap during that player's next untap step.
        this.getSpellAbility().addEffect(new MisstepEffect());
        this.getSpellAbility().addTarget(new TargetPlayer());
    }

    private Misstep(final Misstep card) {
        super(card);
    }

    @Override
    public Misstep copy() {
        return new Misstep(this);
    }
}

class MisstepEffect extends OneShotEffect {
    
    private static final FilterPermanent filter = new FilterCreaturePermanent();

    MisstepEffect() {
        super(Outcome.Detriment);
        this.staticText = "Creatures target player controls don't untap during their next untap step";
    }
    
    MisstepEffect(final MisstepEffect effect) {
        super(effect);
    }
    
    @Override
    public MisstepEffect copy() {
        return new MisstepEffect(this);
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
