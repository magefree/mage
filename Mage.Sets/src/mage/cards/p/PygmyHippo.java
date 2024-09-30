package mage.cards.p;

import mage.MageInt;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.AttacksAndIsNotBlockedTriggeredAbility;
import mage.abilities.common.delayed.AtTheBeginOfMainPhaseDelayedTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.TargetPlayerActivatesAllManaAbilitiesEffect;
import mage.abilities.effects.common.continuous.AssignNoCombatDamageSourceEffect;
import mage.abilities.effects.mana.AddManaToManaPoolSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author xenohedron
 */
public final class PygmyHippo extends CardImpl {

    public PygmyHippo(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{G}{U}");

        this.subtype.add(SubType.HIPPO);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever Pygmy Hippo attacks and isn't blocked, you may have defending player activate a mana ability of each land they control and lose all unspent mana. If you do, Pygmy Hippo assigns no combat damage this turn and at the beginning of your next main phase this turn, you add an amount of {C} equal to the amount of mana that player lost this way.
        this.addAbility(new AttacksAndIsNotBlockedTriggeredAbility(new PygmyHippoEffect(), true, SetTargetPointer.PLAYER));
    }

    private PygmyHippo(final PygmyHippo card) {
        super(card);
    }

    @Override
    public PygmyHippo copy() {
        return new PygmyHippo(this);
    }
}

class PygmyHippoEffect extends OneShotEffect {

    PygmyHippoEffect() {
        super(Outcome.PutManaInPool);
        this.staticText = "you may have defending player activate a mana ability of each land they control" +
                " and lose all unspent mana. If you do, {this} assigns no combat damage this turn" +
                " and at the beginning of your next main phase this turn, you add an amount of {C} equal to" +
                " the amount of mana that player lost this way";
    }

    private PygmyHippoEffect(final PygmyHippoEffect effect) {
        super(effect);
    }

    @Override
    public PygmyHippoEffect copy() {
        return new PygmyHippoEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player targetPlayer = game.getPlayer(getTargetPointer().getFirst(game, source));
        if (targetPlayer == null) {
            return false;
        }
        new TargetPlayerActivatesAllManaAbilitiesEffect().setTargetPointer(this.getTargetPointer().copy())
                .apply(game, source);
        int amountToAdd = targetPlayer.getManaPool().count();
        targetPlayer.getManaPool().emptyPool(game);

        game.addEffect(new AssignNoCombatDamageSourceEffect(Duration.EndOfTurn, true), source);

        if (amountToAdd > 0) {
            DelayedTriggeredAbility delayed = new AtTheBeginOfMainPhaseDelayedTriggeredAbility(
                    new AddManaToManaPoolSourceControllerEffect(new Mana(ManaType.COLORLESS, amountToAdd)),
                    false, TargetController.YOU,
                    AtTheBeginOfMainPhaseDelayedTriggeredAbility.PhaseSelection.NEXT_MAIN_THIS_TURN);
            game.addDelayedTriggeredAbility(delayed, source);
        }
        return true;
    }
}
