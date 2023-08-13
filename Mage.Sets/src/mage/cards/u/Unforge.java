
package mage.cards.u;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.common.FilterArtifactPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetArtifactPermanent;

/**
 *
 * @author wetterlicht
 */
public final class Unforge extends CardImpl {
    
    private static final FilterArtifactPermanent filter = new FilterArtifactPermanent("Equipment");
    
    static{
        filter.add(SubType.EQUIPMENT.getPredicate());
    }

    public Unforge(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{2}{R}");

        // Destroy target Equipment. If that Equipment was attached to a creature, Unforge deals 2 damage to that creature.
        getSpellAbility().addTarget(new TargetArtifactPermanent(filter));
        getSpellAbility().addEffect(new DestroyTargetEffect());
        getSpellAbility().addEffect(new UnforgeEffect());
    }

    private Unforge(final Unforge card) {
        super(card);
    }

    @Override
    public Unforge copy() {
        return new Unforge(this);
    }
    
}

class UnforgeEffect extends OneShotEffect{
    
    public UnforgeEffect(){
       super(Outcome.Damage);
       staticText = "If that Equipment was attached to a creature, Unforge deals 2 damage to that creature.";
    }
    
    public UnforgeEffect(final UnforgeEffect effect){
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent equipment = getTargetPointer().getFirstTargetPermanentOrLKI(game, source);
        if(equipment != null){
            Permanent creature = game.getPermanent(equipment.getAttachedTo());
            if(creature != null){
              creature.damage(2, source.getSourceId(), source, game, false, true);
              return true;
            }
        }
        
        return false;
    }

    @Override
    public Effect copy() {
        return new UnforgeEffect(this);
    }
    

}
