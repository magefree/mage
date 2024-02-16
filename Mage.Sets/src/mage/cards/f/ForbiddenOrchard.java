package mage.cards.f;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.CreateTokenTargetEffect;
import mage.abilities.mana.AnyColorManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.TappedForManaEvent;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.SpiritToken;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class ForbiddenOrchard extends CardImpl {

    public ForbiddenOrchard(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, null);

        // {T}: Add one mana of any color.
        this.addAbility(new AnyColorManaAbility());

        // Whenever you tap Forbidden Orchard for mana, create a 1/1 colorless Spirit creature token under target opponent's control.
        this.addAbility(new ForbiddenOrchardTriggeredAbility());
    }

    private ForbiddenOrchard(final ForbiddenOrchard card) {
        super(card);
    }

    @Override
    public ForbiddenOrchard copy() {
        return new ForbiddenOrchard(this);
    }
}

class ForbiddenOrchardTriggeredAbility extends TriggeredAbilityImpl {

    ForbiddenOrchardTriggeredAbility() {
        super(Zone.BATTLEFIELD, new CreateTokenTargetEffect(new SpiritToken()));
        this.addTarget(new TargetOpponent());
        setTriggerPhrase("Whenever you tap {this} for mana, ");
    }

    private ForbiddenOrchardTriggeredAbility(final ForbiddenOrchardTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.TAPPED_FOR_MANA;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        // it's non mana triggered ability, so ignore it on checking, see TAPPED_FOR_MANA
        if (game.inCheckPlayableState()) {
            return false;
        }
        Permanent permanent = ((TappedForManaEvent) event).getPermanent();
        return permanent != null
                && permanent == getSourcePermanentOrLKI(game)
                && isControlledBy(event.getPlayerId());
    }

    @Override
    public ForbiddenOrchardTriggeredAbility copy() {
        return new ForbiddenOrchardTriggeredAbility(this);
    }
}
