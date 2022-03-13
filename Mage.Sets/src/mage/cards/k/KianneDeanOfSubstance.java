package mage.cards.k;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.hint.Hint;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.*;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.FilterCard;
import mage.filter.common.FilterOwnedCard;
import mage.game.Game;
import mage.game.permanent.token.FractalToken;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInExile;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author TheElk801
 */
public final class KianneDeanOfSubstance extends ModalDoubleFacesCard {

    public KianneDeanOfSubstance(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.ELF, SubType.DRUID}, "{2}{G}",
                "Imbraham, Dean of Theory", new CardType[]{CardType.CREATURE}, new SubType[]{SubType.BIRD, SubType.WIZARD}, "{2}{U}{U}");

        // 1.
        // Kianne, Dean of Substance
        // Legendary Creature - Elf Druid
        this.getLeftHalfCard().addSuperType(SuperType.LEGENDARY);
        this.getLeftHalfCard().setPT(2, 2);

        // {T}: Exile the top card of your library. If it's a land card, put it into your hand. Otherwise, put a study counter on it.
        this.getLeftHalfCard().addAbility(new SimpleActivatedAbility(
                new KianneDeanOfSubstanceExileEffect(), new TapSourceCost()
        ));

        // {4}{G}: Create a 0/0 green and blue Fractal creature token. Put a +1/+1 counter on it for each different mana value among nonland cards you own in exile with study counters on them.
        this.getLeftHalfCard().addAbility(new SimpleActivatedAbility(FractalToken.getEffect(
                KianneDeanOfSubstanceValue.instance, "Put a +1/+1 counter on it for each different mana value " +
                        "among nonland cards you own in exile with study counters on them"
        ), new ManaCostsImpl("{4}{G}")).addHint(KianneDeanOfSubstanceHint.instance));

        // 2.
        // Imbraham, Dean of Theory
        // Legendary Creature - Bird Wizard
        this.getRightHalfCard().addSuperType(SuperType.LEGENDARY);
        this.getRightHalfCard().setPT(3, 3);

        // Flying
        this.getRightHalfCard().addAbility(FlyingAbility.getInstance());

        // {X}{U}{U}, {T}: Exile the top X cards of your library and put a study counter on each of them. Then you may put a card you own in exile with a study counter on it into your hand.
        Ability ability = new SimpleActivatedAbility(
                new ImbrahamDeanOfTheoryEffect(), new ManaCostsImpl("{X}{U}{U}")
        );
        ability.addCost(new TapSourceCost());
        this.getRightHalfCard().addAbility(ability);
    }

    private KianneDeanOfSubstance(final KianneDeanOfSubstance card) {
        super(card);
    }

    @Override
    public KianneDeanOfSubstance copy() {
        return new KianneDeanOfSubstance(this);
    }
}

class KianneDeanOfSubstanceExileEffect extends OneShotEffect {

    KianneDeanOfSubstanceExileEffect() {
        super(Outcome.Benefit);
        staticText = "Exile the top card of your library. If it's a land card, " +
                "put it into your hand. Otherwise, put a study counter on it.";
    }

    private KianneDeanOfSubstanceExileEffect(final KianneDeanOfSubstanceExileEffect effect) {
        super(effect);
    }

    @Override
    public KianneDeanOfSubstanceExileEffect copy() {
        return new KianneDeanOfSubstanceExileEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Card card = player.getLibrary().getFromTop(game);
        if (card == null) {
            return false;
        }
        player.moveCards(card, Zone.EXILED, source, game);
        if (card.isLand(game)) {
            return player.moveCards(card, Zone.HAND, source, game);
        }
        return card.getMainCard().addCounters(
                CounterType.STUDY.createInstance(),
                player.getId(), source, game
        );
    }
}

enum KianneDeanOfSubstanceValue implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        return game
                .getExile()
                .getAllCards(game)
                .stream()
                .filter(Objects::nonNull)
                .filter(card -> card.isOwnedBy(sourceAbility.getControllerId()))
                .filter(card -> card.getCounters(game).containsKey(CounterType.STUDY))
                .map(MageObject::getManaValue)
                .distinct()
                .mapToInt(x -> 1)
                .sum();
    }

    @Override
    public KianneDeanOfSubstanceValue copy() {
        return instance;
    }

    @Override
    public String getMessage() {
        return "";
    }
}

enum KianneDeanOfSubstanceHint implements Hint {
    instance;

    @Override
    public String getText(Game game, Ability ability) {
        List<String> values = game.getExile()
                .getAllCards(game)
                .stream()
                .filter(Objects::nonNull)
                .filter(card -> card.isOwnedBy(ability.getControllerId()))
                .filter(card -> card.getCounters(game).containsKey(CounterType.STUDY))
                .mapToInt(MageObject::getManaValue)
                .distinct()
                .sorted()
                .mapToObj(String::valueOf)
                .collect(Collectors.toList());
        return "Mana values of cards exiled with study counters: " + values.size()
                + (values.size() > 0 ? " (" + String.join(", ", values) + ')' : "");
    }

    @Override
    public KianneDeanOfSubstanceHint copy() {
        return instance;
    }
}

class ImbrahamDeanOfTheoryEffect extends OneShotEffect {

    private static final FilterCard filter = new FilterOwnedCard("card you own in exile with a study counter on it");

    static {
        filter.add(CounterType.STUDY.getPredicate());
    }

    ImbrahamDeanOfTheoryEffect() {
        super(Outcome.DrawCard);
        staticText = "Exile the top X cards of your library and put a study counter on each of them. " +
                "Then you may put a card you own in exile with a study counter on it into your hand.";
    }

    private ImbrahamDeanOfTheoryEffect(final ImbrahamDeanOfTheoryEffect effect) {
        super(effect);
    }

    @Override
    public ImbrahamDeanOfTheoryEffect copy() {
        return new ImbrahamDeanOfTheoryEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Cards cards = new CardsImpl(player.getLibrary().getTopCards(game, source.getManaCostsToPay().getX()));
        player.moveCards(cards, Zone.EXILED, source, game);
        for (Card card : cards.getCards(game)) {
            if (card == null) {
                continue;
            }
            card.addCounters(CounterType.STUDY.createInstance(), source.getControllerId(), source, game);
        }
        TargetCard targetCard = new TargetCardInExile(0, 1, filter, null);
        targetCard.setNotTarget(true);
        player.choose(outcome, targetCard, source, game);
        Card card = game.getCard(targetCard.getFirstTarget());
        if (card != null) {
            player.moveCards(card, Zone.HAND, source, game);
        }
        return true;
    }
}
