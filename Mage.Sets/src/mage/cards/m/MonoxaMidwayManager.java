package mage.cards.m;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.RollDiceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.DieRolledEvent;
import mage.game.events.GameEvent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MonoxaMidwayManager extends CardImpl {

    public MonoxaMidwayManager(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.VAMPIRE);
        this.subtype.add(SubType.EMPLOYEE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Whenever you roll a 3 or higher, Monoxa, Midway Manager gains first strike until end of turn. If the roll was 4 or higher, it gains menace until end of turn. If the roll was 5 or higher, it gains lifelink until end of turn.
        this.addAbility(new MonoxaMidwayManagerTriggeredAbility());

        // {6}: Roll a six-sided die.
        this.addAbility(new SimpleActivatedAbility(new RollDiceEffect(null, 6), new GenericManaCost(6)));
    }

    private MonoxaMidwayManager(final MonoxaMidwayManager card) {
        super(card);
    }

    @Override
    public MonoxaMidwayManager copy() {
        return new MonoxaMidwayManager(this);
    }
}

class MonoxaMidwayManagerTriggeredAbility extends TriggeredAbilityImpl {

    MonoxaMidwayManagerTriggeredAbility() {
        super(Zone.BATTLEFIELD, new GainAbilitySourceEffect(
                FirstStrikeAbility.getInstance(), Duration.EndOfTurn
        ));
        this.addEffect(new MonoxaMidwayManagerEffect());
    }

    private MonoxaMidwayManagerTriggeredAbility(final MonoxaMidwayManagerTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public MonoxaMidwayManagerTriggeredAbility copy() {
        return new MonoxaMidwayManagerTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DIE_ROLLED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        int result = ((DieRolledEvent) event).getResult();
        if (!isControlledBy(event.getPlayerId()) || result < 3) {
            return false;
        }
        this.getEffects().setValue("dieRoll", result);
        return true;
    }

    @Override
    public String getRule() {
        return "Whenever you roll a 3 or higher, {this} gains first strike until end of turn. " +
                "If the roll was 4 or higher, it gains menace until end of turn. " +
                "If the roll was 5 or higher, it gains lifelink until end of turn.";
    }
}

class MonoxaMidwayManagerEffect extends OneShotEffect {

    MonoxaMidwayManagerEffect() {
        super(Outcome.Benefit);
    }

    private MonoxaMidwayManagerEffect(final MonoxaMidwayManagerEffect effect) {
        super(effect);
    }

    @Override
    public MonoxaMidwayManagerEffect copy() {
        return new MonoxaMidwayManagerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int result = (Integer) getValue("dieRoll");
        if (result > 4) {
            game.addEffect(new GainAbilitySourceEffect(
                    new MenaceAbility(), Duration.EndOfTurn
            ), source);
        }
        if (result > 5) {
            game.addEffect(new GainAbilitySourceEffect(
                    LifelinkAbility.getInstance(), Duration.EndOfTurn
            ), source);
        }
        return true;
    }
}
