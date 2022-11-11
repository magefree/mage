
package mage.cards.i;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.continuous.BoostEnchantedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author fireshoes
 */
public final class IonasBlessing extends CardImpl {

    public IonasBlessing(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{3}{W}");
        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.AddAbility));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);

        // Enchanted creature gets +2/+2, has vigilance, and can block an additional creature.
        ability = new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostEnchantedEffect(2, 2, Duration.WhileOnBattlefield));
        Effect effect = new GainAbilityAttachedEffect(VigilanceAbility.getInstance(), AttachmentType.AURA);
        effect.setText(", has vigilance");
        ability.addEffect(effect);
        ability.addEffect(new IonasBlessingEffect());
        this.addAbility(ability);
    }

    private IonasBlessing(final IonasBlessing card) {
        super(card);
    }

    @Override
    public IonasBlessing copy() {
        return new IonasBlessing(this);
    }
}

class IonasBlessingEffect extends ContinuousEffectImpl {

    public IonasBlessingEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = ", and can block an additional creature each combat";
    }

    public IonasBlessingEffect(final IonasBlessingEffect effect) {
        super(effect);
    }

    @Override
    public IonasBlessingEffect copy() {
        return new IonasBlessingEffect(this);
    }

    @Override
    public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
        Permanent perm = game.getPermanent(source.getSourceId());
        if (perm != null && perm.getAttachedTo() != null) {
            Permanent enchanted = game.getPermanent(perm.getAttachedTo());
            if (enchanted != null) {
                switch (layer) {
                    case RulesEffects:
                        // maxBlocks = 0 equals to "can block any number of creatures"
                        if (enchanted.getMaxBlocks() > 0) {
                            enchanted.setMaxBlocks(enchanted.getMaxBlocks() + 1);
                        }
                        break;
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public boolean hasLayer(Layer layer) {
        return layer == Layer.RulesEffects;
    }

}
