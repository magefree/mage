
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
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
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
        filter.add(SubType.AURA.getPredicate());
        filter.add(new PyramidsPredicate());
    }
    
    public Pyramids(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{6}");

        // {2}: Choose one - Destroy target Aura attached to a land; 
        Effect effect = new DestroyTargetEffect();
        effect.setText("Destroy target Aura attached to a land");
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, effect, new ManaCostsImpl("{2}"));
        //or the next time target land would be destroyed this turn, remove all damage marked on it instead.
        Mode mode = new Mode(new PreventDamageToTargetEffect(Duration.EndOfTurn)); //back in the day this was not technically "damage", hopefully this modern description will work nowadays
        mode.addTarget(new TargetLandPermanent());
        ability.addMode(mode);
        
        this.addAbility(ability);
    }

    private Pyramids(final Pyramids card) {
        super(card);
    }

    @Override
    public Pyramids copy() {
        return new Pyramids(this);
    }
}
class PyramidsPredicate implements ObjectSourcePlayerPredicate<Permanent> {
    @Override
    public boolean apply(ObjectSourcePlayer<Permanent> input, Game game) {
        Permanent attachment = input.getObject();
        if (attachment != null) {
            Permanent permanent = game.getPermanent(attachment.getAttachedTo());
            if (permanent != null && permanent.isLand(game)) {
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
