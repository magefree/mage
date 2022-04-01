package mage.cards.s;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DamagePlayersEffect;
import mage.abilities.effects.common.MillCardsEachPlayerEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SyrKonradTheGrim extends CardImpl {

    public SyrKonradTheGrim(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}{B}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(5);
        this.toughness = new MageInt(4);

        // Whenever another creature dies, or a creature card is put into a graveyard from anywhere other than the battlefield, or a creature card leaves your graveyard, Syr Konrad, the Grim deals 1 damage to each opponent.
        this.addAbility(new SyrKonradTheGrimTriggeredAbility());

        // {1}{B}: Each player puts the top card of their library into their graveyard.
        this.addAbility(new SimpleActivatedAbility(new MillCardsEachPlayerEffect(
                1, TargetController.ANY
        ), new ManaCostsImpl("{1}{B}")));
    }

    private SyrKonradTheGrim(final SyrKonradTheGrim card) {
        super(card);
    }

    @Override
    public SyrKonradTheGrim copy() {
        return new SyrKonradTheGrim(this);
    }
}

class SyrKonradTheGrimTriggeredAbility extends TriggeredAbilityImpl {

    SyrKonradTheGrimTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DamagePlayersEffect(1, TargetController.OPPONENT));
    }

    private SyrKonradTheGrimTriggeredAbility(final SyrKonradTheGrimTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public SyrKonradTheGrimTriggeredAbility copy() {
        return new SyrKonradTheGrimTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
        // Whenever another creature dies
        if (zEvent.isDiesEvent()
                && zEvent.getTarget() != null
                && !zEvent.getTargetId().equals(this.getSourceId())
                && zEvent.getTarget().isCreature(game)) {
            return true;
        }
        Card card = game.getCard(zEvent.getTargetId());
        // Or a creature card is put into a graveyard from anywhere other than the battlefield
        if (card == null || !card.isCreature(game)) {
            return false;
        }
        if (zEvent.getToZone() == Zone.GRAVEYARD
                && zEvent.getFromZone() != Zone.BATTLEFIELD) {
            return true;
        }
        // Or a creature card leaves your graveyard
        return zEvent.getFromZone() == Zone.GRAVEYARD
                && card.isOwnedBy(this.getControllerId());
    }

    @Override
    public boolean isInUseableZone(Game game, MageObject source, GameEvent event) {
        return TriggeredAbilityImpl.isInUseableZoneDiesTrigger(this, event, game);
    }

    @Override
    public String getRule() {
        return "Whenever another creature dies, or a creature card is put into a graveyard " +
                "from anywhere other than the battlefield, or a creature card leaves your graveyard, " +
                "{this} deals 1 damage to each opponent.";
    }
}
