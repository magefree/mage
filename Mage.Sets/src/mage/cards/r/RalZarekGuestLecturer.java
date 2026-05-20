package mage.cards.r;

import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.abilities.effects.common.discard.DiscardTargetEffect;
import mage.abilities.effects.keyword.SurveilEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.Game;
import mage.game.turn.TurnMod;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RalZarekGuestLecturer extends CardImpl {

    private static final FilterCard filter = new FilterCreatureCard("creature card with mana value 3 or less from your graveyard");

    static {
        filter.add(new ManaValuePredicate(ComparisonType.FEWER_THAN, 4));
    }

    public RalZarekGuestLecturer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{1}{B}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.RAL);
        this.setStartingLoyalty(3);

        // +1: Surveil 2.
        this.addAbility(new LoyaltyAbility(new SurveilEffect(2), 1));

        // -1: Any number of target players each discard a card.
        Ability ability = new LoyaltyAbility(new DiscardTargetEffect(1), -1);
        ability.addTarget(new TargetPlayer(0, Integer.MAX_VALUE, false));
        this.addAbility(ability);

        // -2: Return target creature card with mana value 3 or less from your graveyard to the battlefield.
        ability = new LoyaltyAbility(new ReturnFromGraveyardToBattlefieldTargetEffect(), -2);
        ability.addTarget(new TargetCardInYourGraveyard(filter));
        this.addAbility(ability);

        // -7: Flip five coins. Target opponent skips their next X turns, where X is the number of coins that came up heads.
        ability = new LoyaltyAbility(new RalZarekGuestLecturerEffect(), -7);
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);
    }

    private RalZarekGuestLecturer(final RalZarekGuestLecturer card) {
        super(card);
    }

    @Override
    public RalZarekGuestLecturer copy() {
        return new RalZarekGuestLecturer(this);
    }
}

class RalZarekGuestLecturerEffect extends OneShotEffect {

    RalZarekGuestLecturerEffect() {
        super(Outcome.Benefit);
        staticText = "flip five coins. Target opponent skips their next X turns, " +
                "where X is the number of coins that came up heads";
    }

    private RalZarekGuestLecturerEffect(final RalZarekGuestLecturerEffect effect) {
        super(effect);
    }

    @Override
    public RalZarekGuestLecturerEffect copy() {
        return new RalZarekGuestLecturerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        int count = player
                .flipCoins(source, game, 5, false)
                .stream()
                .mapToInt(x -> x ? 1 : 0)
                .sum();
        Player opponent = game.getPlayer(getTargetPointer().getFirst(game, source));
        if (opponent == null) {
            return true;
        }
        game.informPlayers(opponent.getLogName() + " skips their next " + count + " turns.");
        for (int i = 0; i < count; i++) {
            game.getState().getTurnMods().add(new TurnMod(opponent.getId()).withSkipTurn());
        }
        return true;
    }
}
