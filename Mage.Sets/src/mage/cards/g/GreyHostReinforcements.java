package mage.cards.g;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.WardAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.common.FilterCreatureCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;

import java.util.UUID;

/**
 * @author PurpleCrowbar
 */
public final class GreyHostReinforcements extends CardImpl {

    public GreyHostReinforcements(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}");
        this.subtype.add(SubType.SPIRIT, SubType.SOLDIER);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Ward {3}
        this.addAbility(new WardAbility(new ManaCostsImpl<>("{3}"), false));

        // When Grey Host Reinforcements enters the battlefield, exile target player's graveyard. Put a number
        // of +1/+1 counters on Grey Host Reinforcements equal to the number of creature cards exiled this way.
        Ability ability = new EntersBattlefieldTriggeredAbility(new GreyHostReinforcementsEffect());
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);
    }

    private GreyHostReinforcements(final GreyHostReinforcements card) {
        super(card);
    }

    @Override
    public GreyHostReinforcements copy() {
        return new GreyHostReinforcements(this);
    }
}

class GreyHostReinforcementsEffect extends OneShotEffect {

    public GreyHostReinforcementsEffect() {
        super(Outcome.Benefit);
        this.staticText = "exile target player's graveyard. Put a number of +1/+1 counters " +
                "on {this} equal to the number of creature cards exiled this way";
    }

    public GreyHostReinforcementsEffect(final GreyHostReinforcementsEffect effect) {
        super(effect);
    }

    @Override
    public GreyHostReinforcementsEffect copy() {
        return new GreyHostReinforcementsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Player player = game.getPlayer(this.getTargetPointer().getFirst(game, source));
        if (controller == null || player == null) {
            return false;
        }
        int totalCreatures = player.getGraveyard().count(new FilterCreatureCard(), game);
        controller.moveCards(player.getGraveyard(), Zone.EXILED, source, game);
        return new AddCountersSourceEffect(CounterType.P1P1.createInstance(totalCreatures)).apply(game, source);
    }
}
