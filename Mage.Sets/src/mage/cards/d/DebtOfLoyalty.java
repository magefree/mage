package mage.cards.d;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.common.RegenerateTargetEffect;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author Dilnu
 */
public final class DebtOfLoyalty extends CardImpl {

    public DebtOfLoyalty(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{W}{W}");

        // Regenerate target creature. You gain control of that creature if it regenerates this way.
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addEffect(new DebtOfLoyaltyEffect());
    }

    private DebtOfLoyalty(final DebtOfLoyalty card) {
        super(card);
    }

    @Override
    public DebtOfLoyalty copy() {
        return new DebtOfLoyalty(this);
    }

}

class DebtOfLoyaltyEffect extends RegenerateTargetEffect {
    DebtOfLoyaltyEffect() {
        super();
        this.staticText = "Regenerate target creature. You gain control of that creature if it regenerates this way.";
    }

    private DebtOfLoyaltyEffect(final DebtOfLoyaltyEffect effect) {
        super(effect);
    }

    @Override
    public DebtOfLoyaltyEffect copy() {
        return new DebtOfLoyaltyEffect(this);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Permanent permanent = game.getPermanent(targetPointer.getFirst(game, source));
        if (super.replaceEvent(event, source, game) && permanent != null) {
            GainControlTargetEffect effect = new GainControlTargetEffect(Duration.EndOfGame);
            effect.setTargetPointer(targetPointer);
            game.addEffect(effect, source);
            return true;
        }
        return false;
    }
}
