
package mage.cards.b;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 *
 * @author Backfir3
 */
public final class Bravado extends CardImpl {

    public Bravado(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{1}{R}");
        this.subtype.add(SubType.AURA);

		
        // Enchant creature
		TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);
		
		// Enchanted creature gets +1/+1 for each other creature you control.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BravadoBoostEnchantedEffect()));
    }

    private Bravado(final Bravado card) {
        super(card);
    }

    @Override
    public Bravado copy() {
        return new Bravado(this);
    }
}

class BravadoBoostEnchantedEffect extends ContinuousEffectImpl {

    public BravadoBoostEnchantedEffect() {
        super(Duration.WhileOnBattlefield, Layer.PTChangingEffects_7, SubLayer.ModifyPT_7c, Outcome.BoostCreature);
		staticText = "Enchanted creature gets +1/+1 for each other creature you control";
    }

    public BravadoBoostEnchantedEffect(final BravadoBoostEnchantedEffect effect) {
        super(effect);
    }

    @Override
    public BravadoBoostEnchantedEffect copy() {
        return new BravadoBoostEnchantedEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent();
        int count = game.getBattlefield().count(filter, source.getControllerId(), source, game) - 1;
        if (count > 0) {
            Permanent enchantment = game.getPermanent(source.getSourceId());
            if (enchantment != null && enchantment.getAttachedTo() != null) {
                Permanent creature = game.getPermanent(enchantment.getAttachedTo());
                if (creature != null) {
                    creature.addPower(count);
                    creature.addToughness(count);
                    return true;
                }
            }
        }
        return false;
    }
}
