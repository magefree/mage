
package mage.cards.b;

import mage.MageItem;
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

import java.util.List;
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
        this.addAbility(new SimpleStaticAbility(new BravadoBoostEnchantedEffect()));
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

    BravadoBoostEnchantedEffect() {
        super(Duration.WhileOnBattlefield, Layer.PTChangingEffects_7, SubLayer.ModifyPT_7c, Outcome.BoostCreature);
		staticText = "Enchanted creature gets +1/+1 for each other creature you control";
    }

    private BravadoBoostEnchantedEffect(final BravadoBoostEnchantedEffect effect) {
        super(effect);
    }

    @Override
    public BravadoBoostEnchantedEffect copy() {
        return new BravadoBoostEnchantedEffect(this);
    }

    @Override
    public void applyToObjects(Layer layer, SubLayer sublayer, Ability source, Game game, List<MageItem> affectedObjects) {
        FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent();
        int count = game.getBattlefield().count(filter, source.getControllerId(), source, game) - 1;
        for (MageItem object : affectedObjects) {
            Permanent permanent = (Permanent) object;
            permanent.addPower(count);
            permanent.addToughness(count);
        }
    }

    @Override
    public boolean queryAffectedObjects(Layer layer, Ability source, Game game, List<MageItem> affectedObjects) {
        Permanent enchantment = game.getPermanent(source.getSourceId());
        if (enchantment == null || enchantment.getAttachedTo() == null) {
            return false;
        }
        Permanent permanent = game.getPermanent(enchantment.getAttachedTo());
        if (permanent == null) {
            return false;
        }
        affectedObjects.add(permanent);
        return true;
    }
}
