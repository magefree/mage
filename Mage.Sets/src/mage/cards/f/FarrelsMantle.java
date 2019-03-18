
package mage.cards.f;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.AttacksAndIsNotBlockedTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.continuous.AssignNoCombatDamageSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicate;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;
import mage.util.CardUtil;

/**
 *
 * @author MarcoMarin
 */
public final class FarrelsMantle extends CardImpl {

    
    public FarrelsMantle(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{2}{W}");
        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.AddAbility));
        Ability ability = new EnchantAbility(auraTarget.getTargetName());
        this.addAbility(ability);
        
        // Whenever enchanted creature attacks and isn't blocked, its controller may have it deal damage equal to its power plus 2 to another target creature. If that player does, the attacking creature assigns no combat damage this turn.
        FilterPermanent filter = new FilterCreaturePermanent();
        filter.add(Predicates.not(new AttachmentByUUIDPredicate(this.getId()))); 
        
        Ability ability2 = new AttacksAndIsNotBlockedTriggeredAbility(new FarrelsMantleEffect(), true);
        ability2.addTarget(new TargetPermanent(filter));
        ability2.addEffect(new AssignNoCombatDamageSourceEffect(Duration.EndOfTurn));
        
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GainAbilityAttachedEffect(ability2, AttachmentType.AURA)));
        
    }

    public FarrelsMantle(final FarrelsMantle card) {
        super(card);
    }

    @Override
    public FarrelsMantle copy() {
        return new FarrelsMantle(this);
    }
}
class AttachmentByUUIDPredicate implements Predicate<Permanent> {

    private final UUID id;

    public AttachmentByUUIDPredicate(UUID id) {
        this.id = id;
    }

    @Override
    public boolean apply(Permanent input, Game game) {
        return input.getAttachments().contains(id);            
    }

    @Override
    public String toString() {
        return "AttachmentUUID(" + id + ')';
    }
}

class FarrelsMantleEffect extends OneShotEffect{
                      
    public FarrelsMantleEffect() {
        super(Outcome.Damage);
        this.setText("its controller may have it deal damage equal to its power plus 2 to another target creature.");
    }
    
    public FarrelsMantleEffect(final FarrelsMantleEffect effect) {
        super(effect);
    }
    
    @Override
    public FarrelsMantleEffect copy() {
        return new FarrelsMantleEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent perm = game.getPermanent(source.getSourceId());
        int damage = CardUtil.addWithOverflowCheck(perm.getPower().getValue(), 2);
        DamageTargetEffect dmgEffect = new DamageTargetEffect(damage);
        return dmgEffect.apply(game, source);        
    }
}
