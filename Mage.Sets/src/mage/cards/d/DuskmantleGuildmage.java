
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.abilities.effects.common.PutLibraryIntoGraveTargetEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.events.ZoneChangeEvent;
import mage.target.TargetPlayer;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author jeffwadsworth and LevelX2
 */
public final class DuskmantleGuildmage extends CardImpl {

    public DuskmantleGuildmage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{U}{B}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // {1}{U}{B}: Whenever a card is put into an opponent's graveyard from anywhere this turn, that player loses 1 life.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new CreateDelayedTriggeredAbilityEffect(new CardPutIntoOpponentGraveThisTurn()), new ManaCostsImpl<>("{1}{U}{B}")));

        // {2}{U}{B}: Target player puts the top two cards of their library into their graveyard.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new PutLibraryIntoGraveTargetEffect(2), new ManaCostsImpl<>("{2}{U}{B}"));
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);
    }

    private DuskmantleGuildmage(final DuskmantleGuildmage card) {
        super(card);
    }

    @Override
    public DuskmantleGuildmage copy() {
        return new DuskmantleGuildmage(this);
    }
}

class CardPutIntoOpponentGraveThisTurn extends DelayedTriggeredAbility {

    public CardPutIntoOpponentGraveThisTurn() {
        super(new LoseLifeTargetEffect(1), Duration.EndOfTurn, false);
    }

    public CardPutIntoOpponentGraveThisTurn(final CardPutIntoOpponentGraveThisTurn ability) {
        super(ability);
    }

    @Override
    public CardPutIntoOpponentGraveThisTurn copy() {
        return new CardPutIntoOpponentGraveThisTurn(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
        UUID cardId = event.getTargetId();
        Card card = game.getCard(cardId);
        if (card != null && zEvent.getToZone() == Zone.GRAVEYARD && !card.isCopy()
                && game.getOpponents(controllerId).contains(card.getOwnerId())) {
            for (Effect effect : this.getEffects()) {
                effect.setTargetPointer(new FixedTarget(card.getOwnerId()));
            }
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever a card is put into an opponent's graveyard from anywhere this turn, that player loses 1 life";
    }
}