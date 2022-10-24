
package mage.cards.s;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.BoostEnchantedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author emerald000
 */
public final class SagesReverie extends CardImpl {
    
    private static final FilterControlledPermanent filter = new FilterControlledPermanent("aura you control that's attached to a creature");
    static {
        filter.add(SubType.AURA.getPredicate());
        filter.add(new SagesReveriePredicate());
    }

    public SagesReverie(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{3}{W}");
        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);
        
        // When Sage's Reverie enters the battlefield, draw a card for each aura you control that's attached to a creature.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new DrawCardSourceControllerEffect(new PermanentsOnBattlefieldCount(filter))));
        
        // Enchanted creature gets +1/+1 for each aura you control that's attached to a creature.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostEnchantedEffect(new PermanentsOnBattlefieldCount(filter), new PermanentsOnBattlefieldCount(filter))));
    }

    private SagesReverie(final SagesReverie card) {
        super(card);
    }

    @Override
    public SagesReverie copy() {
        return new SagesReverie(this);
    }
}

class SagesReveriePredicate implements Predicate<Permanent> {

    @Override
    public boolean apply(Permanent input, Game game) {
        UUID attachedTo = input.getAttachedTo();
        Permanent attachedToPermanent = game.getPermanent(attachedTo);
        return attachedToPermanent != null && attachedToPermanent.isCreature(game);
    }

    @Override
    public String toString() {
        return "Attached to a creature";
    }
}
