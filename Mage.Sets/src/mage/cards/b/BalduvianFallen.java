package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.keyword.CumulativeUpkeepAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ManaEvent;

/**
 *
 * @author noahg
 */
public final class BalduvianFallen extends CardImpl {

    public BalduvianFallen(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}");
        
        this.subtype.add(SubType.ZOMBIE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(5);

        // Cumulative upkeep {1}
        this.addAbility(new CumulativeUpkeepAbility(new ManaCostsImpl<>("{1}")));

        // Whenever Balduvian Fallen's cumulative upkeep is paid, it gets +1/+0 until end of turn for each {B} or {R} spent this way.
        this.addAbility(new BalduvianFallenAbility());
    }

    private BalduvianFallen(final BalduvianFallen card) {
        super(card);
    }

    @Override
    public BalduvianFallen copy() {
        return new BalduvianFallen(this);
    }
}

class BalduvianFallenAbility extends TriggeredAbilityImpl {

    public BalduvianFallenAbility() {
        super(Zone.BATTLEFIELD, null, false);
    }

    private BalduvianFallenAbility(final BalduvianFallenAbility ability) {
        super(ability);
    }

    @Override
    public BalduvianFallenAbility copy() {
        return new BalduvianFallenAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.PAID_CUMULATIVE_UPKEEP;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        this.getEffects().clear();
        if(event.getTargetId().equals(this.getSourceId()) && event instanceof ManaEvent) {
            ManaEvent manaEvent = (ManaEvent) event;
            int total = manaEvent.getMana().getBlack() + manaEvent.getMana().getRed();
            if (total > 0) {
                this.getEffects().add(new BoostSourceEffect(total, 0, Duration.EndOfTurn));
            }
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever {this}'s cumulative upkeep is paid, it gets +1/+0 until end of turn for each {B} or {R} spent this way";
    }
}