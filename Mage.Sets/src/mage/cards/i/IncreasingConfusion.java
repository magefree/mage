
package mage.cards.i;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlashbackAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.target.TargetPlayer;

/**
 *
 * @author BetaSteward
 */
public final class IncreasingConfusion extends CardImpl {

    public IncreasingConfusion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{X}{U}");


        // Target player puts the top X cards of their library into their graveyard. If this spell was cast from a graveyard, that player puts twice that many cards into their graveyard instead.
        this.getSpellAbility().addEffect(new IncreasingConfusionEffect());
        this.getSpellAbility().addTarget(new TargetPlayer());

        // Flashback {X}{U}
        this.addAbility(new FlashbackAbility(this, new ManaCostsImpl("{X}{U}")));
    }

    private IncreasingConfusion(final IncreasingConfusion card) {
        super(card);
    }

    @Override
    public IncreasingConfusion copy() {
        return new IncreasingConfusion(this);
    }
}

class IncreasingConfusionEffect extends OneShotEffect {

    public IncreasingConfusionEffect() {
        super(Outcome.Detriment);
        staticText = "Target player mills X cards. If this spell was cast from a graveyard, that player mills twice that many cards";
    }

    public IncreasingConfusionEffect(final IncreasingConfusionEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getFirstTarget());
        if (player != null) {
            int amount = source.getManaCostsToPay().getX();
            Spell spell = (Spell) game.getStack().getStackObject(source.getSourceId());
            if (spell != null) {
                if (spell.getFromZone() == Zone.GRAVEYARD) {
                    amount *= 2;
                }
                player.millCards(amount, source, game);
                return true;
            }
        }
        return false;
    }

    @Override
    public IncreasingConfusionEffect copy() {
        return new IncreasingConfusionEffect(this);
    }

}