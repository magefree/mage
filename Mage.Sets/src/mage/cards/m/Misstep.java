
package mage.cards.m;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DontUntapInControllersNextUntapStepTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.target.targetpointer.FixedTargets;

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
         List<Permanent> doNotUntapNextUntapStep = new ArrayList<>();
         for (Permanent creature : game.getBattlefield().getAllActivePermanents(StaticFilters.FILTER_PERMANENT_CREATURES, player.getId(), game)) {
           doNotUntapNextUntapStep.add(creature); 
        }
        if (!doNotUntapNextUntapStep.isEmpty()) {
                ContinuousEffect effect = new DontUntapInControllersNextUntapStepTargetEffect("", player.getId());
                effect.setText("those creatures don't untap during that player's next untap step");
                effect.setTargetPointer(new FixedTargets(doNotUntapNextUntapStep, game));
                game.addEffect(effect, source);
             }
             return true;
         }
        return false;
    }
}
