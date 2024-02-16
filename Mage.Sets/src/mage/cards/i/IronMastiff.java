package mage.cards.i;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.SourcePermanentPowerCount;
import mage.abilities.effects.common.DamageControllerEffect;
import mage.abilities.effects.common.DamagePlayersEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.RollDieWithResultTableEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.players.Player;

import java.util.Objects;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class IronMastiff extends CardImpl {

    public IronMastiff(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{4}");

        this.subtype.add(SubType.DOG);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Whenever Iron Mastiff attacks, roll a d20 for each player being attacked and ignore all but the highest roll.
        // 1-9 | Iron Mastiff deals damage equal to its power to you.
        // 10-19 | Iron Mastiff deals damage equal to its power to defending player.
        // 20 | Iron Mastiff deals damage equal to its power to each opponent.
        this.addAbility(new AttacksTriggeredAbility(
                new IronMastiffEffect(), false, null, SetTargetPointer.PLAYER
        ));
    }

    private IronMastiff(final IronMastiff card) {
        super(card);
    }

    @Override
    public IronMastiff copy() {
        return new IronMastiff(this);
    }
}

class IronMastiffEffect extends RollDieWithResultTableEffect {

    private static final DynamicValue xValue = new SourcePermanentPowerCount(false);

    IronMastiffEffect() {
        super(20, "roll a d20 for each player being attacked and ignore all but the highest roll");
        this.addTableEntry(1, 9, new DamageControllerEffect(xValue)
                .setText("{this} deals damage equal to its power to you"));
        this.addTableEntry(10, 19, new DamageTargetEffect(xValue)
                .setText("{this} deals damage equal to its power to defending player"));
        this.addTableEntry(20, 20, new DamagePlayersEffect(
                Outcome.Damage, xValue, TargetController.OPPONENT
        ).setText("{this} deals damage equal to its power to each opponent"));
    }

    private IronMastiffEffect(final IronMastiffEffect effect) {
        super(effect);
    }

    @Override
    public IronMastiffEffect copy() {
        return new IronMastiffEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        int toRoll = game
                .getCombat()
                .getDefenders()
                .stream()
                .map(game::getPlayer)
                .filter(Objects::nonNull)
                .mapToInt(x -> 1)
                .sum();
        if (toRoll < 1) {
            return false;
        }
        int result = player.rollDice(outcome, source, game, sides, toRoll, toRoll - 1).get(0);
        this.applyResult(result, game, source);
        return true;
    }
}
