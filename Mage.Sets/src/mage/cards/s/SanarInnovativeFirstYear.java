package mage.cards.s;

import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.assignment.common.ColorAssignment;
import mage.abilities.dynamicvalue.common.ColorsAmongControlledPermanentsCount;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.triggers.BeginningOfFirstMainTriggeredAbility;
import mage.cards.*;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ColorlessPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInLibrary;
import mage.util.CardUtil;

import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author TheElk801
 */
public final class SanarInnovativeFirstYear extends CardImpl {

    public SanarInnovativeFirstYear(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U/R}{U/R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.SORCERER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // Vivid -- At the beginning of your first main phase, reveal cards from the top of your library until you reveal X nonland cards, where X is the number of colors among permanents you control. For each of those colors, you may exile a card of that color from among the revealed cards. Then shuffle. You may cast the exiled cards this turn.
        this.addAbility(new BeginningOfFirstMainTriggeredAbility(new SanarInnovativeFirstYearEffect())
                .setAbilityWord(AbilityWord.VIVID)
                .addHint(ColorsAmongControlledPermanentsCount.ALL_PERMANENTS.getHint()));
    }

    private SanarInnovativeFirstYear(final SanarInnovativeFirstYear card) {
        super(card);
    }

    @Override
    public SanarInnovativeFirstYear copy() {
        return new SanarInnovativeFirstYear(this);
    }
}

class SanarInnovativeFirstYearEffect extends OneShotEffect {

    SanarInnovativeFirstYearEffect() {
        super(Outcome.Benefit);
        staticText = "reveal cards from the top of your library until you reveal X nonland cards, " +
                "where X is the number of colors among permanents you control. For each of those colors, " +
                "you may exile a card of that color from among the revealed cards. Then shuffle. " +
                "You may cast the exiled cards this turn";
    }

    private SanarInnovativeFirstYearEffect(final SanarInnovativeFirstYearEffect effect) {
        super(effect);
    }

    @Override
    public SanarInnovativeFirstYearEffect copy() {
        return new SanarInnovativeFirstYearEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        ObjectColor color = ColorsAmongControlledPermanentsCount.ALL_PERMANENTS.getAllControlledColors(game, source);
        int count = color.getColorCount();
        if (player == null || count < 1) {
            return false;
        }
        Cards cards = new CardsImpl();
        for (Card card : player.getLibrary().getCards(game)) {
            cards.add(card);
            if (cards.count(StaticFilters.FILTER_CARD_NON_LAND, game) >= count) {
                break;
            }
        }
        player.revealCards(source, cards, game);
        TargetCard target = new SanarInnovativeFirstYearTarget(color);
        player.choose(outcome, cards, target, source, game);
        Cards toExile = new CardsImpl(target.getTargets());
        player.moveCards(toExile, Zone.EXILED, source, game);
        toExile.retainZone(Zone.EXILED, game);
        player.shuffleLibrary(source, game);
        for (Card card : toExile.getCards(game)) {
            CardUtil.makeCardPlayable(game, source, card, true, Duration.EndOfTurn, false);
        }
        return true;
    }
}

class SanarInnovativeFirstYearTarget extends TargetCardInLibrary {

    private final ColorAssignment colorAssigner;

    private static FilterCard makeFilter(ObjectColor color) {
        FilterCard filterCard = new FilterCard(CardUtil.concatWithAnd(
                color.getColors()
                        .stream()
                        .map(ObjectColor::getDescription)
                        .map(s -> "a " + s + " card")
                        .collect(Collectors.toList())
        ));
        filterCard.add(Predicates.not(ColorlessPredicate.instance));
        return filterCard;
    }

    SanarInnovativeFirstYearTarget(ObjectColor color) {
        super(0, color.getColorCount(), makeFilter(color));
        this.colorAssigner = new ColorAssignment(color.toString().split(""));
    }

    private SanarInnovativeFirstYearTarget(final SanarInnovativeFirstYearTarget target) {
        super(target);
        this.colorAssigner = target.colorAssigner;
    }

    @Override
    public SanarInnovativeFirstYearTarget copy() {
        return new SanarInnovativeFirstYearTarget(this);
    }

    @Override
    public boolean canTarget(UUID playerId, UUID id, Ability source, Game game) {
        if (!super.canTarget(playerId, id, source, game)) {
            return false;
        }
        Card card = game.getCard(id);
        if (card == null) {
            return false;
        }
        if (this.getTargets().isEmpty()) {
            return true;
        }
        Cards cards = new CardsImpl(this.getTargets());
        cards.add(card);
        return colorAssigner.getRoleCount(cards, game) >= cards.size();
    }
}
