
package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.WolfToken;

/**
 *
 * @author LevelX2
 */
public final class CultOfTheWaxingMoon extends CardImpl {

    public CultOfTheWaxingMoon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{G}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SHAMAN);
        this.power = new MageInt(5);
        this.toughness = new MageInt(4);

        // Whenever a permanent you control transforms into a non-Human creature, create a 2/2 green Wolf creature token.
        this.addAbility(new CultOfTheWaxingMoonAbility());
    }

    private CultOfTheWaxingMoon(final CultOfTheWaxingMoon card) {
        super(card);
    }

    @Override
    public CultOfTheWaxingMoon copy() {
        return new CultOfTheWaxingMoon(this);
    }
}

class CultOfTheWaxingMoonAbility extends TriggeredAbilityImpl {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent();

    static {
        filter.add(Predicates.not(SubType.HUMAN.getPredicate()));
    }

    public CultOfTheWaxingMoonAbility() {
        super(Zone.BATTLEFIELD, new CreateTokenEffect(new WolfToken()), false);
    }

    public CultOfTheWaxingMoonAbility(final CultOfTheWaxingMoonAbility ability) {
        super(ability);
    }

    @Override
    public CultOfTheWaxingMoonAbility copy() {
        return new CultOfTheWaxingMoonAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.TRANSFORMED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent permanent = game.getPermanent(event.getTargetId());
        return filter.match(permanent, getControllerId(), this, game);
    }

    @Override
    public String getTriggerPhrase() {
        return "Whenever a permanent you control transforms into a non-Human creature, " ;
    }
}
