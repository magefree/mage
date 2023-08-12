package mage.cards.m;

import mage.ApprovingObject;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ExileSpellEffect;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInGraveyard;
import org.apache.log4j.Logger;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MnemonicDeluge extends CardImpl {

    public MnemonicDeluge(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{6}{U}{U}{U}");

        // Exile target instant or sorcery card from a graveyard. Copy that card three times. You may cast the copies without paying their mana costs. Exile Mnemonic Deluge.
        this.getSpellAbility().addEffect(new MnemonicDelugeEffect());
        this.getSpellAbility().addEffect(new ExileSpellEffect());
        this.getSpellAbility().addTarget(new TargetCardInGraveyard(StaticFilters.FILTER_CARD_INSTANT_OR_SORCERY));
    }

    private MnemonicDeluge(final MnemonicDeluge card) {
        super(card);
    }

    @Override
    public MnemonicDeluge copy() {
        return new MnemonicDeluge(this);
    }
}

class MnemonicDelugeEffect extends OneShotEffect {

    MnemonicDelugeEffect() {
        super(Outcome.Benefit);
        staticText = "Exile target instant or sorcery card from a graveyard. " +
                "Copy that card three times. You may cast the copies without paying their mana costs.";
    }

    private MnemonicDelugeEffect(final MnemonicDelugeEffect effect) {
        super(effect);
    }

    @Override
    public MnemonicDelugeEffect copy() {
        return new MnemonicDelugeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Card card = game.getCard(source.getFirstTarget());
        if (player == null || card == null) {
            return false;
        }
        player.moveCards(card, Zone.EXILED, source, game);
        Cards cards = new CardsImpl();
        for (int i = 0; i < 3; i++) {
            Card copiedCard = game.copyCard(card, source, source.getControllerId());
            cards.add(copiedCard);
        }
        for (Card copiedCard : cards.getCards(game)) {
            if (!player.chooseUse(outcome, "Cast the copied card?", source, game)) {
                continue;
            }
            if (copiedCard.getSpellAbility() != null) {
                game.getState().setValue("PlayFromNotOwnHandZone" + copiedCard.getId(), Boolean.TRUE);
                player.cast(player.chooseAbilityForCast(copiedCard, game, true),
                        game, true, new ApprovingObject(source, game));
                game.getState().setValue("PlayFromNotOwnHandZone" + copiedCard.getId(), null);
            } else {
                Logger.getLogger(MnemonicDelugeEffect.class).error("Mnemonic Deluge: "
                        + "spell ability == null " + copiedCard.getName());
            }
        }
        return true;
    }
}
