package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksWithCreaturesTriggeredAbility;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TeoSpiritedGlider extends CardImpl {

    public TeoSpiritedGlider(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.PILOT);
        this.subtype.add(SubType.ALLY);
        this.power = new MageInt(1);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever one or more creatures you control with flying attack, draw a card, then discard a card. When you discard a nonland card this way, put a +1/+1 counter on target creature you control.
        Ability ability = new AttacksWithCreaturesTriggeredAbility(
                new DrawCardSourceControllerEffect(1),
                1, StaticFilters.FILTER_CREATURE_FLYING
        ).setTriggerPhrase("Whenever one or more creatures you control with flying attack, ");
        ability.addEffect(new TeoSpiritedGliderEffect());
        this.addAbility(ability);
    }

    private TeoSpiritedGlider(final TeoSpiritedGlider card) {
        super(card);
    }

    @Override
    public TeoSpiritedGlider copy() {
        return new TeoSpiritedGlider(this);
    }
}

class TeoSpiritedGliderEffect extends OneShotEffect {

    TeoSpiritedGliderEffect() {
        super(Outcome.Benefit);
        staticText = ", then discard a card. When you discard a nonland card this way, " +
                "put a +1/+1 counter on target creature you control";
    }

    private TeoSpiritedGliderEffect(final TeoSpiritedGliderEffect effect) {
        super(effect);
    }

    @Override
    public TeoSpiritedGliderEffect copy() {
        return new TeoSpiritedGliderEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Card card = player.discardOne(false, false, source, game);
        if (card == null) {
            return false;
        }
        if (card.isLand(game)) {
            return true;
        }
        ReflexiveTriggeredAbility ability = new ReflexiveTriggeredAbility(
                new AddCountersTargetEffect(CounterType.P1P1.createInstance()), false
        );
        ability.addTarget(new TargetControlledCreaturePermanent());
        game.fireReflexiveTriggeredAbility(ability, source);
        return true;
    }
}
