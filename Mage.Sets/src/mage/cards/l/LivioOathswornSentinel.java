package mage.cards.l;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.PartnerAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;

import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author TheElk801
 */
public final class LivioOathswornSentinel extends CardImpl {

    public LivioOathswornSentinel(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // {1}{W}: Choose another target creature. Its controller may exile it with an aegis counter on it.
        Ability ability = new SimpleActivatedAbility(
                new LivioOathswornSentinelExileEffect(), new ManaCostsImpl<>("{1}{W}")
        );
        ability.addTarget(new TargetPermanent(StaticFilters.FILTER_ANOTHER_TARGET_CREATURE));
        this.addAbility(ability);

        // {2}{W}, {T}: Return all exiled cards with aegis counters on them to the battlefield under their owners' control.
        ability = new SimpleActivatedAbility(new LivioOathswornSentinelReturnEffect(), new ManaCostsImpl<>("{2}{W}"));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);

        // Partner
        this.addAbility(PartnerAbility.getInstance());
    }

    private LivioOathswornSentinel(final LivioOathswornSentinel card) {
        super(card);
    }

    @Override
    public LivioOathswornSentinel copy() {
        return new LivioOathswornSentinel(this);
    }
}

class LivioOathswornSentinelExileEffect extends OneShotEffect {

    LivioOathswornSentinelExileEffect() {
        super(Outcome.Exile);
        staticText = "choose another target creature. Its controller may exile it with an aegis counter on it";
    }

    private LivioOathswornSentinelExileEffect(final LivioOathswornSentinelExileEffect effect) {
        super(effect);
    }

    @Override
    public LivioOathswornSentinelExileEffect copy() {
        return new LivioOathswornSentinelExileEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        if (permanent == null) {
            return false;
        }
        Player player = game.getPlayer(permanent.getControllerId());
        if (player == null || !player.chooseUse(
                outcome, "Exile " + permanent.getName() + " with an aegis counter on it?", source, game
        )) {
            return false;
        }
        Card card = game.getCard(permanent.getId());
        player.moveCards(permanent, Zone.EXILED, source, game);
        if (card == null || game.getState().getZone(card.getId()) != Zone.EXILED) {
            return false;
        }
        card.addCounters(CounterType.AEGIS.createInstance(), source.getControllerId(), source, game);
        return true;
    }
}

class LivioOathswornSentinelReturnEffect extends OneShotEffect {

    LivioOathswornSentinelReturnEffect() {
        super(Outcome.Benefit);
        staticText = "return all exiled cards with aegis counters on them to the battlefield under their owners' control";
    }

    private LivioOathswornSentinelReturnEffect(final LivioOathswornSentinelReturnEffect effect) {
        super(effect);
    }

    @Override
    public LivioOathswornSentinelReturnEffect copy() {
        return new LivioOathswornSentinelReturnEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Set<Card> cards = game
                .getExile()
                .getAllCards(game)
                .stream()
                .filter(Objects::nonNull)
                .filter(card -> card.getCounters(game).containsKey(CounterType.AEGIS))
                .collect(Collectors.toSet());
        return player.moveCards(
                cards, Zone.BATTLEFIELD, source, game, false,
                false, true, null
        );
    }
}
