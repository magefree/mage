
package mage.cards.o;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.ActivatedAbility;
import mage.abilities.SpellAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.combat.CantAttackBlockUnlessPaysAttachedEffect;
import mage.abilities.effects.common.cost.CostModificationEffectImpl;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.CostModificationType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;
import mage.util.CardUtil;

/**
 *
 * @author LevelX2
 */
public final class OppressiveRays extends CardImpl {

    public OppressiveRays(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{W}");
        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.Detriment));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);

        // Enchanted creature can't attack or block unless its controller pays {3}.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new CantAttackBlockUnlessPaysAttachedEffect(new ManaCostsImpl<>("{3}"), AttachmentType.AURA)));

        // Activated abilities of enchanted creature cost {3} more to activate.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new OppressiveRaysCostModificationEffect()));
    }

    private OppressiveRays(final OppressiveRays card) {
        super(card);
    }

    @Override
    public OppressiveRays copy() {
        return new OppressiveRays(this);
    }
}

class OppressiveRaysCostModificationEffect extends CostModificationEffectImpl {

    OppressiveRaysCostModificationEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit, CostModificationType.INCREASE_COST);
        staticText = "Activated abilities of enchanted creature cost {3} more to activate";
    }

    OppressiveRaysCostModificationEffect(OppressiveRaysCostModificationEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source, Ability abilityToModify) {
        CardUtil.increaseCost(abilityToModify, 3);
        return true;
    }

    @Override
    public boolean applies(Ability abilityToModify, Ability source, Game game) {
        Permanent creature = game.getPermanent(abilityToModify.getSourceId());
        if (creature != null && creature.getAttachments().contains(source.getSourceId())) {
            if (abilityToModify instanceof ActivatedAbility
                    && !(abilityToModify instanceof SpellAbility)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public OppressiveRaysCostModificationEffect copy() {
        return new OppressiveRaysCostModificationEffect(this);
    }

}
