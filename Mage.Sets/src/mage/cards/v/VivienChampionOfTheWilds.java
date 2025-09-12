package mage.cards.v;

import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.CastAsThoughItHadFlashAllEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.ReachAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.*;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInLibrary;
import mage.target.common.TargetCreaturePermanent;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class VivienChampionOfTheWilds extends CardImpl {

    private static final FilterCard filter = new FilterCard("creature spells");

    static {
        filter.add(CardType.CREATURE.getPredicate());
    }

    public VivienChampionOfTheWilds(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{2}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.VIVIEN);
        this.setStartingLoyalty(4);

        // You may cast creature spells as though they had flash.
        this.addAbility(new SimpleStaticAbility(new CastAsThoughItHadFlashAllEffect(
                Duration.WhileOnBattlefield, filter
        )));

        // +1: Until your next turn, up to one target creature gains vigilance and reach.
        Ability ability = new LoyaltyAbility(new GainAbilityTargetEffect(
                VigilanceAbility.getInstance(), Duration.UntilYourNextTurn
        ).setText("Until your next turn, up to one target creature gains vigilance"), 1);
        ability.addEffect(new GainAbilityTargetEffect(
                ReachAbility.getInstance(), Duration.UntilYourNextTurn
        ).setText("and reach"));
        ability.addTarget(new TargetCreaturePermanent(0, 1));
        this.addAbility(ability);

        // -2: Look at the top three cards of your library. Exile one face down and put the rest on the bottom of your library in any order.
        // For as long as it remains exiled, you may look at that card and you may cast it if it's a creature card.
        this.addAbility(new LoyaltyAbility(new VivienChampionOfTheWildsEffect(), -2));
    }

    private VivienChampionOfTheWilds(final VivienChampionOfTheWilds card) {
        super(card);
    }

    @Override
    public VivienChampionOfTheWilds copy() {
        return new VivienChampionOfTheWilds(this);
    }
}

class VivienChampionOfTheWildsEffect extends OneShotEffect {

    VivienChampionOfTheWildsEffect() {
        super(Outcome.Benefit);
        staticText = "Look at the top three cards of your library. " +
                "Exile one face down and put the rest on the bottom of your library in any order. " +
                "For as long as it remains exiled, you may look at that card " +
                "and you may cast it if it's a creature card.";
    }

    private VivienChampionOfTheWildsEffect(final VivienChampionOfTheWildsEffect effect) {
        super(effect);
    }

    @Override
    public VivienChampionOfTheWildsEffect copy() {
        return new VivienChampionOfTheWildsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }

        // select
        Cards cardsToLook = new CardsImpl(player.getLibrary().getTopCards(game, 3));
        FilterCard filter = new FilterCard("card to exile face down");
        TargetCard target = new TargetCardInLibrary(filter);
        if (!player.choose(outcome, cardsToLook, target, source, game)) {
            return false;
        }

        // exile
        Card cardToExile = game.getCard(target.getFirstTarget());
        if (!CardUtil.moveCardsToExileFaceDown(game, source, player, cardToExile,
                CardUtil.getCardExileZoneId(game, source),
                CardUtil.createObjectRelatedWindowTitle(source, game, " (look and cast)"), true)) {
            return false;
        }
        // cast
        if (cardToExile.isCreature(game)) {
            CardUtil.makeCardPlayable(game, source, cardToExile, true, Duration.EndOfGame, false);
        }

        // put the rest on the bottom of your library in any order
        Cards cardsToBottom = new CardsImpl(cardsToLook);
        cardsToBottom.remove(cardToExile);
        player.putCardsOnBottomOfLibrary(cardsToBottom, game, source, true);

        return true;
    }
}
