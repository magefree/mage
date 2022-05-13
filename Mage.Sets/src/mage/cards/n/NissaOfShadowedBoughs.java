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
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.custom.CreatureToken;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.TargetPermanent;
import mage.target.common.TargetCardInHand;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

import static mage.constants.Outcome.Benefit;

/**
 * @author TheElk801
 */
public final class NissaOfShadowedBoughs extends CardImpl {

    public NissaOfShadowedBoughs(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{2}{B}{G}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.NISSA);
        this.setStartingLoyalty(4);

        // Landfall — Whenever a land enters the battlefield under your control, put a loyalty counter on Nissa of Shadowed Boughs.
        this.addAbility(new LandfallAbility(new AddCountersSourceEffect(CounterType.LOYALTY.createInstance())));

        // +1: Untap target land you control. You may have it become a 3/3 Elemental creature with haste and menace until end of turn. It's still a land.
        Ability ability = new LoyaltyAbility(new UntapTargetEffect(), 1);
        ability.addEffect(new NissaOfShadowedBoughsLandEffect());
        ability.addTarget(new TargetPermanent(StaticFilters.FILTER_CONTROLLED_PERMANENT_LAND));
        this.addAbility(ability);

        // −5: You may put a creature card with converted mana cost less than or equal to the number of lands you control onto the battlefield from your hand or graveyard with two +1/+1 counters on it.
        this.addAbility(new LoyaltyAbility(new NissaOfShadowedBoughsCreatureEffect(), -5));
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
        super(Benefit);
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

class NissaOfShadowedBoughsCreatureEffect extends OneShotEffect {

    NissaOfShadowedBoughsCreatureEffect() {
        super(Outcome.Benefit);
        staticText = "You may put a creature card with mana value less than or equal to " +
                "the number of lands you control onto the battlefield from your hand or graveyard " +
                "with two +1/+1 counters on it.";
    }

    private NissaOfShadowedBoughsCreatureEffect(final NissaOfShadowedBoughsCreatureEffect effect) {
        super(effect);
    }

    @Override
    public NissaOfShadowedBoughsCreatureEffect copy() {
        return new NissaOfShadowedBoughsCreatureEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        int lands = game.getBattlefield().count(
                StaticFilters.FILTER_CONTROLLED_PERMANENT_LAND,
                source.getControllerId(), source, game
        );
        FilterCard filter = new FilterCreatureCard("creature card with mana value " + lands + " or less");
        filter.add(new ManaValuePredicate(ComparisonType.FEWER_THAN, lands + 1));
        int inHand = player.getHand().count(filter, game);
        int inGrave = player.getGraveyard().count(filter, game);
        if (inHand < 1 && inGrave < 1) {
            return false;
        }
        TargetCard target;
        if (inHand < 1 || (inGrave > 0 && !player.chooseUse(
                outcome, "Put a creature card from your hand or graveyard onto the battlefield?",
                null, "Hand", "Graveyard", source, game
        ))) {
            target = new TargetCardInYourGraveyard(0, 1, filter, true);
        } else {
            target = new TargetCardInHand(filter);
        }
        player.choose(outcome, target, source, game);
        Card card = game.getCard(target.getFirstTarget());
        if (card == null) {
            return false;
        }
        player.moveCards(card, Zone.BATTLEFIELD, source, game);
        Permanent permanent = game.getPermanent(card.getId());
        if (permanent != null) {
            permanent.addCounters(CounterType.P1P1.createInstance(2), source.getControllerId(), source, game);
        }
        return true;
    }
}
