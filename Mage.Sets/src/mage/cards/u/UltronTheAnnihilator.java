package mage.cards.u;

import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.token.RobotVillainToken;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.EntersBattlefieldOrAttacksSourceTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.LoseLifeOpponentsEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 * @author muz
 */
public final class UltronTheAnnihilator extends CardImpl {

    public UltronTheAnnihilator(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{3}{B}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ROBOT);
        this.subtype.add(SubType.VILLAIN);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever Ultron enters or attacks, create a 2/2 colorless Robot Villain artifact creature token.
        this.addAbility(new EntersBattlefieldOrAttacksSourceTriggeredAbility(
            new CreateTokenEffect(new RobotVillainToken())
        ));

        // Whenever another artifact is put into your graveyard from the battlefield or an artifact card is put into your graveyard from anywhere other than the battlefield, each opponent loses 1 life.
        this.addAbility(new UltronTheAnnihilatorTriggeredAbility());
    }

    private UltronTheAnnihilator(final UltronTheAnnihilator card) {
        super(card);
    }

    @Override
    public UltronTheAnnihilator copy() {
        return new UltronTheAnnihilator(this);
    }
}

class UltronTheAnnihilatorTriggeredAbility extends TriggeredAbilityImpl {

    UltronTheAnnihilatorTriggeredAbility() {
        super(Zone.BATTLEFIELD, new LoseLifeOpponentsEffect(1));
        setLeavesTheBattlefieldTrigger(true);
    }

    private UltronTheAnnihilatorTriggeredAbility(final UltronTheAnnihilatorTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public UltronTheAnnihilatorTriggeredAbility copy() {
        return new UltronTheAnnihilatorTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
        // Whenever another artifact enters your graveyard from the battlefield
        if (zEvent.isDiesEvent()
                && zEvent.isPermanentMoved()
                && !zEvent.getTargetId().equals(this.getSourceId())
                && zEvent.getTarget().isArtifact(game)) {
            return true;
        }
        Card card = game.getCard(zEvent.getTargetId());
        // Or an artifact card is put into a graveyard from anywhere other than the battlefield
        if (card == null || !card.isArtifact(game)) {
            return false;
        }
        if (zEvent.getToZone() == Zone.GRAVEYARD
                && zEvent.getFromZone() != Zone.BATTLEFIELD) {
            return true;
        }
        return false;
    }

    @Override
    public boolean isInUseableZone(Game game, MageObject sourceObject, GameEvent event) {
        return TriggeredAbilityImpl.isInUseableZoneDiesTrigger(this, sourceObject, event, game);
    }

    @Override
    public String getRule() {
        return "Whenever another artifact is put into your graveyard from the battlefield " +
            "or an artifact card is put into your graveyard from anywhere other than the battlefield, " +
            "each opponent loses 1 life.";
    }
}
