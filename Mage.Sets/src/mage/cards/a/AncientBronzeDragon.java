package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AncientBronzeDragon extends CardImpl {

    public AncientBronzeDragon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{G}{G}");

        this.subtype.add(SubType.ELDER);
        this.subtype.add(SubType.DRAGON);
        this.power = new MageInt(7);
        this.toughness = new MageInt(7);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Ancient Bronze Dragon deals combat damage to a player, roll a d20. When you do, put X +1/+1 counters on each of up to two target creatures, where X is the result.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(new AncientBronzeDragonEffect(), false));
    }

    private AncientBronzeDragon(final AncientBronzeDragon card) {
        super(card);
    }

    @Override
    public AncientBronzeDragon copy() {
        return new AncientBronzeDragon(this);
    }
}

class AncientBronzeDragonEffect extends OneShotEffect {

    AncientBronzeDragonEffect() {
        super(Outcome.Benefit);
        staticText = "roll a d20. When you do, put X +1/+1 counters " +
                "on each of up to two target creatures, where X is the result";
    }

    private AncientBronzeDragonEffect(final AncientBronzeDragonEffect effect) {
        super(effect);
    }

    @Override
    public AncientBronzeDragonEffect copy() {
        return new AncientBronzeDragonEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        int result = player.rollDice(outcome, source, game, 20);
        ReflexiveTriggeredAbility ability = new ReflexiveTriggeredAbility(
                new AddCountersTargetEffect(CounterType.P1P1.createInstance(result)), false
        );
        ability.addTarget(new TargetCreaturePermanent(0, 2));
        game.fireReflexiveTriggeredAbility(ability, source);
        return true;
    }
}
