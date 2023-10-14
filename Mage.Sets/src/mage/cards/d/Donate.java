
package mage.cards.d;

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
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.target.common.TargetControlledPermanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author Quercitron
 */
public final class Donate extends CardImpl {

    public Donate(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{2}{U}");

        // Target player gains control of target permanent you control.
        this.getSpellAbility().addEffect(new DonateEffect());
        this.getSpellAbility().addTarget(new TargetPlayer());
        this.getSpellAbility().addTarget(new TargetControlledPermanent());
    }

    private Donate(final Donate card) {
        super(card);
    }

    @Override
    public Donate copy() {
        return new Donate(this);
    }
}

class DonateEffect extends OneShotEffect {

    public DonateEffect() {
        super(Outcome.Detriment);
        this.staticText = "Target player gains control of target permanent you control";
    }
    
    private DonateEffect(final DonateEffect effect) {
        super(effect);
    }

    @Override
    public DonateEffect copy() {
        return new DonateEffect(this);
    }
    
    @Override
    public boolean apply(Game game, Ability source) {        
        Player targetPlayer = game.getPlayer(getTargetPointer().getFirst(game, source));
        Permanent permanent = game.getPermanent(source.getTargets().get(1).getFirstTarget());
        if (targetPlayer != null && permanent != null) {
            ContinuousEffect effect = new GainControlTargetEffect(Duration.Custom, true, targetPlayer.getId());
            effect.setTargetPointer(new FixedTarget(permanent, game));
            game.addEffect(effect, source);
        }
        return true;
    }
    
}
