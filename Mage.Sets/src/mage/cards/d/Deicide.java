package mage.cards.d;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.common.search.SearchTargetGraveyardHandLibraryForCardNameAndExileEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetEnchantmentPermanent;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class Deicide extends CardImpl {

    public Deicide(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{W}");

        // Exile target enchantment. If the exiled card is a God card, search its controller's graveyard, hand, and library for any number of cards with the same name as that card and exile them, then that player shuffles their library.
        this.getSpellAbility().addEffect(new DeicideExileEffect());
        this.getSpellAbility().addTarget(new TargetEnchantmentPermanent());
    }

    private Deicide(final Deicide card) {
        super(card);
    }

    @Override
    public Deicide copy() {
        return new Deicide(this);
    }
}

class DeicideExileEffect extends SearchTargetGraveyardHandLibraryForCardNameAndExileEffect {

    DeicideExileEffect() {
        super(true, "its controller's", "any number of cards with the same name as that card");
        this.staticText = "Exile target enchantment. If the exiled card is a God card, " + this.staticText;
    }

    private DeicideExileEffect(final DeicideExileEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Card sourceCard = game.getCard(source.getSourceId());
        if (controller == null || sourceCard == null) {
            return false;
        }
        Permanent targetEnchantment = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (targetEnchantment == null) {
            return false;
        }
        controller.moveCards(targetEnchantment, Zone.EXILED, source, game);
        // 4/26/2014
        // Deicide looks at the card in exile, not the permanent that was exiled, to determine
        // if it is a God. For each of the Gods in the Theros block, it won't matter what your
        // devotion to its color(s) was. The card is a God card when not on the battlefield.
        Card cardInExile = game.getExile().getCard(targetEnchantment.getId(), game);
        if (cardInExile != null && cardInExile.hasSubtype(SubType.GOD, game)) {
            Player enchantmentController = game.getPlayer(targetEnchantment.getControllerId());
            return enchantmentController != null && super.applySearchAndExile(game, source, cardInExile.getName(), enchantmentController.getId());
        }
        return false;
    }

    @Override
    public DeicideExileEffect copy() {
        return new DeicideExileEffect(this);
    }
}
