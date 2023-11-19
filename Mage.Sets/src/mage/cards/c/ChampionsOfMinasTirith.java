package mage.cards.c;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfCombatTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.MonarchIsSourceControllerCondition;
import mage.abilities.costs.Cost;
import mage.abilities.decorator.ConditionalTriggeredAbility;
import mage.abilities.dynamicvalue.common.CardsInTargetPlayerHandCount;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.BecomesMonarchSourceEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.combat.TargetPlayerCantAttackYouEffect;
import mage.abilities.hint.common.MonarchHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.players.Player;
import mage.util.ManaUtil;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class ChampionsOfMinasTirith extends CardImpl {

    public ChampionsOfMinasTirith(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(4);
        this.toughness = new MageInt(6);

        // When Champions of Minas Tirith enters the battlefield, you become the monarch.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new BecomesMonarchSourceEffect()).addHint(MonarchHint.instance));

        // At the beginning of combat on each opponent's turn, if you're the monarch, that opponent may pay {X}, where X is the number of cards in their hand. If they don't, they can't attack you this combat.
        this.addAbility(new ConditionalTriggeredAbility(
                new BeginningOfCombatTriggeredAbility(
                        Zone.BATTLEFIELD,
                        new ChampionsOfMinasTirithEffect(),
                        TargetController.OPPONENT,
                        false,
                        true
                ),
                MonarchIsSourceControllerCondition.instance,
                "At the beginning of combat on each opponent's turn, if you're the monarch, that opponent may pay {X}, "
                        + "where X is the number of cards in their hand. If they don't, they can't attack you this combat."
        ).addHint(MonarchHint.instance));
    }

    private ChampionsOfMinasTirith(final ChampionsOfMinasTirith card) {
        super(card);
    }

    @Override
    public ChampionsOfMinasTirith copy() {
        return new ChampionsOfMinasTirith(this);
    }
}

class ChampionsOfMinasTirithEffect extends OneShotEffect {

    ChampionsOfMinasTirithEffect() {
        super(Outcome.Benefit);
    }

    private ChampionsOfMinasTirithEffect(final ChampionsOfMinasTirithEffect effect) {
        super(effect);
    }

    @Override
    public ChampionsOfMinasTirithEffect copy() {
        return new ChampionsOfMinasTirithEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }

        return new ChampionsOfMinasTirithDoIfCostPaid(
                new TargetPlayerCantAttackYouEffect(Duration.EndOfCombat),
                ManaUtil.createManaCost(CardsInTargetPlayerHandCount.instance, game, source, this),
                "Pay to be able to attack " + player.getName() + " this combat?"
        ).setTargetPointer(targetPointer).apply(game, source);
    }
}

class ChampionsOfMinasTirithDoIfCostPaid extends DoIfCostPaid {

    ChampionsOfMinasTirithDoIfCostPaid(Effect effect, Cost cost, String chooseText) {
        super(null, effect, cost, chooseText, false);
    }

    private ChampionsOfMinasTirithDoIfCostPaid(final ChampionsOfMinasTirithDoIfCostPaid effect) {
        super(effect);
    }

    @Override
    public ChampionsOfMinasTirithDoIfCostPaid copy() {
        return new ChampionsOfMinasTirithDoIfCostPaid(this);
    }

    @Override
    protected Player getPayingPlayer(Game game, Ability source) {
        return game.getPlayer(targetPointer.getFirst(game, source));
    }
}