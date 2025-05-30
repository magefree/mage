package mage.cards.v;

import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.*;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.token.PilotSaddleCrewToken;
import mage.game.stack.StackObject;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ValorsFlagship extends CardImpl {

    public ValorsFlagship(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{4}{W}{W}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.VEHICLE);
        this.power = new MageInt(7);
        this.toughness = new MageInt(7);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // First strike
        this.addAbility(FirstStrikeAbility.getInstance());

        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());

        // Crew 3
        this.addAbility(new CrewAbility(3));

        // Cycling {X}{2}{W}
        this.addAbility(new CyclingAbility(new ManaCostsImpl<>("{X}{2}{W}")));

        // When you cycle this card, create X 1/1 colorless Pilot creature tokens with "This token saddles Mounts and crews Vehicles as though its power were 2 greater."
        this.addAbility(new ValorsFlagshipTriggeredAbility());
    }

    private ValorsFlagship(final ValorsFlagship card) {
        super(card);
    }

    @Override
    public ValorsFlagship copy() {
        return new ValorsFlagship(this);
    }
}

class ValorsFlagshipTriggeredAbility extends TriggeredAbilityImpl {

    ValorsFlagshipTriggeredAbility() {
        super(Zone.ALL, null);
    }

    private ValorsFlagshipTriggeredAbility(final ValorsFlagshipTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public ValorsFlagshipTriggeredAbility copy() {
        return new ValorsFlagshipTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ACTIVATED_ABILITY;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (!event.getSourceId().equals(this.getSourceId())) {
            return false;
        }
        StackObject object = game.getStack().getStackObject(event.getSourceId());
        if (object == null || !(object.getStackAbility() instanceof CyclingAbility)) {
            return false;
        }
        this.getEffects().clear();
        int amount = CardUtil.getSourceCostsTag(game, object.getStackAbility(), "X", 0);
        this.addEffect(new CreateTokenEffect(new PilotSaddleCrewToken(), amount));
        return true;
    }

    @Override
    public String getRule() {
        return "When you cycle this card, create X 1/1 colorless Pilot creature tokens with " +
                "\"This token saddles Mounts and crews Vehicles as though its power were 2 greater.\"";
    }
}
