package mage.cards.i;

import java.util.UUID;

import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.continuous.BoostEnchantedEffect;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;
import mage.abilities.Ability;
import mage.abilities.effects.common.AttachEffect;
import mage.target.TargetPermanent;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;

/**
 *
 * @author NinthWorld
 */
public final class Irradiation extends CardImpl {

    public Irradiation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{R}");
        
        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        Ability ability = new EnchantAbility(auraTarget.getTargetName());
        this.addAbility(ability);

        // If enchanted creature is an artifact, it gets +2/+0. Otherwise, it gets +2/-2.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new IrradiationEffect()));
    }

    public Irradiation(final Irradiation card) {
        super(card);
    }

    @Override
    public Irradiation copy() {
        return new Irradiation(this);
    }
}

class IrradiationEffect extends ContinuousEffectImpl {

    public IrradiationEffect() {
        super(Duration.WhileOnBattlefield, Outcome.BoostCreature);
    }

    public IrradiationEffect(final IrradiationEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent enchantment = game.getPermanent(source.getSourceId());
        if(enchantment != null) {
            Permanent enchanted = game.getPermanent(enchantment.getAttachedTo());
            if(enchanted != null) {
                enchanted.addPower(2);
                if(!enchanted.isArtifact()) {
                    enchanted.addToughness(-2);
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public IrradiationEffect copy() {
        return new IrradiationEffect(this);
    }
}
