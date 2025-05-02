package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.abilities.costs.common.SacrificeAttachmentCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.DoWhenCostPaid;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.effects.common.continuous.GainAbilityWithAttachmentEffect;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.Game;
import mage.target.common.TargetAnyTarget;

import java.util.UUID;

/**
 * @author xenohedron
 */
public final class SunfireTorch extends CardImpl {

    public SunfireTorch(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{R}");
        
        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature gets +1/+0 and has "Whenever this creature attacks, you may sacrifice Sunfire Torch. When you do, this creature deals 2 damage to any target."
        Ability ability = new SimpleStaticAbility(new BoostEquippedEffect(1, 0));
        ability.addEffect(new SunfireTorchEffect());
        this.addAbility(ability);

        // Equip {1}
        this.addAbility(new EquipAbility(1));
    }

    private SunfireTorch(final SunfireTorch card) {
        super(card);
    }

    @Override
    public SunfireTorch copy() {
        return new SunfireTorch(this);
    }
}

class SunfireTorchEffect extends GainAbilityWithAttachmentEffect {

    SunfireTorchEffect() {
        super("and has \"Whenever this creature attacks, you may sacrifice {this}. " +
                        "When you do, this creature deals 2 damage to any target.\"",
                (Effect) null, null, new SacrificeAttachmentCost());
    }

    private SunfireTorchEffect(final SunfireTorchEffect effect) {
        super(effect);
    }

    @Override
    public SunfireTorchEffect copy() {
        return new SunfireTorchEffect(this);
    }

    @Override
    protected Ability makeAbility(Game game, Ability source) {
        if (source == null || game == null || source.getSourcePermanentIfItStillExists(game) == null) {
            return null;
        }
        String sourceName = source.getSourcePermanentIfItStillExists(game).getName();
        ReflexiveTriggeredAbility ability = new ReflexiveTriggeredAbility(
                new DamageTargetEffect(2, "this creature"), false
        );
        ability.addTarget(new TargetAnyTarget());

        return new AttacksTriggeredAbility(new DoWhenCostPaid(
                ability, useAttachedCost.copy().setMageObjectReference(source, game),
                "Sacrifice " + sourceName + "?"
        ), false, "Whenever this creature attacks, you may sacrifice "
                + sourceName + ". When you do, this creature deals 2 damage to any target.");
    }
}
