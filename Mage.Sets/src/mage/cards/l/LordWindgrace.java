package mage.cards.l;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.common.CanBeYourCommanderAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.filter.StaticFilters;
import mage.filter.common.FilterLandCard;
import mage.game.Game;
import mage.game.permanent.token.CatWarriorToken;
import mage.players.Player;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.common.TargetNonlandPermanent;

/**
 *
 * @author TheElk801
 */
public final class LordWindgrace extends CardImpl {

    private static final FilterLandCard filter = new FilterLandCard("land cards from your graveyard");

    static {
        filter.add(TargetController.YOU.getOwnerPredicate());
    }

    public LordWindgrace(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{2}{B}{R}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.WINDGRACE);
        this.setStartingLoyalty(5);

        // +2: Discard a card, then draw a card. If a land card is discarded this way, draw an additional card.
        this.addAbility(new LoyaltyAbility(new LordWindgraceEffect(), 2));

        // -3: Return up to two target land cards from your graveyard to the battlefield.
        Ability ability = new LoyaltyAbility(
                new ReturnFromGraveyardToBattlefieldTargetEffect().setText("return up to two target land cards from your graveyard to the battlefield"), -3
        );
        ability.addTarget(new TargetCardInYourGraveyard(0, 2, filter));
        this.addAbility(ability);

        // -11: Destroy up to six target nonland permanents, then create six 2/2 green Cat Warrior creature tokens with forestwalk.
        ability = new LoyaltyAbility(new DestroyTargetEffect(), -11);
        ability.addEffect(
                new CreateTokenEffect(new CatWarriorToken(), 6)
                        .setText(", then create six 2/2 green Cat Warrior "
                                + "creature tokens with forestwalk")
        );
        ability.addTarget(new TargetNonlandPermanent(0, 6, StaticFilters.FILTER_PERMANENTS_NON_LAND, false));
        this.addAbility(ability);

        // Lord Windgrace can be your commander.
        this.addAbility(CanBeYourCommanderAbility.getInstance());
    }

    private LordWindgrace(final LordWindgrace card) {
        super(card);
    }

    @Override
    public LordWindgrace copy() {
        return new LordWindgrace(this);
    }
}

class LordWindgraceEffect extends OneShotEffect {

    public LordWindgraceEffect() {
        super(Outcome.Benefit);
        this.staticText = "discard a card, then draw a card. "
                + "If a land card is discarded this way, draw an additional card";
    }

    public LordWindgraceEffect(final LordWindgraceEffect effect) {
        super(effect);
    }

    @Override
    public LordWindgraceEffect copy() {
        return new LordWindgraceEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Card card = player.discardOne(false, false, source, game);
        if (card == null || !card.isLand(game)) {
            player.drawCards(1, source, game);
        } else {
            player.drawCards(2, source, game);
        }
        return true;
    }
}
