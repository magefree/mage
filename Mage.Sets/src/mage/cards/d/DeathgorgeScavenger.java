
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldOrAttacksSourceTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInGraveyard;

/**
 *
 * @author TheElk801
 */
public final class DeathgorgeScavenger extends CardImpl {

    public DeathgorgeScavenger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");

        this.subtype.add(SubType.DINOSAUR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Whenever Deathgorge Scavenger enters the battlefield or attacks, you may exile target card from a graveyard. If a creature card is exiled this way, you gain 2 life. If a noncreature card is exiled this way, Deathgorge Scavenger gets +1/+1 until end of turn.
        Ability ability = new EntersBattlefieldOrAttacksSourceTriggeredAbility(new DeathgorgeScavengerEffect(), true);
        ability.addTarget(new TargetCardInGraveyard());
        this.addAbility(ability);
    }

    private DeathgorgeScavenger(final DeathgorgeScavenger card) {
        super(card);
    }

    @Override
    public DeathgorgeScavenger copy() {
        return new DeathgorgeScavenger(this);
    }
}

class DeathgorgeScavengerEffect extends OneShotEffect {

    public DeathgorgeScavengerEffect() {
        super(Outcome.Benefit);
        this.staticText = "exile target card from a graveyard. If a creature card is exiled this way, you gain 2 life. If a noncreature card is exiled this way, {this} gets +1/+1 until end of turn";
    }

    private DeathgorgeScavengerEffect(final DeathgorgeScavengerEffect effect) {
        super(effect);
    }

    @Override
    public DeathgorgeScavengerEffect copy() {
        return new DeathgorgeScavengerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Card card = game.getCard(getTargetPointer().getFirst(game, source));
            if (card != null) {
                controller.moveCards(card, Zone.EXILED, source, game);
                if (card.isCreature(game)) {
                    controller.gainLife(2, game, source);
                } else {
                    game.addEffect(new BoostSourceEffect(1, 1, Duration.EndOfTurn), source);
                }
            }
            return true;
        }
        return false;
    }

}
