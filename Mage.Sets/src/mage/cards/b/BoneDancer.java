
package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksAndIsNotBlockedTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.AssignNoCombatDamageSourceEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author LoneFox
 */
public final class BoneDancer extends CardImpl {

    public BoneDancer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{B}{B}");
        this.subtype.add(SubType.ZOMBIE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever Bone Dancer attacks and isn't blocked, you may put the top creature card of defending player's graveyard onto the battlefield under your control. If you do, Bone Dancer assigns no combat damage this turn.
        Ability ability = new AttacksAndIsNotBlockedTriggeredAbility(new BoneDancerEffect(), true, true);
        ability.addEffect(new AssignNoCombatDamageSourceEffect(Duration.EndOfTurn, true));
        this.addAbility(ability);
    }

    private BoneDancer(final BoneDancer card) {
        super(card);
    }

    @Override
    public BoneDancer copy() {
        return new BoneDancer(this);
    }
}

class BoneDancerEffect extends OneShotEffect {

    public BoneDancerEffect() {
        super(Outcome.Benefit);
        this.staticText = "put the top creature card of defending player's graveyard onto the battlefield under your control";
    }

    private BoneDancerEffect(final BoneDancerEffect effect) {
        super(effect);
    }

    @Override
    public BoneDancerEffect copy() {
        return new BoneDancerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Player defendingPlayer = game.getPlayer(targetPointer.getFirst(game, source));
        if (controller != null && defendingPlayer != null) {
            Card lastCreatureCard = null;
            for (Card card : defendingPlayer.getGraveyard().getCards(game)) {
                if (card.isCreature(game)) {
                    lastCreatureCard = card;
                }
            }
            if (lastCreatureCard != null) {
                controller.moveCards(lastCreatureCard, Zone.BATTLEFIELD, source, game);
            }
            return true;
        }
        return false;
    }
}
