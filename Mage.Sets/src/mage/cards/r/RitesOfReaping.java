
package mage.cards.r;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.other.AnotherTargetPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class RitesOfReaping extends CardImpl {

    public RitesOfReaping(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{4}{B}{G}");


        // Target creature gets +3/+3 until end of turn. Another target creature gets -3/-3 until end of turn.
        this.getSpellAbility().addEffect(new RitesOfReapingEffect());
        
        FilterCreaturePermanent filter1 = new FilterCreaturePermanent("creature (gets +3/+3 until end of turn)");
        TargetCreaturePermanent target1 = new TargetCreaturePermanent(filter1);
        target1.setTargetTag(1);
        this.getSpellAbility().addTarget(target1);
        
        FilterCreaturePermanent filter2 = new FilterCreaturePermanent("another creature (gets -3/-3 until end of turn)");
        filter2.add(new AnotherTargetPredicate(2));
        TargetCreaturePermanent target2 = new TargetCreaturePermanent(filter2);
        target2.setTargetTag(2);
        this.getSpellAbility().addTarget(target2);
    }

    private RitesOfReaping(final RitesOfReaping card) {
        super(card);
    }

    @Override
    public RitesOfReaping copy() {
        return new RitesOfReaping(this);
    }
}

class RitesOfReapingEffect extends ContinuousEffectImpl {

    public RitesOfReapingEffect() {
        super(Duration.EndOfTurn, Layer.PTChangingEffects_7, SubLayer.ModifyPT_7c, Outcome.BoostCreature);
        this.staticText = "Target creature gets +3/+3 until end of turn. Another target creature gets -3/-3 until end of turn";
    }

    private RitesOfReapingEffect(final RitesOfReapingEffect effect) {
        super(effect);
    }

    @Override
    public RitesOfReapingEffect copy() {
        return new RitesOfReapingEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        if (permanent != null) {
            permanent.addPower(3);
            permanent.addToughness(3);
        }
        permanent = game.getPermanent(source.getTargets().get(1).getFirstTarget());
        if (permanent != null) {
            permanent.addPower(-3);
            permanent.addToughness(-3);
        }
        return true;
    }
}
