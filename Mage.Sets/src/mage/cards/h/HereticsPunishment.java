
package mage.cards.h;

import java.util.Set;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetAnyTarget;

/**
 *
 * @author BetaSteward
 */
public final class HereticsPunishment extends CardImpl {

    public HereticsPunishment(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{4}{R}");

        // {3}{R}: Choose any target, then put the top three cards of your library into your graveyard. Heretic's Punishment deals damage to that creature or player equal to the highest converted mana cost among those cards.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new HereticsPunishmentEffect(), new ManaCostsImpl("{3}{R}"));
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);
    }

    public HereticsPunishment(final HereticsPunishment card) {
        super(card);
    }

    @Override
    public HereticsPunishment copy() {
        return new HereticsPunishment(this);
    }
}

class HereticsPunishmentEffect extends OneShotEffect {

    public HereticsPunishmentEffect() {
        super(Outcome.Damage);
        staticText = "Choose any target, then put the top three cards of your library into your graveyard. {this} deals damage to that permanent or player equal to the highest converted mana cost among those cards";
    }

    public HereticsPunishmentEffect(final HereticsPunishmentEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            int maxCost = 0;
            Set<Card> cardList = controller.getLibrary().getTopCards(game, 3);
            for (Card card : cardList) {
                int test = card.getConvertedManaCost();
                if (test > maxCost) {
                    maxCost = test;
                }
            }
            controller.moveCards(cardList, Zone.GRAVEYARD, source, game);
            Permanent permanent = game.getPermanent(targetPointer.getFirst(game, source));
            if (permanent != null) {
                permanent.damage(maxCost, source.getSourceId(), game, false, true);
                return true;
            }
            Player targetPlayer = game.getPlayer(targetPointer.getFirst(game, source));
            if (targetPlayer != null) {
                targetPlayer.damage(maxCost, source.getSourceId(), game, false, true);
                return true;
            }
        }
        return false;
    }

    @Override
    public HereticsPunishmentEffect copy() {
        return new HereticsPunishmentEffect(this);
    }

}
