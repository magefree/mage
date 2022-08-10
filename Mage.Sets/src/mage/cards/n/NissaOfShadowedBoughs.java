package mage.cards.n;

import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.common.LandfallAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.PutCardFromOneOfTwoZonesOntoBattlefieldEffect;
import mage.abilities.effects.common.UntapTargetEffect;
import mage.abilities.effects.common.continuous.BecomesCreatureTargetEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.filter.predicate.card.CardManaCostLessThanControlledLandCountPredicate;
import mage.game.Game;
import mage.game.permanent.token.custom.CreatureToken;
import mage.players.Player;
import mage.target.TargetPermanent;

import java.util.UUID;

import static mage.constants.Outcome.Benefit;

/**
 * @author TheElk801
 */
public final class NissaOfShadowedBoughs extends CardImpl {

    private static final FilterCard filter = new FilterCard("card with mana value less than or equal to the number of lands you control");
    static {
        filter.add(CardManaCostLessThanControlledLandCountPredicate.getInstance());
    }

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

        // −5: You may put a creature card with mana value less than or equal to the number of lands you control onto the battlefield from your hand or graveyard with two +1/+1 counters on it.
        Effect putCardEffect = new PutCardFromOneOfTwoZonesOntoBattlefieldEffect(filter, false, new AddCountersTargetEffect(CounterType.P1P1.createInstance(2)));
        putCardEffect.setText("You may put a creature card with mana value less than or equal to " +
                "the number of lands you control onto the battlefield from your hand or graveyard " +
                "with two +1/+1 counters on it.");
        this.addAbility(new LoyaltyAbility(putCardEffect,-5)
        );
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
