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
 * @author TheElk801
 */
public final class SpareDagger extends CardImpl {

    public SpareDagger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}");

        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature gets +1/+0 and has "Whenever this creature attacks, you may sacrifice Spare Dagger. When you do, this creature deals 1 damage to any target."
        Ability ability = new SimpleStaticAbility(new BoostEquippedEffect(1, 0));
        ability.addEffect(new SpareDaggerEffect());
        this.addAbility(ability);

        // Equip {1}
        this.addAbility(new EquipAbility(1));
    }

    private SpareDagger(final SpareDagger card) {
        super(card);
    }

    @Override
    public SpareDagger copy() {
        return new SpareDagger(this);
    }
}

class SpareDaggerEffect extends GainAbilityWithAttachmentEffect {

    SpareDaggerEffect() {
        super("and has \"Whenever this creature attacks, you may sacrifice {this}. " +
                        "When you do, this creature deals 1 damage to any target.\"",
                (Effect) null, null, new SacrificeAttachmentCost());
    }

    private SpareDaggerEffect(final SpareDaggerEffect effect) {
        super(effect);
    }

    @Override
    public SpareDaggerEffect copy() {
        return new SpareDaggerEffect(this);
    }

    @Override
    protected Ability makeAbility(Game game, Ability source) {
        if (source == null || game == null) {
            return null;
        }
        String sourceName = source.getSourcePermanentIfItStillExists(game).getName();
        ReflexiveTriggeredAbility ability = new ReflexiveTriggeredAbility(
                new DamageTargetEffect(1), false,
                "This creature deals 1 damage to any target"
        );
        ability.addTarget(new TargetAnyTarget());

        return new AttacksTriggeredAbility(new DoWhenCostPaid(
                ability, useAttachedCost.copy().setMageObjectReference(source, game),
                "Sacrifice " + sourceName + "?"
        ), false, "Whenever this creature attacks, you may sacrifice "
                + sourceName + ". When you do, this creature deals 1 damage to any target.");
    }
}
