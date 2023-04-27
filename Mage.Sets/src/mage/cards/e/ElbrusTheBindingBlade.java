package mage.cards.e;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.common.DealsDamageToAPlayerAttachedTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.keyword.TransformAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 * @author BetaSteward
 */
public final class ElbrusTheBindingBlade extends CardImpl {

    public ElbrusTheBindingBlade(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{7}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.EQUIPMENT);

        this.secondSideCardClazz = mage.cards.w.WithengarUnbound.class;
        this.addAbility(new TransformAbility());

        // Equipped creature gets +1/+0.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostEquippedEffect(1, 0)));
        // When equipped creature deals combat damage to a player, unattach Elbrus, the Binding Blade, then transform it.
        this.addAbility(new DealsDamageToAPlayerAttachedTriggeredAbility(new ElbrusTheBindingBladeEffect(), "equipped", true));
        // Equip {1}
        this.addAbility(new EquipAbility(Outcome.BoostCreature, new GenericManaCost(1), new TargetControlledCreaturePermanent(), false));
    }

    private ElbrusTheBindingBlade(final ElbrusTheBindingBlade card) {
        super(card);
    }

    @Override
    public ElbrusTheBindingBlade copy() {
        return new ElbrusTheBindingBlade(this);
    }
}

class ElbrusTheBindingBladeEffect extends OneShotEffect {
    public ElbrusTheBindingBladeEffect() {
        super(Outcome.BecomeCreature);
        staticText = "unattach {this}, then transform it";
    }

    public ElbrusTheBindingBladeEffect(final ElbrusTheBindingBladeEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent equipment = game.getPermanent(source.getSourceId());
        if (equipment != null && equipment.getAttachedTo() != null) {
            Permanent attachedTo = game.getPermanent(equipment.getAttachedTo());
            if (attachedTo != null) {
                attachedTo.removeAttachment(equipment.getId(), source, game);
                equipment.transform(source, game);
            }
        }
        return false;
    }

    @Override
    public ElbrusTheBindingBladeEffect copy() {
        return new ElbrusTheBindingBladeEffect(this);
    }

}