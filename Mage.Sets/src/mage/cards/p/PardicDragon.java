
package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SpellCastOpponentTriggeredAbility;
import mage.abilities.condition.common.SuspendedCondition;
import mage.abilities.costs.mana.ColoredManaCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.SuspendAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public final class PardicDragon extends CardImpl {

    public PardicDragon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{R}{R}");
        this.subtype.add(SubType.DRAGON);

        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // {R}: Pardic Dragon gets +1/+0 until end of turn.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new BoostSourceEffect(1, 0, Duration.EndOfTurn), new ColoredManaCost(ColoredManaSymbol.R)));
        // Suspend 2-{R}{R}
        this.addAbility(new SuspendAbility(2, new ManaCostsImpl<>("{R}{R}"), this, true));
        // Whenever an opponent casts a spell, if Pardic Dragon is suspended, that player may put a time counter on Pardic Dragon.
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(
                new SpellCastOpponentTriggeredAbility(Zone.EXILED, new PardicDragonEffect(), StaticFilters.FILTER_SPELL, false, SetTargetPointer.PLAYER),
                SuspendedCondition.instance,
                "Whenever an opponent casts a spell, if {this} is suspended, that player may put a time counter on {this}."
                ));

    }

    private PardicDragon(final PardicDragon card) {
        super(card);
    }

    @Override
    public PardicDragon copy() {
        return new PardicDragon(this);
    }
}

class PardicDragonEffect extends OneShotEffect {

    public PardicDragonEffect() {
        super(Outcome.Benefit);
        this.staticText = "that player may put a time counter on Pardic Dragon";
    }

    public PardicDragonEffect(final PardicDragonEffect effect) {
        super(effect);
    }

    @Override
    public PardicDragonEffect copy() {
        return new PardicDragonEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player opponent = game.getPlayer(getTargetPointer().getFirst(game, source));
        Card sourceCard = game.getCard(source.getSourceId());
        if (opponent != null && sourceCard != null) {
            if (opponent.chooseUse(outcome, "Put a time counter on " + sourceCard.getName() + '?', source, game)) {
                sourceCard.addCounters(CounterType.TIME.createInstance(), opponent.getId(), source, game);
            }
            return true;
        }
        return false;
    }
}
