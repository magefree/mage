
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.DontUntapInControllersNextUntapStepSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;

/**
 *
 * @author fireshoes
 */
public final class SpectralBears extends CardImpl {

    public SpectralBears(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{G}");
        this.subtype.add(SubType.BEAR);
        this.subtype.add(SubType.SPIRIT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Whenever Spectral Bears attacks, if defending player controls no black nontoken permanents, it doesn't untap during your next untap step.
        this.addAbility(new SpectralBearsTriggeredAbility());
    }

    private SpectralBears(final SpectralBears card) {
        super(card);
    }

    @Override
    public SpectralBears copy() {
        return new SpectralBears(this);
    }
}

class SpectralBearsTriggeredAbility extends TriggeredAbilityImpl {

    private static final FilterPermanent filter = new FilterPermanent("black nontoken permanents");

    static {
        filter.add(new ColorPredicate(ObjectColor.BLACK));
        filter.add(TokenPredicate.FALSE);
    }

    public SpectralBearsTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DontUntapInControllersNextUntapStepSourceEffect());
    }

    private SpectralBearsTriggeredAbility(final SpectralBearsTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public SpectralBearsTriggeredAbility copy() {
        return new SpectralBearsTriggeredAbility(this);
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
        return "Whenever {this} attacks, if defending player controls no black nontoken permanents, it doesn't untap during your next untap step.";
    }
}
