package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
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
import mage.players.Player;
import mage.watchers.common.ManaPaidSourceWatcher;

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
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new MrHousePresidentAndCEODieRollEffect(), new ManaCostsImpl<>("{4}"));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
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
        super(Zone.BATTLEFIELD, new MrHousePresidentAndCEOTokenEffect(), false);
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

class MrHousePresidentAndCEOTokenEffect extends OneShotEffect {

    public MrHousePresidentAndCEOTokenEffect() {
        super(Outcome.Benefit);
    }

    private MrHousePresidentAndCEOTokenEffect(final MrHousePresidentAndCEOTokenEffect effect) {
        super(effect);
    }

    @Override
    public MrHousePresidentAndCEOTokenEffect copy() {
        return new MrHousePresidentAndCEOTokenEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        if (getValue("rolled") == null) {
            return false;
        }
        int amount = (Integer) getValue("rolled");

        if (amount >= 4) {
            Token robotToken = new RobotToken();
            robotToken.putOntoBattlefield(1, game, source);
        }
        if (amount >= 6) {
            Token treasureToken = new TreasureToken();
            treasureToken.putOntoBattlefield(1, game, source);
        }
        return amount >= 4;
    }
}

// Based on Berg Strider and Ancient Brass Dragon
class MrHousePresidentAndCEODieRollEffect extends OneShotEffect {

    public MrHousePresidentAndCEODieRollEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "Roll a six-sided die plus an additional six-sided die for each mana from Treasures spent " +
                "to activate this ability.";
    }

    private MrHousePresidentAndCEODieRollEffect(final MrHousePresidentAndCEODieRollEffect effect) {
        super(effect);
    }

    @Override
    public MrHousePresidentAndCEODieRollEffect copy() {
        return new MrHousePresidentAndCEODieRollEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            controller.rollDice(outcome, source, game, 6,
                    1 + ManaPaidSourceWatcher.getTreasurePaid(source.getSourceId(), game), 0);
            return true;
        }
        return false;
    }
}
