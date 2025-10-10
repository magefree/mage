package mage.cards.e;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.DefenderAttackedEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.token.CordycepsInfectedToken;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class EllieBrickMaster extends CardImpl {

    public EllieBrickMaster(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SURVIVOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Distract the Horde -- Whenever a player attacks one of your opponents, that attacking player creates a tapped 1/1 black Fungus Zombie creature token named Cordyceps Infected that's attacking that opponent.
        this.addAbility(new EllieBrickMasterTriggeredAbility());

        // Partner--Survivors
        this.addAbility(PartnerVariantType.SURVIVORS.makeAbility());
    }

    private EllieBrickMaster(final EllieBrickMaster card) {
        super(card);
    }

    @Override
    public EllieBrickMaster copy() {
        return new EllieBrickMaster(this);
    }
}

class EllieBrickMasterTriggeredAbility extends TriggeredAbilityImpl {

    EllieBrickMasterTriggeredAbility() {
        super(Zone.BATTLEFIELD, new EllieBrickMasterEffect());
        setTriggerPhrase("Whenever a player attacks one of your opponents, ");
        withFlavorWord("Distract the Horde");
    }

    private EllieBrickMasterTriggeredAbility(final EllieBrickMasterTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public EllieBrickMasterTriggeredAbility copy() {
        return new EllieBrickMasterTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DEFENDER_ATTACKED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        DefenderAttackedEvent dEvent = (DefenderAttackedEvent) event;
        if (!game.getOpponents(this.getControllerId()).contains(event.getTargetId())) {
            return false;
        }
        this.getEffects().setTargetPointer(new FixedTarget(event.getTargetId()));
        return true;
    }
}

class EllieBrickMasterEffect extends OneShotEffect {

    EllieBrickMasterEffect() {
        super(Outcome.Benefit);
        staticText = "that attacking player creates a tapped 1/1 black Fungus Zombie creature token " +
                "named Cordyceps Infected that's attacking that opponent";
    }

    private EllieBrickMasterEffect(final EllieBrickMasterEffect effect) {
        super(effect);
    }

    @Override
    public EllieBrickMasterEffect copy() {
        return new EllieBrickMasterEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return new CordycepsInfectedToken().putOntoBattlefield(
                1, game, source, game.getActivePlayerId(),
                true, true, getTargetPointer().getFirst(game, source)
        );
    }
}
