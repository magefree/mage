package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.HideawayPlayEffect;
import mage.abilities.keyword.HideawayAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CemeteryTampering extends CardImpl {

    public CemeteryTampering(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{B}");

        // Hideaway 5
        this.addAbility(new HideawayAbility(5));

        // At the beginning of your upkeep, you may mill three cards. Then if there are twenty or more cards in your graveyard, you may play the exiled card without paying its mana cost.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(
                new CemeteryTamperingEffect(), TargetController.YOU, false
        ));
    }

    private CemeteryTampering(final CemeteryTampering card) {
        super(card);
    }

    @Override
    public CemeteryTampering copy() {
        return new CemeteryTampering(this);
    }
}

class CemeteryTamperingEffect extends OneShotEffect {

    CemeteryTamperingEffect() {
        super(Outcome.Benefit);
        staticText = "you may mill three cards. Then if there are twenty or more cards in your graveyard, " +
                "you may play the exiled card without paying its mana cost";
    }

    private CemeteryTamperingEffect(final CemeteryTamperingEffect effect) {
        super(effect);
    }

    @Override
    public CemeteryTamperingEffect copy() {
        return new CemeteryTamperingEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        if (player.chooseUse(outcome, "Mill three cards?", source, game)) {
            player.millCards(3, source, game);
        }
        if (player.getGraveyard().size() >= 20) {
            new HideawayPlayEffect().apply(game, source);
        }
        return true;
    }
}
