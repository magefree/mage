
package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.ChancellorAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.PlayTargetWithoutPayingManaEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.players.Player;
import mage.target.common.TargetCardInOpponentsGraveyard;

/**
 *
 * @author BetaSteward
 */
public final class ChancellorOfTheSpires extends CardImpl {

    private static final String abilityText = "at the beginning of the first upkeep, each opponent puts the top seven cards of their library into their graveyard";

    private static final FilterCard filter = new FilterCard("instant or sorcery card from an opponent's graveyard");

    static {
        filter.add(Predicates.or(
                new CardTypePredicate(CardType.INSTANT),
                new CardTypePredicate(CardType.SORCERY)));
    }

    public ChancellorOfTheSpires(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{U}{U}{U}");
        this.subtype.add(SubType.SPHINX);

        this.power = new MageInt(5);
        this.toughness = new MageInt(7);

        // You may reveal this card from your opening hand. If you do, at the beginning of the first upkeep, each opponent puts the top seven cards of their library into their graveyard.
        this.addAbility(new ChancellorAbility(new ChancellorOfTheSpiresDelayedTriggeredAbility(), abilityText));

        this.addAbility(FlyingAbility.getInstance());

        // When Chancellor of the Spires enters the battlefield, you may cast target instant or sorcery card from an opponent's graveyard without paying its mana cost.
        Ability ability = new EntersBattlefieldTriggeredAbility(new PlayTargetWithoutPayingManaEffect(), true);
        ability.addTarget(new TargetCardInOpponentsGraveyard(filter));
        this.addAbility(ability);
    }

    public ChancellorOfTheSpires(final ChancellorOfTheSpires card) {
        super(card);
    }

    @Override
    public ChancellorOfTheSpires copy() {
        return new ChancellorOfTheSpires(this);
    }
}

class ChancellorOfTheSpiresDelayedTriggeredAbility extends DelayedTriggeredAbility {

    ChancellorOfTheSpiresDelayedTriggeredAbility () {
        super(new ChancellorOfTheSpiresEffect());
    }

    ChancellorOfTheSpiresDelayedTriggeredAbility(ChancellorOfTheSpiresDelayedTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.UPKEEP_STEP_PRE;
    }
    
    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return true;
    }
    @Override
    public ChancellorOfTheSpiresDelayedTriggeredAbility copy() {
        return new ChancellorOfTheSpiresDelayedTriggeredAbility(this);
    }
}

class ChancellorOfTheSpiresEffect extends OneShotEffect {

    ChancellorOfTheSpiresEffect () {
        super(Outcome.Benefit);
        staticText = "each opponent puts the top seven cards of their library into their graveyard";
    }

    ChancellorOfTheSpiresEffect(ChancellorOfTheSpiresEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (UUID opponentId : game.getOpponents(source.getControllerId())) {
            Player opponent = game.getPlayer(opponentId);
            if (opponent != null) {
                opponent.moveCards(opponent.getLibrary().getTopCards(game, 7), Zone.GRAVEYARD, source, game);
            }
        }
        return true;
    }

    @Override
    public ChancellorOfTheSpiresEffect copy() {
        return new ChancellorOfTheSpiresEffect(this);
    }

}

