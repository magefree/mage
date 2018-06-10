
package mage.cards.m;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author wetterlicht
 */
public final class MurderousSpoils extends CardImpl {
    
    private static final FilterCreaturePermanent FILTER = new FilterCreaturePermanent("nonblack creature");

    static {
        FILTER.add(Predicates.not(new ColorPredicate(ObjectColor.BLACK)));
    }

    public MurderousSpoils(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{5}{B}");

        // Destroy target nonblack creature. It can't be regenerated. You gain control of all Equipment that was attached to it.
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(FILTER));
        this.getSpellAbility().addEffect(new MurderousSpoilsEffect());
        
    }

    public MurderousSpoils(final MurderousSpoils card) {
        super(card);
    }

    @Override
    public MurderousSpoils copy() {
        return new MurderousSpoils(this);
    }
}

class MurderousSpoilsEffect extends OneShotEffect {

    public MurderousSpoilsEffect() {
        super(Outcome.DestroyPermanent);
        staticText = "Destroy target nonblack creature. It can't be regenerated. You gain control of all Equipment that was attached to it.";
    }

    public MurderousSpoilsEffect(final MurderousSpoilsEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent target = game.getPermanent(source.getFirstTarget());
        if (target != null) {
            List<Permanent> attachments = new ArrayList<>();
            for (UUID uuid : target.getAttachments()) {
                Permanent attached = game.getBattlefield().getPermanent(uuid);
                if (attached.hasSubtype(SubType.EQUIPMENT, game)) {
                    attachments.add(attached);
                }
            }
            for (Permanent p : attachments) {
                ContinuousEffect gainControl = new GainControlTargetEffect(Duration.Custom);
                gainControl.setTargetPointer(new FixedTarget(p, game));
                game.addEffect(gainControl, source);
            }
            target.destroy(source.getId(), game, true);
            return true;
        }
        return false;
    }

    @Override
    public MurderousSpoilsEffect copy() {
        return new MurderousSpoilsEffect(this);
    }

}
