
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.DontUntapInControllersNextUntapStepSourceEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;

/**
 *
 * @author fireshoes
 */
public final class SpectralForce extends CardImpl {

    public SpectralForce(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{G}{G}");
        this.subtype.add(SubType.ELEMENTAL);
        this.subtype.add(SubType.SPIRIT);
        this.power = new MageInt(8);
        this.toughness = new MageInt(8);

        // Trample
        this.addAbility(TrampleAbility.getInstance());
        
        // Whenever Spectral Force attacks, if defending player controls no black permanents, it doesn't untap during your next untap step.
        this.addAbility(new SpectralForceTriggeredAbility());
    }

    private SpectralForce(final SpectralForce card) {
        super(card);
    }

    @Override
    public SpectralForce copy() {
        return new SpectralForce(this);
    }
}

class SpectralForceTriggeredAbility extends TriggeredAbilityImpl {

    private static final FilterPermanent filter = new FilterPermanent("black permanents");

    static {
        filter.add(new ColorPredicate(ObjectColor.BLACK));
    }

    public SpectralForceTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DontUntapInControllersNextUntapStepSourceEffect());
    }

    private SpectralForceTriggeredAbility(final SpectralForceTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public SpectralForceTriggeredAbility copy() {
        return new SpectralForceTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ATTACKER_DECLARED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return event.getSourceId().equals(this.getSourceId());
    }

    @Override
    public boolean checkInterveningIfClause(Game game) {
        UUID defendingPlayerId = game.getCombat().getDefendingPlayerId(getSourceId(), game);
        return defendingPlayerId != null && game.getBattlefield().countAll(filter, defendingPlayerId, game) < 1;
    }

    @Override
    public String getRule() {
        return "Whenever {this} attacks, if defending player controls no black permanents, it doesn't untap during your next untap step.";
    }
}