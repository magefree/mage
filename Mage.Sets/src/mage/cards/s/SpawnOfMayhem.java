package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DamagePlayersEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.SpectacleAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.counters.CounterType;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SpawnOfMayhem extends CardImpl {

    public SpawnOfMayhem(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}{B}");

        this.subtype.add(SubType.DEMON);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Spectacle {1}{B}{B}
        this.addAbility(new SpectacleAbility(this, new ManaCostsImpl<>("{1}{B}{B}")));

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // At the beginning of your upkeep, Spawn of Mayhem deals 1 damage to each player. Then if you have 10 or less life, put a +1/+1 counter on Spawn of Mayhem.
        Ability ability = new BeginningOfUpkeepTriggeredAbility(
                new DamagePlayersEffect(1, TargetController.ANY),
                TargetController.YOU, false
        );
        ability.addEffect(new SpawnOfMayhemEffect());
        this.addAbility(ability);
    }

    private SpawnOfMayhem(final SpawnOfMayhem card) {
        super(card);
    }

    @Override
    public SpawnOfMayhem copy() {
        return new SpawnOfMayhem(this);
    }
}

class SpawnOfMayhemEffect extends OneShotEffect {

    SpawnOfMayhemEffect() {
        super(Outcome.Benefit);
        staticText = "Then if you have 10 or less life, put a +1/+1 counter on {this}.";
    }

    private SpawnOfMayhemEffect(final SpawnOfMayhemEffect effect) {
        super(effect);
    }

    @Override
    public SpawnOfMayhemEffect copy() {
        return new SpawnOfMayhemEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        if (player.getLife() < 11) {
            return new AddCountersSourceEffect(
                    CounterType.P1P1.createInstance()
            ).apply(game, source);
        }
        return false;
    }
}