package mage.cards.n;

import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.common.LandfallAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.UntapTargetEffect;
import mage.abilities.effects.common.continuous.BecomesCreatureTargetEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.*;
import mage.constants.*;
import mage.counters.CounterType;
import mage.counters.Counters;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.card.ManaValueLessThanControlledLandCountPredicate;
import mage.game.Game;
import mage.game.permanent.token.custom.CreatureToken;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class NissaOfShadowedBoughs extends CardImpl {

    public NissaOfShadowedBoughs(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{2}{B}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.NISSA);
        this.setStartingLoyalty(4);

        // Landfall — Whenever a land enters the battlefield under your control, put a loyalty counter on Nissa of Shadowed Boughs.
        this.addAbility(new LandfallAbility(new AddCountersSourceEffect(CounterType.LOYALTY.createInstance())));

        // +1: Untap target land you control. You may have it become a 3/3 Elemental creature with haste and menace until end of turn. It's still a land.
        Ability ability = new LoyaltyAbility(new UntapTargetEffect(), 1);
        ability.addEffect(new NissaOfShadowedBoughsLandEffect());
        ability.addTarget(new TargetPermanent(StaticFilters.FILTER_CONTROLLED_PERMANENT_LAND));
        this.addAbility(ability);

        // −5: You may put a creature card with mana value less than or equal to the number of lands you control onto the battlefield from your hand or graveyard with two +1/+1 counters on it.
        this.addAbility(new LoyaltyAbility(new NissaOfShadowedBoughsPutCardEffect(),-5));
    }

    private NissaOfShadowedBoughs(final NissaOfShadowedBoughs card) {
        super(card);
    }

    @Override
    public NissaOfShadowedBoughs copy() {
        return new NissaOfShadowedBoughs(this);
    }
}

class NissaOfShadowedBoughsLandEffect extends OneShotEffect {

    NissaOfShadowedBoughsLandEffect() {
        super(Outcome.Benefit);
        staticText = "You may have it become a 3/3 Elemental creature with haste and menace until end of turn. It's still a land.";
    }

    private NissaOfShadowedBoughsLandEffect(final NissaOfShadowedBoughsLandEffect effect) {
        super(effect);
    }

    @Override
    public NissaOfShadowedBoughsLandEffect copy() {
        return new NissaOfShadowedBoughsLandEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null || !player.chooseUse(
                Outcome.BecomeCreature, "Have it become a creature?", source, game
        )) {
            return false;
        }
        game.addEffect(new BecomesCreatureTargetEffect(
                new CreatureToken(3, 3, "", SubType.ELEMENTAL)
                        .withAbility(HasteAbility.getInstance())
                        .withAbility(new MenaceAbility()),
                false, true, Duration.EndOfTurn
        ), source);
        return true;
    }
}


class NissaOfShadowedBoughsPutCardEffect extends OneShotEffect {

    private static final FilterCreatureCard filter = new FilterCreatureCard("creature card with mana value less than or equal to the number of lands you control");
    static {
        filter.add(ManaValueLessThanControlledLandCountPredicate.instance);
    }

     NissaOfShadowedBoughsPutCardEffect() {
         super(Outcome.PutCardInPlay);
         this.staticText = "you may put a creature card with mana value less than or equal to " +
                 "the number of lands you control onto the battlefield from your hand or graveyard " +
                 "with two +1/+1 counters on it";
    }

    private NissaOfShadowedBoughsPutCardEffect(final NissaOfShadowedBoughsPutCardEffect effect) {
        super(effect);
    }

    @Override
    public NissaOfShadowedBoughsPutCardEffect copy() {
        return new NissaOfShadowedBoughsPutCardEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        Cards cards = new CardsImpl();
        cards.addAllCards(controller.getHand().getCards(filter, source.getControllerId(), source, game));
        cards.addAllCards(controller.getGraveyard().getCards(filter, source.getControllerId(), source, game));
        if (cards.isEmpty()) {
            return true;
        }
        TargetCard target = new TargetCard(0, 1, Zone.ALL, filter);
        target.withNotTarget(true);
        controller.choose(outcome, cards, target, source, game);
        Card card = game.getCard(target.getFirstTarget());
        if (card != null) {
            Counters counters = new Counters();
            counters.addCounter(CounterType.P1P1.createInstance(2));
            game.setEnterWithCounters(card.getId(), counters);
            controller.moveCards(card, Zone.BATTLEFIELD, source, game);
        }
        return true;
    }

}
