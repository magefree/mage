package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.common.AttacksOrBlocksAttachedTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.continuous.BoostEnchantedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 *
 * @author muz
 */
public final class SuperSoldierSerum extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledPermanent(SubType.EQUIPMENT);

    public SuperSoldierSerum(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{W}");

        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        this.addAbility(new EnchantAbility(auraTarget));

        // Enchanted creature gets +2/+2, has first strike and vigilance, and is a legendary Soldier in addition to its other types.
        Ability ability = new SimpleStaticAbility(new BoostEnchantedEffect(2, 2));
        ability.addEffect(new GainAbilityAttachedEffect(
                FirstStrikeAbility.getInstance(), AttachmentType.AURA
        ).setText(", has first strike"));
        ability.addEffect(new GainAbilityAttachedEffect(
                VigilanceAbility.getInstance(), AttachmentType.AURA
        ).setText("and vigilance"));
        ability.addEffect(new SuperSoldierSerumTypeEffect());
        this.addAbility(ability);

        // Whenever enchanted creature attacks or blocks, attach any number of target Equipment you control to it.
        ability = new AttacksOrBlocksAttachedTriggeredAbility(new SuperSoldierSerumAttachEffect(), AttachmentType.AURA);
        ability.addTarget(new TargetPermanent(0, Integer.MAX_VALUE, filter));
        this.addAbility(ability);
    }

    private SuperSoldierSerum(final SuperSoldierSerum card) {
        super(card);
    }

    @Override
    public SuperSoldierSerum copy() {
        return new SuperSoldierSerum(this);
    }
}

class SuperSoldierSerumTypeEffect extends ContinuousEffectImpl {

    SuperSoldierSerumTypeEffect() {
        super(Duration.WhileOnBattlefield, Layer.TypeChangingEffects_4, SubLayer.NA, Outcome.Benefit);
        staticText = ", and is a legendary Soldier in addition to its other types";
    }

    private SuperSoldierSerumTypeEffect(final SuperSoldierSerumTypeEffect effect) {
        super(effect);
    }

    @Override
    public SuperSoldierSerumTypeEffect copy() {
        return new SuperSoldierSerumTypeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = source.getPermanentSourceAttachedToIfItStillExists(game);
        if (permanent == null) {
            return false;
        }
        permanent.addSuperType(game, SuperType.LEGENDARY);
        permanent.addSubType(game, SubType.SOLDIER);
        return true;
    }
}

class SuperSoldierSerumAttachEffect extends OneShotEffect {

    SuperSoldierSerumAttachEffect() {
        super(Outcome.BoostCreature);
        staticText = "attach any number of target Equipment you control to it";
    }

    private SuperSoldierSerumAttachEffect(final SuperSoldierSerumAttachEffect effect) {
        super(effect);
    }

    @Override
    public SuperSoldierSerumAttachEffect copy() {
        return new SuperSoldierSerumAttachEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent creature = source.getPermanentSourceAttachedToIfItStillExists(game);
        if (creature == null) {
            return false;
        }
        for (UUID targetId : getTargetPointer().getTargets(game, source)) {
            Permanent equipment = game.getPermanent(targetId);
            if (equipment != null) {
                creature.addAttachment(equipment.getId(), source, game);
            }
        }
        return true;
    }
}
