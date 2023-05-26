package mage.cards.m;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.SourceTappedCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.RestrictionEffect;
import mage.abilities.effects.common.AddContinuousEffectToGame;
import mage.abilities.effects.common.continuous.ChangeMaxNumberThatCanAttackSourceEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MirriWeatherlightDuelist extends CardImpl {

    public MirriWeatherlightDuelist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.CAT);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // First Strike
        this.addAbility(FirstStrikeAbility.getInstance());

        // Whenever Mirri, Weatherlight Duelist attacks, each opponent can't block with more than one creature this combat.
        this.addAbility(new AttacksTriggeredAbility(new AddContinuousEffectToGame(new MirriWeatherlightDuelistBlockRestrictionEffect()), false));

        // As long as Mirri, Weatherlight Duelist is tapped, no more than one creature can attack you each combat.
        Ability ability = new SimpleStaticAbility(Zone.BATTLEFIELD, new ConditionalContinuousEffect(
                new ChangeMaxNumberThatCanAttackSourceEffect(1), SourceTappedCondition.TAPPED,
                "As long as {this} is tapped, no more than one creature can attack you each combat."));
        this.addAbility(ability);
    }

    private MirriWeatherlightDuelist(final MirriWeatherlightDuelist card) {
        super(card);
    }

    @Override
    public MirriWeatherlightDuelist copy() {
        return new MirriWeatherlightDuelist(this);
    }
}

class MirriWeatherlightDuelistBlockRestrictionEffect extends RestrictionEffect {

    MirriWeatherlightDuelistBlockRestrictionEffect() {
        super(Duration.EndOfCombat);
        staticText = "each opponent can't block with more than one creature this combat";
    }

    MirriWeatherlightDuelistBlockRestrictionEffect(final MirriWeatherlightDuelistBlockRestrictionEffect effect) {
        super(effect);
    }

    @Override
    public MirriWeatherlightDuelistBlockRestrictionEffect copy() {
        return new MirriWeatherlightDuelistBlockRestrictionEffect(this);
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        return true;
    }

    @Override
    public boolean canBlock(Permanent attacker, Permanent newBlocker, Ability source, Game game, boolean canUseChooseDialogs) {
        if (attacker == null) {
            return true;
        }
        Player controller = game.getPlayer(attacker.getControllerId());
        if (controller == null) {
            return true;  // Supposed to return false since without the controller this effect should not restrict blockers
        }

        for (UUID existingBlockerId : game.getCombat().getBlockers()) {
            Permanent existingBlocker = game.getPermanent(existingBlockerId);
            if (existingBlocker == null) {
                continue;
            }
            if (controller.hasOpponent(existingBlocker.getControllerId(), game)
                    && existingBlocker.isControlledBy(newBlocker.getControllerId())) {
                return false;
            }
        }
        return true;
    }
}