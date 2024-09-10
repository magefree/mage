package mage.cards.t;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.common.ChooseACardNameEffect;
import mage.abilities.effects.common.search.SearchTargetGraveyardHandLibraryForCardNameAndExileEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class ThoughtHemorrhage extends CardImpl {

    public ThoughtHemorrhage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{B}{R}");

        // Name a nonland card. Target player reveals their hand. Thought 
        // Hemorrhage deals 3 damage to that player for each card with that 
        // name revealed this way. Search that player's graveyard, hand, and 
        // library for all cards with that name and exile them. Then that 
        // player shuffles their library.
        this.getSpellAbility().addTarget(new TargetPlayer());
        this.getSpellAbility().addEffect(new ChooseACardNameEffect(
                ChooseACardNameEffect.TypeOfName.NON_LAND_NAME));
        this.getSpellAbility().addEffect(new ThoughtHemorrhageEffect());
    }

    private ThoughtHemorrhage(final ThoughtHemorrhage card) {
        super(card);
    }

    @Override
    public ThoughtHemorrhage copy() {
        return new ThoughtHemorrhage(this);
    }
}

class ThoughtHemorrhageEffect extends SearchTargetGraveyardHandLibraryForCardNameAndExileEffect {

    static final String rule = "Target player reveals their hand. "
            + "{this} deals 3 damage to that player for each card with "
            + "the chosen name revealed this way. "
            + "Search that player's graveyard, hand, and library for "
            + "all cards with that name and exile them. "
            + "Then that player shuffles";

    public ThoughtHemorrhageEffect() {
        super(false, "that player's", "all cards with that name");
        staticText = rule;
    }

    private ThoughtHemorrhageEffect(final ThoughtHemorrhageEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = game.getObject(source);
        String cardName = (String) game.getState().getValue(
                source.getSourceId().toString() + ChooseACardNameEffect.INFO_KEY);
        if (sourceObject != null
                && controller != null
                && cardName != null
                && !cardName.isEmpty()) {
            Player targetPlayer = game.getPlayer(source.getFirstTarget());
            if (targetPlayer != null) {
                targetPlayer.revealCards("hand of "
                        + targetPlayer.getName(), targetPlayer.getHand(), game);
                int cardsFound = 0;
                for (Card card : targetPlayer.getHand().getCards(game)) {
                    if (CardUtil.haveSameNames(card, cardName, game)) {
                        cardsFound++;
                    }
                }
                if (cardsFound > 0) {
                    targetPlayer.damage(3 * cardsFound, source.getSourceId(), source, game);
                }

                return applySearchAndExile(game, source, cardName, source.getFirstTarget());
            }
        }
        return false;
    }

    @Override
    public ThoughtHemorrhageEffect copy() {
        return new ThoughtHemorrhageEffect(this);
    }
}
