
package mage.cards.l;

import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.PreventionEffectImpl;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class LithomancersFocus extends CardImpl {

    public LithomancersFocus(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{W}");

        // Target creature gets +2/+2 until end of turn.
        this.getSpellAbility().addEffect(new BoostTargetEffect(2, 2, Duration.EndOfTurn));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());

        // Prevent all damage that would be dealt to that creature this turn by colorless sources.
        this.getSpellAbility().addEffect(new LithomancersFocusPreventDamageToTargetEffect());
    }

    private LithomancersFocus(final LithomancersFocus card) {
        super(card);
    }

    @Override
    public LithomancersFocus copy() {
        return new LithomancersFocus(this);
    }
}

class LithomancersFocusPreventDamageToTargetEffect extends PreventionEffectImpl {

    public LithomancersFocusPreventDamageToTargetEffect() {
        super(Duration.EndOfTurn, Integer.MAX_VALUE, false);
        staticText = "Prevent all damage that would be dealt to that creature this turn by colorless sources";
    }

    private LithomancersFocusPreventDamageToTargetEffect(final LithomancersFocusPreventDamageToTargetEffect effect) {
        super(effect);
    }

    @Override
    public LithomancersFocusPreventDamageToTargetEffect copy() {
        return new LithomancersFocusPreventDamageToTargetEffect(this);
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (super.applies(event, source, game) && event.getTargetId().equals(targetPointer.getFirst(game, source))) {
            MageObject object = game.getObject(event.getSourceId());
            return object != null && object.getColor(game).isColorless();
        }
        return false;
    }

}
