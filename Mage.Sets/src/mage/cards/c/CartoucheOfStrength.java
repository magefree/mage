
package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.continuous.BoostEnchantedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetOpponentsCreaturePermanent;

import java.util.UUID;

/**
 *
 * @author stravant
 */
public final class CartoucheOfStrength extends CardImpl {

    public CartoucheOfStrength(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{G}");
        
        this.subtype.add(SubType.AURA, SubType.CARTOUCHE);

        // Enchant creature you control
        TargetPermanent auraTarget = new TargetControlledCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);

        // When Cartouche of Strength enters the battlefield, you may have enchanted creature fight target creature an opponent controls.
        ability = new EntersBattlefieldTriggeredAbility(new FightEnchantedTargetEffect(), /* optional = */true);
        ability.addTarget(new TargetOpponentsCreaturePermanent());
        this.addAbility(ability);

        // Enchanted creature gets +1/+1 and has trample.
        ability = new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostEnchantedEffect(1, 1, Duration.WhileOnBattlefield));
        Effect effect = new GainAbilityAttachedEffect(TrampleAbility.getInstance(), AttachmentType.AURA);
        effect.setText("and has trample");
        ability.addEffect(effect);
        this.addAbility(ability);
    }

    private CartoucheOfStrength(final CartoucheOfStrength card) {
        super(card);
    }

    @Override
    public CartoucheOfStrength copy() {
        return new CartoucheOfStrength(this);
    }
}

/**
 *
 * @author stravant
 */
class FightEnchantedTargetEffect extends OneShotEffect {

    public FightEnchantedTargetEffect() {
        super(Outcome.Damage);
        this.staticText = "you may have enchanted creature fight target creature an opponent controls. " +
                "<i>(Each deals damage equal to its power to the other.)</i>";
    }

    public FightEnchantedTargetEffect(final FightEnchantedTargetEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent sourcePermanent  = game.getPermanent(source.getSourceId());
        if (sourcePermanent != null) {
            Permanent originalCreature = game.getPermanentOrLKIBattlefield(sourcePermanent.getAttachedTo());
            if (originalCreature != null) {
                Permanent enchantedCreature = game.getPermanent(sourcePermanent.getAttachedTo());
                // only if target is legal the effect will be applied
                if (source.getTargets().get(0).isLegal(source, game)) {
                    Permanent creature1 = game.getPermanent(source.getTargets().get(0).getFirstTarget());
                    // 20110930 - 701.10
                    if (creature1 != null && enchantedCreature != null) {
                        if (creature1.isCreature(game) && enchantedCreature.isCreature(game)) {
                            return enchantedCreature.fight(creature1, source, game);
                        }
                    }
                }
                if (!game.isSimulation())
                    game.informPlayers(originalCreature.getLogName() + ": Fighting effect has been fizzled.");
            }
        }
        return false;
    }

    @Override
    public FightEnchantedTargetEffect copy() {
        return new FightEnchantedTargetEffect(this);
    }
}
