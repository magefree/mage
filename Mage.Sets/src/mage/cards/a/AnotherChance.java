package mage.cards.a;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.OneShotNonTargetEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class AnotherChance extends CardImpl {

    public AnotherChance(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{B}");

        // You may mill two cards. Then return up to two creature cards from your graveyard to your hand.
        this.getSpellAbility().addEffect(new AnotherChanceMillEffect());
        this.getSpellAbility().addEffect(new OneShotNonTargetEffect(new ReturnFromGraveyardToHandTargetEffect().setText("Then return up to two creature cards from your graveyard to your hand."),
                new TargetCardInYourGraveyard(0, 2, StaticFilters.FILTER_CARD_CREATURES_YOUR_GRAVEYARD, true)).withTargetDescription("up to two creature cards").concatBy("Then "));
    }

    private AnotherChance(final AnotherChance card) {
        super(card);
    }

    @Override
    public AnotherChance copy() {
        return new AnotherChance(this);
    }
}

class AnotherChanceMillEffect extends OneShotEffect {

    AnotherChanceMillEffect() {
        super(Outcome.Benefit);
        staticText = "You may mill two cards.";
    }

    private AnotherChanceMillEffect(final AnotherChanceMillEffect effect) {
        super(effect);
    }

    @Override
    public AnotherChanceMillEffect copy() {
        return new AnotherChanceMillEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }

        if (player.chooseUse(outcome, "Mill two cards?", source, game)) {
            player.millCards(2, source, game);
        }
        return true;
    }
}
