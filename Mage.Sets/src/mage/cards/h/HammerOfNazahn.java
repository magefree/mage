
package mage.cards.h;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SetTargetPointer;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.common.FilterEquipmentPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author Saga
 */
public final class HammerOfNazahn extends CardImpl {
    
    private final static FilterEquipmentPermanent filter = new FilterEquipmentPermanent("{this} or another Equipment");

    public HammerOfNazahn(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{4}");
        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.EQUIPMENT);
        
        // Whenever Hammer of Nazahn or another Equipment enters the battlefiend under your control, you may attach that Equipment to target creature you control.
        Ability ability = new EntersBattlefieldControlledTriggeredAbility(Zone.BATTLEFIELD, new HammerOfNazahnEffect(), filter, true, SetTargetPointer.PERMANENT, "");
        ability.addTarget(new TargetControlledCreaturePermanent());
        this.addAbility(ability);

        // Equipped creature gets +2/+0 and has indestructible.
        Ability abilityEquipped = new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostEquippedEffect(2, 0));
        Effect effect = new GainAbilityAttachedEffect(IndestructibleAbility.getInstance(), AttachmentType.EQUIPMENT);
        effect.setText("and has indestructible");
        abilityEquipped.addEffect(effect);
        this.addAbility(abilityEquipped);

        // Equip {4}
        this.addAbility(new EquipAbility(Outcome.AddAbility, new GenericManaCost(4)));

    }

    public HammerOfNazahn(final HammerOfNazahn card) {
        super(card);
    }

    @Override
    public HammerOfNazahn copy() {
        return new HammerOfNazahn(this);
    }
}

class HammerOfNazahnEffect extends OneShotEffect {

    public HammerOfNazahnEffect() {
        super(Outcome.Benefit);
        this.staticText = "you may attach that Equipment to target creature you control";
    }

    public HammerOfNazahnEffect(final HammerOfNazahnEffect effect) {
        super(effect);
    }

    @Override
    public HammerOfNazahnEffect copy() {
        return new HammerOfNazahnEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Permanent equipment = game.getPermanent(getTargetPointer().getFirst(game, source));
            Permanent targetCreature = game.getPermanent(source.getTargets().getFirstTarget());
            if (equipment != null && targetCreature != null) {
                targetCreature.addAttachment(equipment.getId(), game);
            }
            return true;
        }
        return false;
    }
}
