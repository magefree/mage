
package mage.cards.t;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlashbackAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;

/**
 *
 * @author cbt33, Cloudthresher (LevelX2), Cranial Extraction (BetaSteward)
 */
public final class Tombfire extends CardImpl {

    public Tombfire(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{B}");


        // Target player exiles all cards with flashback from their graveyard.
        this.getSpellAbility().addTarget(new TargetPlayer());
        this.getSpellAbility().addEffect(new TombfireEffect());

    }

    private Tombfire(final Tombfire card) {
        super(card);
    }

    @Override
    public Tombfire copy() {
        return new Tombfire(this);
    }
}

class TombfireEffect extends OneShotEffect {

    private static final FilterCard filter = new FilterCard("cards with flashback");

    static {
        filter.add(new AbilityPredicate(FlashbackAbility.class));
    }

    public TombfireEffect() {
        super(Outcome.Exile);
        staticText = "Target player exiles all cards with flashback from their graveyard";
    }

    private TombfireEffect(final TombfireEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player targetPlayer = game.getPlayer(targetPointer.getFirst(game, source));
        if (targetPlayer != null) {
            for (Card card : targetPlayer.getGraveyard().getCards(filter, game)) {
                card.moveToExile(null, "", source, game);
            }
            return true;
        } 
        return false;

    }

    @Override
    public TombfireEffect copy() {
        return new TombfireEffect(this);
    }
}
