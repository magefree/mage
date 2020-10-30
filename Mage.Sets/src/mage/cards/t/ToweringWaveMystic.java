package mage.cards.t;

import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.MillCardsTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.target.TargetPlayer;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ToweringWaveMystic extends CardImpl {

    public ToweringWaveMystic(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}");

        this.subtype.add(SubType.MERFOLK);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Whenever Towering-Wave Mystic deals damage, target player puts that many cards from the top of their library into their graveyard.
        this.addAbility(new ToweringWaveMysticTriggeredAbility());
    }

    private ToweringWaveMystic(final ToweringWaveMystic card) {
        super(card);
    }

    @Override
    public ToweringWaveMystic copy() {
        return new ToweringWaveMystic(this);
    }
}

class ToweringWaveMysticTriggeredAbility extends TriggeredAbilityImpl {

    ToweringWaveMysticTriggeredAbility() {
        super(Zone.BATTLEFIELD, null, false);
        this.addTarget(new TargetPlayer());
    }

    private ToweringWaveMysticTriggeredAbility(final ToweringWaveMysticTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public ToweringWaveMysticTriggeredAbility copy() {
        return new ToweringWaveMysticTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_CREATURE
                || event.getType() == GameEvent.EventType.DAMAGED_PLAYER
                || event.getType() == GameEvent.EventType.DAMAGED_PLANESWALKER;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (!event.getSourceId().equals(this.getSourceId())) {
            return false;
        }
        this.getEffects().clear();
        this.addEffect(new MillCardsTargetEffect(event.getAmount()));
        return true;
    }

    @Override
    public String getRule() {
        return "Whenever {this} deals damage, target player mills that many cards.";
    }
}
