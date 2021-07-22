
package mage.cards.s;

import java.util.List;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.condition.common.KickedCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.KickerAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author North
 */
public final class SadisticSacrament extends CardImpl {

    private static final String ruleText = "Search target player's library for up to three cards, exile them, then that player shuffles. If this spell was kicked, instead search that player's library for up to fifteen cards, exile them, then that player shuffles";

    public SadisticSacrament(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{B}{B}{B}");

        this.color.setBlack(true);

        // Kicker {7}
        this.addAbility(new KickerAbility("{7}"));

        // Search target player's library for up to three cards, exile them, then that player shuffles their library.
        // If Sadistic Sacrament was kicked, instead search that player's library for up to fifteen cards, exile them, then that player shuffles their library.
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new SadisticSacramentEffect(15),
                new SadisticSacramentEffect(3),
                KickedCondition.instance,
                ruleText));
        this.getSpellAbility().addTarget(new TargetPlayer());
    }

    private SadisticSacrament(final SadisticSacrament card) {
        super(card);
    }

    @Override
    public SadisticSacrament copy() {
        return new SadisticSacrament(this);
    }
}

class SadisticSacramentEffect extends OneShotEffect {

    private int amount;

    public SadisticSacramentEffect(int amount) {
        super(Outcome.Exile);
        this.amount = amount;
    }

    public SadisticSacramentEffect(final SadisticSacramentEffect effect) {
        super(effect);
        this.amount = effect.amount;
    }

    @Override
    public SadisticSacramentEffect copy() {
        return new SadisticSacramentEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player targetPlayer = game.getPlayer(source.getFirstTarget());
        Player player = game.getPlayer(source.getControllerId());
        if (player != null && targetPlayer != null) {
            TargetCardInLibrary target = new TargetCardInLibrary(0, amount, new FilterCard("cards to exile"));
            if (player.searchLibrary(target, source, game, targetPlayer.getId())) {
                List<UUID> targets = target.getTargets();
                for (UUID targetId : targets) {
                    Card card = targetPlayer.getLibrary().remove(targetId, game);
                    if (card != null) {
                        card.moveToExile(null, "", source, game);
                    }
                }
            }
            targetPlayer.shuffleLibrary(source, game);
            return true;
        }

        return false;
    }
}
