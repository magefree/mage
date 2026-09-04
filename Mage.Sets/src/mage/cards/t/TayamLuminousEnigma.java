package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.RemoveCounterCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.EntersWithCountersControlledEffect;
import mage.abilities.effects.common.MillCardsControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.filter.common.FilterPermanentCard;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author htrajan
 */
public final class TayamLuminousEnigma extends CardImpl {

    public TayamLuminousEnigma(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}{B}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.NIGHTMARE);
        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Each other creature you control enters the battlefield with an additional vigilance counter on it.
        this.addAbility(new SimpleStaticAbility(new EntersWithCountersControlledEffect(
                StaticFilters.FILTER_PERMANENT_CREATURE,
                CounterType.VIGILANCE.createInstance(), true
        )));

        // {3}, Remove three counters from among creatures you control: Put the top three cards of your library into your graveyard, then return a permanent card with converted mana cost 3 or less from your graveyard to the battlefield.
        Ability ability = new SimpleActivatedAbility(
                new MillCardsControllerEffect(3).concatBy("."), new GenericManaCost(3)
        );
        ability.addCost(new RemoveCounterCost(new TargetControlledCreaturePermanent(1, 3), null, 3));
        ability.addEffect(new TayamLuminousEnigmaEffect());
        this.addAbility(ability);
    }

    private TayamLuminousEnigma(final TayamLuminousEnigma card) {
        super(card);
    }

    @Override
    public TayamLuminousEnigma copy() {
        return new TayamLuminousEnigma(this);
    }
}

class TayamLuminousEnigmaEffect extends OneShotEffect {

    private static final FilterCard filter = new FilterPermanentCard("permanent card in your graveyard with mana value 3 or less");

    static {
        filter.add(new ManaValuePredicate(ComparisonType.FEWER_THAN, 4));
    }

    TayamLuminousEnigmaEffect() {
        super(Outcome.Benefit);
        staticText = ", then return a permanent card with mana value 3 or less from your graveyard to the battlefield";
    }

    private TayamLuminousEnigmaEffect(TayamLuminousEnigmaEffect effect) {
        super(effect);
    }

    @Override
    public TayamLuminousEnigmaEffect copy() {
        return new TayamLuminousEnigmaEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null || player.getGraveyard().count(filter, game) == 0) {
            return false;
        }
        TargetCard target = new TargetCardInYourGraveyard(filter);
        target.withNotTarget(true);
        if (!player.choose(outcome, player.getGraveyard(), target, source, game)) {
            return false;
        }
        return player.moveCards(game.getCard(target.getFirstTarget()), Zone.BATTLEFIELD, source, game);
    }
}
