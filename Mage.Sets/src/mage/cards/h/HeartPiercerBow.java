
package mage.cards.h;

import java.util.UUID;
import mage.abilities.common.AttacksAttachedTriggeredAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class HeartPiercerBow extends CardImpl {

    public HeartPiercerBow(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{2}");
        this.subtype.add(SubType.EQUIPMENT);

        // Whenever equipped creature attacks, Heart-Piercer Bow deals 1 damage to target creature defending player controls.
        this.addAbility(new HeartPiercerBowAbility());

        // Equip {1}
        this.addAbility(new EquipAbility(Outcome.Benefit, new GenericManaCost(1)));
    }

    private HeartPiercerBow(final HeartPiercerBow card) {
        super(card);
    }



    @Override
    public HeartPiercerBow copy() {
        return new HeartPiercerBow(this);
    }
}

class HeartPiercerBowAbility extends AttacksAttachedTriggeredAbility {

    public HeartPiercerBowAbility() {
        super(new DamageTargetEffect(1), AttachmentType.EQUIPMENT, false);
    }

    public HeartPiercerBowAbility(final HeartPiercerBowAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (super.checkTrigger(event, game)) {
            Permanent equipment = game.getPermanent(getSourceId());
            if (equipment != null && equipment.getAttachedTo() != null) {
                FilterCreaturePermanent filter = new FilterCreaturePermanent("creature defending player controls");
                UUID defenderId = game.getCombat().getDefendingPlayerId(equipment.getAttachedTo(), game);
                if (defenderId != null) {
                    filter.add(new ControllerIdPredicate(defenderId));
                    this.getTargets().clear();
                    TargetCreaturePermanent target = new TargetCreaturePermanent(filter);
                    this.addTarget(target);
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever equipped creature attacks, {this} deals 1 damage to target creature defending player controls.";
    }

    @Override
    public HeartPiercerBowAbility copy() {
        return new HeartPiercerBowAbility(this);
    }
}