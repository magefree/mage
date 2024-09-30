package mage.cards.n;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;

import java.util.Optional;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class NashiSearcherInTheDark extends CardImpl {

    public NashiSearcherInTheDark(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{U}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.RAT);
        this.subtype.add(SubType.NINJA);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Menace
        this.addAbility(new MenaceAbility(false));

        // Whenever Nashi, Searcher in the Dark deals combat damage to a player, you mill that many cards. You may put any number of legendary and/or enchantment cards from among them into your hand. If you put no cards into your hand this way, put a +1/+1 counter on Nashi.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(new NashiSearcherInTheDarkEffect()));
    }

    private NashiSearcherInTheDark(final NashiSearcherInTheDark card) {
        super(card);
    }

    @Override
    public NashiSearcherInTheDark copy() {
        return new NashiSearcherInTheDark(this);
    }
}

class NashiSearcherInTheDarkEffect extends OneShotEffect {

    private static final FilterCard filter = new FilterCard("legendary and/or enchantment cards");

    static {
        filter.add(Predicates.or(
                SuperType.LEGENDARY.getPredicate(),
                CardType.ENCHANTMENT.getPredicate()
        ));
    }

    NashiSearcherInTheDarkEffect() {
        super(Outcome.Benefit);
        staticText = "you mill that many cards. You may put any number of legendary and/or enchantment cards from " +
                "among them into your hand. If you put no cards into your hand this way, put a +1/+1 counter on {this}";
    }

    private NashiSearcherInTheDarkEffect(final NashiSearcherInTheDarkEffect effect) {
        super(effect);
    }

    @Override
    public NashiSearcherInTheDarkEffect copy() {
        return new NashiSearcherInTheDarkEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        int amount = (Integer) getValue("damage");
        if (player == null || amount < 1) {
            return false;
        }
        Cards cards = player.millCards(amount, source, game);
        TargetCard target = new TargetCard(0, Integer.MAX_VALUE, Zone.ALL, filter);
        target.withNotTarget(true);
        player.choose(Outcome.ReturnToHand, cards, target, source, game);
        Cards toHand = new CardsImpl(target.getTargets());
        player.moveCards(toHand, Zone.HAND, source, game);
        toHand.retainZone(Zone.HAND, game);
        if (toHand.isEmpty()) {
            Optional.ofNullable(source.getSourcePermanentIfItStillExists(game))
                    .ifPresent(permanent -> permanent.addCounters(CounterType.P1P1.createInstance(), source, game));
        }
        return true;
    }
}
