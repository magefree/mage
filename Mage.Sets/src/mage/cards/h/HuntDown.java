
package mage.cards.h;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.RequirementEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;
import mage.watchers.common.BlockedAttackerWatcher;

/**
 *
 * @author jeffwadsworth
 */
public final class HuntDown extends CardImpl {
    
    private static final FilterCreaturePermanent filterMustBlock = new FilterCreaturePermanent("Creature that must block");
    private static final FilterCreaturePermanent filterToBeBlocked = new FilterCreaturePermanent("Creature that is to be blocked");
    
    

    public HuntDown(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{G}");
        

        // Target creature blocks target creature this turn if able.
        this.getSpellAbility().addEffect(new HuntDownEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(filterMustBlock));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(filterToBeBlocked));
        this.getSpellAbility().addWatcher(new BlockedAttackerWatcher());
        
    }

    private HuntDown(final HuntDown card) {
        super(card);
    }

    @Override
    public HuntDown copy() {
        return new HuntDown(this);
    }
}

class HuntDownEffect extends RequirementEffect {

    public HuntDownEffect() {
        this(Duration.EndOfTurn);
    }

    public HuntDownEffect(Duration duration) {
        super(duration);
        staticText = "Target creature blocks target creature this turn if able";
    }

    private HuntDownEffect(final HuntDownEffect effect) {
        super(effect);
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        if (permanent.getId().equals(source.getTargets().get(0).getFirstTarget())) {
            Permanent blocker = game.getPermanent(source.getTargets().get(0).getFirstTarget());
            if (blocker != null 
                    && blocker.canBlock(source.getTargets().get(1).getFirstTarget(), game)) {              
                Permanent attacker = game.getPermanent(source.getTargets().get(1).getFirstTarget());
                if (attacker != null) {
                    BlockedAttackerWatcher blockedAttackerWatcher = game.getState().getWatcher(BlockedAttackerWatcher.class);
                    if (blockedAttackerWatcher != null 
                            && blockedAttackerWatcher.creatureHasBlockedAttacker(attacker, blocker, game)) {
                        // has already blocked this turn, so no need to do again
                        return false;
                    }                
                    return true;
                } else {
                    discard();
                }
            }
        }
        return false;
    }

    @Override
    public boolean mustAttack(Game game) {
        return false;
    }

    @Override
    public boolean mustBlock(Game game) {
        return true;
    }

    @Override
    public UUID mustBlockAttacker(Ability source, Game game) {  
        return source.getTargets().get(1).getFirstTarget();
    }

    @Override
    public HuntDownEffect copy() {
        return new HuntDownEffect(this);
    }

}
