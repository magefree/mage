package mage.cards.c;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.ChancellorAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.PlayTargetWithoutPayingManaEffect;
import mage.abilities.effects.common.MillCardsEachPlayerEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.target.common.TargetCardInOpponentsGraveyard;

import java.util.UUID;

/**
 * @author BetaSteward
 */
public final class ChancellorOfTheSpires extends CardImpl {

    private static final String abilityText = "at the beginning of the first upkeep, each opponent mills seven cards";

    private static final FilterCard filter = new FilterCard("instant or sorcery card from an opponent's graveyard");

    static {
        filter.add(Predicates.or(
                CardType.INSTANT.getPredicate(),
                CardType.SORCERY.getPredicate()));
    }

    public ChancellorOfTheSpires(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{U}{U}{U}");
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

    private ChancellorOfTheSpires(final ChancellorOfTheSpires card) {
        super(card);
    }

    @Override
    public ChancellorOfTheSpires copy() {
        return new ChancellorOfTheSpires(this);
    }
}

class ChancellorOfTheSpiresDelayedTriggeredAbility extends DelayedTriggeredAbility {

    ChancellorOfTheSpiresDelayedTriggeredAbility() {
        super(new MillCardsEachPlayerEffect(7, TargetController.OPPONENT));
    }

    private ChancellorOfTheSpiresDelayedTriggeredAbility(ChancellorOfTheSpiresDelayedTriggeredAbility ability) {
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
