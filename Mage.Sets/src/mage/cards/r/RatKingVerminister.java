package mage.cards.r;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.common.RevoltCondition;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.triggers.BeginningOfEndStepTriggeredAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.token.RatToken;
import mage.players.Player;
import mage.target.common.TargetCardInYourGraveyard;
import mage.util.CardUtil;
import mage.watchers.common.RevoltWatcher;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author TheElk801
 */
public final class RatKingVerminister extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent(SubType.RAT);

    public RatKingVerminister(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.RAT);
        this.subtype.add(SubType.AVATAR);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Disappear -- At the beginning of your end step, if a permanent left the battlefield under your control this turn, create a 1/1 black Rat creature token and put a +1/+1 counter on Rat King.
        Ability ability = new BeginningOfEndStepTriggeredAbility(new CreateTokenEffect(new RatToken()))
                .withInterveningIf(RevoltCondition.instance);
        ability.addEffect(new AddCountersSourceEffect(CounterType.P1P1.createInstance()).concatBy("and"));
        this.addAbility(ability.addHint(RevoltCondition.getHint()).withFlavorWord("Disappear"), new RevoltWatcher());

        // {T}, Sacrifice three Rats: Return target creature card and all other cards with the same name as that card from your graveyard to the battlefield tapped.
        ability = new SimpleActivatedAbility(new RatKingVerministerEffect(), new TapSourceCost());
        ability.addCost(new SacrificeTargetCost(3, filter));
        ability.addTarget(new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_CREATURE_YOUR_GRAVEYARD));
        this.addAbility(ability);
    }

    private RatKingVerminister(final RatKingVerminister card) {
        super(card);
    }

    @Override
    public RatKingVerminister copy() {
        return new RatKingVerminister(this);
    }
}

class RatKingVerministerEffect extends OneShotEffect {

    RatKingVerministerEffect() {
        super(Outcome.Benefit);
        staticText = "return target creature card and all other cards with the same name " +
                "as that card from your graveyard to the battlefield tapped";
    }

    private RatKingVerministerEffect(final RatKingVerministerEffect effect) {
        super(effect);
    }

    @Override
    public RatKingVerministerEffect copy() {
        return new RatKingVerministerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Card card = game.getCard(getTargetPointer().getFirst(game, source));
        if (player == null || card == null) {
            return false;
        }
        Set<Card> cards = player
                .getGraveyard()
                .getCards(game)
                .stream()
                .filter(c -> CardUtil.haveSameNames(c, card))
                .collect(Collectors.toSet());
        cards.add(card);
        return player.moveCards(
                cards, Zone.BATTLEFIELD, source, game, true,
                false, false, null
        );
    }
}
