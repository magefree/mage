package mage.cards.t;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.CardsInControllerHandCount;
import mage.abilities.dynamicvalue.common.CountersSourceCount;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.counters.CounterType;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author muz
 */
public final class TheDarkhold extends CardImpl {

    public TheDarkhold(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}{B}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.BOOK);

        // {T}: Put a sin counter on The Darkhold. Draw a card for each sin counter on it, then you lose 1 life for each card in your hand.
        Ability sinAbility = new SimpleActivatedAbility(
            new AddCountersSourceEffect(CounterType.SIN.createInstance()),
            new TapSourceCost()
        );
        sinAbility.addEffect(new DrawCardSourceControllerEffect(new CountersSourceCount(CounterType.SIN)));
        sinAbility.addEffect(new LoseLifeSourceControllerEffect(CardsInControllerHandCount.ANY)
            .setText("then you lose 1 life for each card in your hand"));
        this.addAbility(sinAbility);

        // {6}{B}{B}, {T}: Each player's life total becomes the lowest life total among all players. Activate only as a sorcery.
        Ability lifeAbility = new ActivateAsSorceryActivatedAbility(
            new TheDarkholdSetLifeTotalsEffect(),
            new ManaCostsImpl<>("{6}{B}{B}")
        );
        lifeAbility.addCost(new TapSourceCost());
        this.addAbility(lifeAbility);
    }

    private TheDarkhold(final TheDarkhold card) {
        super(card);
    }

    @Override
    public TheDarkhold copy() {
        return new TheDarkhold(this);
    }
}

class TheDarkholdSetLifeTotalsEffect extends OneShotEffect {

    TheDarkholdSetLifeTotalsEffect() {
        super(Outcome.Neutral);
        staticText = "each player's life total becomes the lowest life total among all players";
    }

    private TheDarkholdSetLifeTotalsEffect(final TheDarkholdSetLifeTotalsEffect effect) {
        super(effect);
    }

    @Override
    public TheDarkholdSetLifeTotalsEffect copy() {
        return new TheDarkholdSetLifeTotalsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int lowestLife = game
            .getState()
            .getPlayersInRange(source.getControllerId(), game, true)
            .stream()
            .map(game::getPlayer)
            .filter(player -> player != null)
            .mapToInt(Player::getLife)
            .min()
            .orElse(0);
        for (UUID playerId : game.getState().getPlayersInRange(source.getControllerId(), game, true)) {
            Player player = game.getPlayer(playerId);
            if (player != null) {
                player.setLife(lowestLife, game, source);
            }
        }
        return true;
    }
}
