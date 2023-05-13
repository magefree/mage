
package mage.cards.l;

import java.util.UUID;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.keyword.TransformAbility;
import mage.constants.SuperType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.token.IxalanVampireToken;

/**
 * @author TheElk801
 */
public final class LegionsLanding extends CardImpl {

    public LegionsLanding(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{W}");

        this.supertype.add(SuperType.LEGENDARY);

        this.secondSideCardClazz = mage.cards.a.AdantoTheFirstFort.class;

        // When Legion's Landing enters the battlefield, create a 1/1 white Vampire creature token with lifelink.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new IxalanVampireToken())));

        // When you attack with three or more creatures, transform Legion's Landing.
        this.addAbility(new TransformAbility());
        this.addAbility(new LegionsLandingTriggeredAbility(new TransformSourceEffect()));
    }

    private LegionsLanding(final LegionsLanding card) {
        super(card);
    }

    @Override
    public LegionsLanding copy() {
        return new LegionsLanding(this);
    }
}

class LegionsLandingTriggeredAbility extends TriggeredAbilityImpl {

    public LegionsLandingTriggeredAbility(Effect effect) {
        super(Zone.BATTLEFIELD, effect, false);
        setTriggerPhrase("When you attack with three or more creatures, " );
    }

    public LegionsLandingTriggeredAbility(final LegionsLandingTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public LegionsLandingTriggeredAbility copy() {
        return new LegionsLandingTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DECLARED_ATTACKERS;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return game.getCombat().getAttackers().size() >= 3 && game.getCombat().getAttackingPlayerId().equals(getControllerId());
    }
}