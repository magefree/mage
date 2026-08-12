package mage.cards.n;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.PowerUpAbility;
import mage.cards.*;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.FilterCard;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.card.DoubleFacedCardPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;
import mage.util.CardUtil;

/**
 *
 * @author anonymous
 */
public final class NickFuryAgentOfSHIELD extends CardImpl {

    public NickFuryAgentOfSHIELD(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{W}");
        
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SPY);
        this.subtype.add(SubType.HERO);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Power-up -- {W}{U}{B}{R}{G}: Put two +1/+1 counters on Nick Fury, then look at the top seven cards of your
        // library. You may put a Hero, Equipment, or Vehicle card from among them onto the battlefield. If it's a
        // double-faced card, you may transform it. Put the rest on the bottom of your library in a random order.
        Ability ability = new PowerUpAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance(2)),
                new ManaCostsImpl<>("{W}{U}{B}{R}{G}")
        );
        ability.addEffect(new NickFuryAgentOfSHIELDEffect());
        this.addAbility(ability);
    }

    private NickFuryAgentOfSHIELD(final NickFuryAgentOfSHIELD card) {
        super(card);
    }

    @Override
    public NickFuryAgentOfSHIELD copy() {
        return new NickFuryAgentOfSHIELD(this);
    }
}

class NickFuryAgentOfSHIELDEffect extends OneShotEffect {
    private static final FilterCard filter = new FilterCard("Hero, Equipment, or Vehicle");
    private static final FilterControlledPermanent filter2 = new FilterControlledPermanent("double-faced");

    static {
        filter.add(Predicates.or(
                SubType.HERO.getPredicate(),
                SubType.EQUIPMENT.getPredicate(),
                SubType.VEHICLE.getPredicate()
        ));
        filter2.add(DoubleFacedCardPredicate.instance);
    }

    NickFuryAgentOfSHIELDEffect() {
        super(Outcome.PutCardInPlay);
        staticText = ", then look at the top seven cards of your library. You may put a Hero, Equipment, or Vehicle " +
                "card from among them onto the battlefield. If it's a double-faced card, you may transform it. Put " +
                "the rest on the bottom of your library in a random order.";
    }

    private NickFuryAgentOfSHIELDEffect(final NickFuryAgentOfSHIELDEffect effect) {
        super(effect);
    }

    @Override
    public NickFuryAgentOfSHIELDEffect copy() {
        return new NickFuryAgentOfSHIELDEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Cards cards = new CardsImpl(player.getLibrary().getTopCards(game, 7));
        TargetCardInLibrary targetCard = new TargetCardInLibrary(0, 1, filter);
        player.choose(outcome, cards, targetCard, source, game);
        Card card = game.getCard(targetCard.getFirstTarget());
        if (card == null) {
            return player.putCardsOnBottomOfLibrary(cards, game, source, false);
        }
        player.moveCards(card, Zone.BATTLEFIELD, source, game);
        cards.removeIf(uuid -> game.getState().getZone(uuid) != Zone.LIBRARY);
        Permanent doubleFaced = CardUtil.getPermanentFromCardPutToBattlefield(card, game);
        if (doubleFaced == null || !filter2.match(doubleFaced, source.getControllerId(), source, game)) {
            return player.putCardsOnBottomOfLibrary(cards, game, source, false);
        }
        if (player.chooseUse(outcome, "Transform " + doubleFaced.getName() + '?', source, game)) {
            doubleFaced.transform(source, game);
        }
        return player.putCardsOnBottomOfLibrary(cards, game, source, false);
    }
}