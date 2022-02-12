
package mage.cards.a;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.combat.CantAttackControllerAttachedEffect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author LevelX2
 */
public final class AssaultSuit extends CardImpl {

    public AssaultSuit(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{4}");
        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature gets +2/+2, has haste, can't attack you or a planeswalker you control, and can't be sacrificed.
        Ability ability = new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostEquippedEffect(2, 2));
        Effect effect = new GainAbilityAttachedEffect(HasteAbility.getInstance(), AttachmentType.EQUIPMENT);
        effect.setText(", has haste");
        ability.addEffect(effect);
        effect = new CantAttackControllerAttachedEffect(AttachmentType.EQUIPMENT);
        effect.setText(", can't attack you or planeswalkers you control");
        ability.addEffect(effect);
        effect = new AssaultSuitCantBeSacrificed();
        effect.setText(", and can't be sacrificed");
        ability.addEffect(effect);
        this.addAbility(ability);

        // At the beginning of each opponent's upkeep, you may have that player gain control of equipped creature until end of turn. If you do, untap it.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new AssaultSuitGainControlEffect(), TargetController.OPPONENT, false));

        // Equip {3}
        this.addAbility(new EquipAbility(Outcome.Detriment, new GenericManaCost(3)));
    }

    private AssaultSuit(final AssaultSuit card) {
        super(card);
    }

    @Override
    public AssaultSuit copy() {
        return new AssaultSuit(this);
    }
}

 class AssaultSuitCantBeSacrificed extends ContinuousRuleModifyingEffectImpl {

    public AssaultSuitCantBeSacrificed() {
        super(Duration.WhileOnBattlefield, Outcome.Detriment, true, false);
        staticText = "and can't be sacrificed";
    }

    public AssaultSuitCantBeSacrificed(final AssaultSuitCantBeSacrificed effect) {
        super(effect);
    }

    @Override
    public AssaultSuitCantBeSacrificed copy() {
        return new AssaultSuitCantBeSacrificed(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public String getInfoMessage(Ability source, GameEvent event, Game game) {
        return "This creature can't be sacrificed.";
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (event.getType() == GameEvent.EventType.SACRIFICE_PERMANENT) {
            Permanent equipment = game.getPermanent(source.getSourceId());
            if (equipment != null) {
                return equipment.isAttachedTo(event.getTargetId());
            }
        }
        return false;
    }
}

class AssaultSuitGainControlEffect extends OneShotEffect {

    public AssaultSuitGainControlEffect() {
        super(Outcome.Benefit);
        this.staticText = "you may have that player gain control of equipped creature until end of turn. If you do, untap it";
    }

    public AssaultSuitGainControlEffect(final AssaultSuitGainControlEffect effect) {
        super(effect);
    }

    @Override
    public AssaultSuitGainControlEffect copy() {
        return new AssaultSuitGainControlEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Player activePlayer = game.getPlayer(game.getActivePlayerId());
        Permanent equipment = game.getPermanent(source.getSourceId());
        if (controller != null && activePlayer != null && equipment != null) {
            if (equipment.getAttachedTo() != null) {
                Permanent equippedCreature = game.getPermanent(equipment.getAttachedTo());
                if (equippedCreature != null && controller.chooseUse(outcome,
                        "Let have " + activePlayer.getLogName() + " gain control of " + equippedCreature.getLogName() + '?', source, game)) {
                    equippedCreature.untap(game);
                    ContinuousEffect effect = new GainControlTargetEffect(Duration.EndOfTurn, activePlayer.getId());
                    effect.setTargetPointer(new FixedTarget(equipment.getAttachedTo(), game));
                    game.addEffect(effect, source);
                }
            }
            return true;
        }


        return false;
    }
}
