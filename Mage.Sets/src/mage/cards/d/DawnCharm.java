
package mage.cards.d;

import java.util.UUID;
import mage.abilities.Mode;
import mage.abilities.effects.common.CounterTargetEffect;
import mage.abilities.effects.common.PreventAllDamageByAllPermanentsEffect;
import mage.abilities.effects.common.RegenerateTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.FilterSpell;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.game.Game;
import mage.game.stack.StackObject;
import mage.target.Target;
import mage.target.TargetSpell;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class DawnCharm extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("spell that targets you");

    static {
        filter.add(new DawnCharmPredicate());
    }

    public DawnCharm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{W}");


        // Choose one - Prevent all combat damage that would be dealt this turn
        this.getSpellAbility().addEffect(new PreventAllDamageByAllPermanentsEffect(Duration.EndOfTurn, true));
        // or regenerate target creature;
        Mode mode = new Mode();
        mode.addEffect(new RegenerateTargetEffect());
        mode.addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addMode(mode);
        // or counter target spell that targets you.
        mode = new Mode();
        mode.addEffect(new CounterTargetEffect());
        mode.addTarget(new TargetSpell(filter));
        this.getSpellAbility().addMode(mode);
    }

    private DawnCharm(final DawnCharm card) {
        super(card);
    }

    @Override
    public DawnCharm copy() {
        return new DawnCharm(this);
    }
}

class DawnCharmPredicate implements ObjectSourcePlayerPredicate<ObjectSourcePlayer<StackObject>> {

    @Override
    public boolean apply(ObjectSourcePlayer<StackObject> input, Game game) {
        UUID controllerId = input.getPlayerId();
        if (controllerId == null) {
            return false;
        }

        for (Target target : input.getObject().getStackAbility().getTargets()) {
            for (UUID targetId : target.getTargets()) {
                if (controllerId.equals(targetId)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "spell that targets you";
    }
}
