package mage.cards.b;

import mage.abilities.Ability;
import mage.abilities.common.SacrificePermanentTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.ExileTargetForSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInExile;
import mage.target.common.TargetControlledPermanent;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BagOfDevouring extends CardImpl {

    private static final FilterPermanent filter
            = new FilterPermanent("another nontoken artifact or creature");
    private static final FilterControlledPermanent filter2
            = new FilterControlledPermanent("another artifact or creature");

    static {
        filter.add(TokenPredicate.FALSE);
        filter.add(AnotherPredicate.instance);
        filter.add(Predicates.or(
                CardType.ARTIFACT.getPredicate(),
                CardType.CREATURE.getPredicate()
        ));
        filter2.add(AnotherPredicate.instance);
        filter2.add(Predicates.or(
                CardType.ARTIFACT.getPredicate(),
                CardType.CREATURE.getPredicate()
        ));
    }

    public BagOfDevouring(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{B}");

        // Whenever you sacrifice another nontoken artifact or creature, exile it.
        this.addAbility(new SacrificePermanentTriggeredAbility(new ExileTargetForSourceEffect().setText("exile it"), filter, true));

        // {2}, {T}, Sacrifice another artifact or creature: Draw a card.
        Ability ability = new SimpleActivatedAbility(new DrawCardSourceControllerEffect(1), new GenericManaCost(2));
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeTargetCost(new TargetControlledPermanent(filter2)));
        this.addAbility(ability);

        // {3}, {T}, Sacrifice Bag of Devouring: Roll a d10. Return up to X cards from among cards exiled with Bag of Devouring to their owners' hands, where X is the result.
        ability = new SimpleActivatedAbility(new BagOfDevouringEffect(), new GenericManaCost(3));
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);
    }

    private BagOfDevouring(final BagOfDevouring card) {
        super(card);
    }

    @Override
    public BagOfDevouring copy() {
        return new BagOfDevouring(this);
    }
}

class BagOfDevouringEffect extends OneShotEffect {

    BagOfDevouringEffect() {
        super(Outcome.ReturnToHand);
        staticText = "roll a d10. Return up to X cards from among cards " +
                "exiled with {this} to their owners' hands, where X is the result";
    }

    private BagOfDevouringEffect(final BagOfDevouringEffect effect) {
        super(effect);
    }

    @Override
    public BagOfDevouringEffect copy() {
        return new BagOfDevouringEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        int result = player.rollDice(Outcome.Benefit, source, game, 10);
        TargetCard target = new TargetCardInExile(
                0, result, StaticFilters.FILTER_CARD,
                CardUtil.getExileZoneId(game, source)
        );
        target.setNotTarget(true);
        player.choose(outcome, target, source.getSourceId(), game);
        player.moveCards(new CardsImpl(target.getTargets()), Zone.HAND, source, game);
        return true;
    }
}
