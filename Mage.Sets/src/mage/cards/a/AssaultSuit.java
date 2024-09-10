package mage.cards.a;

import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.ContinuousEffectImpl;
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
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

import java.util.Optional;
import java.util.UUID;

/**
 * @author LevelX2
 */
public final class AssaultSuit extends CardImpl {

    public AssaultSuit(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{4}");
        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature gets +2/+2, has haste, can't attack you or a planeswalker you control, and can't be sacrificed.
        Ability ability = new SimpleStaticAbility(new BoostEquippedEffect(2, 2));
        ability.addEffect(new GainAbilityAttachedEffect(
                HasteAbility.getInstance(), AttachmentType.EQUIPMENT
        ).setText(", has haste"));
        ability.addEffect(new CantAttackControllerAttachedEffect(AttachmentType.EQUIPMENT, true)
                .setText(", can't attack you or planeswalkers you control"));
        ability.addEffect(new AssaultSuitCantBeSacrificed());
        this.addAbility(ability);

        // At the beginning of each opponent's upkeep, you may have that player gain control of equipped creature until end of turn. If you do, untap it.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(
                new AssaultSuitGainControlEffect(), TargetController.OPPONENT, false
        ));

        // Equip {3}
        this.addAbility(new EquipAbility(3, false));
    }

    private AssaultSuit(final AssaultSuit card) {
        super(card);
    }

    @Override
    public AssaultSuit copy() {
        return new AssaultSuit(this);
    }
}

class AssaultSuitCantBeSacrificed extends ContinuousEffectImpl {

    public AssaultSuitCantBeSacrificed() {
        super(Duration.WhileOnBattlefield, Layer.RulesEffects, SubLayer.NA, Outcome.Benefit);
        staticText = ", and can't be sacrificed";
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
        Optional.ofNullable(source.getSourcePermanentIfItStillExists(game))
                .map(Permanent::getAttachedTo)
                .map(game::getPermanent)
                .ifPresent(permanent -> permanent.setCanBeSacrificed(false));
        return true;
    }
}

class AssaultSuitGainControlEffect extends OneShotEffect {

    AssaultSuitGainControlEffect() {
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
        Permanent equipment = source.getSourcePermanentIfItStillExists(game);
        if (controller == null || activePlayer == null || equipment == null) {
            return false;
        }
        if (equipment.getAttachedTo() == null) {
            return true;
        }
        Permanent equippedCreature = game.getPermanent(equipment.getAttachedTo());
        if (equippedCreature == null || !controller.chooseUse(outcome,
                "Have " + activePlayer.getLogName() + " gain control of " + equippedCreature.getLogName() + '?', source, game)) {
            return true;
        }
        equippedCreature.untap(game);
        ContinuousEffect effect = new GainControlTargetEffect(Duration.EndOfTurn, activePlayer.getId());
        effect.setTargetPointer(new FixedTarget(equipment.getAttachedTo(), game));
        game.addEffect(effect, source);
        return true;


    }
}
