
package mage.cards.p;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.PreventDamageToTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterEnchantmentPermanent;
import mage.filter.predicate.ObjectPlayer;
import mage.filter.predicate.ObjectPlayerPredicate;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetLandPermanent;

/**
 *
 * @author MarcoMarin
 */
public final class Pyramids extends CardImpl {
    
    private static final FilterEnchantmentPermanent filter = new FilterEnchantmentPermanent("Auras attached to a land");
    
    static {
        filter.add(new SubtypePredicate(SubType.AURA));
        filter.add(new PyramidsPredicate());
    }
    
    public Pyramids(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{6}");

        // {2}: Choose one - Destroy target Aura attached to a land; 
        Effect effect = new DestroyTargetEffect();
        effect.setText("Destroy target Aura attached to a land");
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, effect, new ManaCostsImpl("{2}"));
        //or the next time target land would be destroyed this turn, remove all damage marked on it instead.
        Mode mode = new Mode(); //back in the day this was not technically "damage", hopefully this modern description will work nowadays
        mode.getEffects().add(new PreventDamageToTargetEffect(Duration.EndOfTurn));
        mode.getTargets().add(new TargetLandPermanent());
        ability.addMode(mode);
        
        this.addAbility(ability);
    }

    public Pyramids(final Pyramids card) {
        super(card);
    }

    @Override
    public Pyramids copy() {
        return new Pyramids(this);
    }
}
class PyramidsPredicate implements ObjectPlayerPredicate<ObjectPlayer<Permanent>> {
    @Override
    public boolean apply(ObjectPlayer<Permanent> input, Game game) {
        Permanent attachment = input.getObject();
        if (attachment != null) {
            Permanent permanent = game.getPermanent(attachment.getAttachedTo());
            if (permanent != null && permanent.isLand()) {
                return true;
            }
        }
        return false;
    }
    @Override
    public String toString() {
        return "Attached to a land";
    }
}
