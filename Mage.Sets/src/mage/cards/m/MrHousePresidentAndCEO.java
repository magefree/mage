package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.OneShotEffect;
import mage.constants.*;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.game.Game;
import mage.game.events.DieRolledEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.token.RobotToken;
import mage.game.permanent.token.Token;
import mage.game.permanent.token.TreasureToken;

/**
 *
 * @author jimga150
 */
public final class MrHousePresidentAndCEO extends CardImpl {

    public MrHousePresidentAndCEO(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{R}{W}{B}");
        
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.power = new MageInt(0);
        this.toughness = new MageInt(4);

        // Whenever you roll a 4 or higher, create a 3/3 colorless Robot artifact creature token. If you rolled 6 or higher, instead create that token and a Treasure token.
        this.addAbility(new MrHousePresidentAndCEOTriggeredAbility());

        // {4}, {T}: Roll a six-sided die plus an additional six-sided die for each mana from Treasures spent to activate this ability.
    }

    private MrHousePresidentAndCEO(final MrHousePresidentAndCEO card) {
        super(card);
    }

    @Override
    public MrHousePresidentAndCEO copy() {
        return new MrHousePresidentAndCEO(this);
    }
}

// Based on As Luck Would Have It
class MrHousePresidentAndCEOTriggeredAbility extends TriggeredAbilityImpl {

    public MrHousePresidentAndCEOTriggeredAbility() {
        super(Zone.BATTLEFIELD, new MrHousePresidentAndCEOEffect(), false);
    }

    private MrHousePresidentAndCEOTriggeredAbility(final MrHousePresidentAndCEOTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public MrHousePresidentAndCEOTriggeredAbility copy() {
        return new MrHousePresidentAndCEOTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DIE_ROLLED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        DieRolledEvent drEvent = (DieRolledEvent) event;
        if (this.isControlledBy(event.getPlayerId()) && drEvent.getRollDieType() == RollDieType.NUMERICAL) {
            // looks for "result" instead "natural result"
            int result = drEvent.getResult();
            this.getEffects().setValue("rolled", result);
            return result >= 4;
        }
        return false;
    }

    @Override
    public String getRule(){
        return "Whenever you roll a 4 or higher, create a 3/3 colorless Robot artifact creature token. " +
                "If you rolled 6 or higher, instead create that token and a Treasure token.";
    }
}

class MrHousePresidentAndCEOEffect extends OneShotEffect {

    public MrHousePresidentAndCEOEffect() {
        super(Outcome.Benefit);
    }

    private MrHousePresidentAndCEOEffect(final MrHousePresidentAndCEOEffect effect) {
        super(effect);
    }

    @Override
    public MrHousePresidentAndCEOEffect copy() {
        return new MrHousePresidentAndCEOEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        if (getValue("rolled") == null) {
            return false;
        }
        int amount = (Integer) getValue("rolled");

        if (amount >= 6){
            Token treasureToken = new TreasureToken();
            treasureToken.putOntoBattlefield(1, game, source);
        }
        if (amount >= 4){
            Token robotToken = new RobotToken();
            robotToken.putOntoBattlefield(1, game, source);
            return true;
        }
        return false;
    }
}
