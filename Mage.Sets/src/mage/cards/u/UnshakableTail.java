package mage.cards.u;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.ReturnSourceFromGraveyardToHandEffect;
import mage.abilities.effects.keyword.InvestigateEffect;
import mage.abilities.effects.keyword.SurveilEffect;
import mage.cards.Card;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeGroupEvent;

/**
 *
 * @author kleese
 */
public final class UnshakableTail extends CardImpl {

    public UnshakableTail(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");
        
        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.DETECTIVE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // When Unshakable Tail enters the battlefield and at the beginning of your upkeep, surveil 1.
        this.addAbility(new UnshakableTailSurveilTriggeredAbility());

        // Whenever one or more creature cards are put into your graveyard from your library, investigate.
        this.addAbility(new UnshakableTailInvestigateTriggeredAbility());

        // {2}, Sacrifice a Clue: Return Unshakable Tail from your graveyard to your hand.
        Ability ability = new SimpleActivatedAbility(
                Zone.GRAVEYARD, new ReturnSourceFromGraveyardToHandEffect(), new ManaCostsImpl<>("{2}")
        );
        ability.addCost(new SacrificeTargetCost(StaticFilters.FILTER_CONTROLLED_CLUE));
        this.addAbility(ability);
    }

    private UnshakableTail(final UnshakableTail card) {
        super(card);
    }

    @Override
    public UnshakableTail copy() {
        return new UnshakableTail(this);
    }
}

class UnshakableTailSurveilTriggeredAbility extends TriggeredAbilityImpl {

    UnshakableTailSurveilTriggeredAbility() {
        super(Zone.BATTLEFIELD, new SurveilEffect(1));
    }

    private UnshakableTailSurveilTriggeredAbility(final UnshakableTailSurveilTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public UnshakableTailSurveilTriggeredAbility copy() {
        return new UnshakableTailSurveilTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD
                || event.getType() == GameEvent.EventType.UPKEEP_STEP_PRE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD) {
            return event.getTargetId().equals(getSourceId());
        }
        return game.isActivePlayer(getControllerId());
    }

    @Override
    public String getRule() {
        return "When {this} enters the battlefield and at the beginning of your upkeep, surveil 1.";
    }
}

class UnshakableTailInvestigateTriggeredAbility extends TriggeredAbilityImpl {

    public UnshakableTailInvestigateTriggeredAbility() {
        super(Zone.BATTLEFIELD, new InvestigateEffect(), false);
    }

    private UnshakableTailInvestigateTriggeredAbility(final UnshakableTailInvestigateTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE_GROUP;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        ZoneChangeGroupEvent zEvent = (ZoneChangeGroupEvent) event;
        if (zEvent != null
                && Zone.LIBRARY == zEvent.getFromZone()
                && Zone.GRAVEYARD == zEvent.getToZone()
                && zEvent.getCards() != null) {
            for (Card card : zEvent.getCards()) {
                if (card != null) {
                    UUID cardOwnerId = card.getOwnerId();
                    if (cardOwnerId != null
                            && card.isOwnedBy(getControllerId())
                            && card.isCreature(game)) {
                        return true;
                    }
                }

            }
        }
        return false;
    }

    @Override
    public UnshakableTailInvestigateTriggeredAbility copy() {
        return new UnshakableTailInvestigateTriggeredAbility(this);
    }

    @Override
    public String getRule() {
        return "Whenever one or more creature cards are put into your graveyard from your library, investigate.";
    }
}
