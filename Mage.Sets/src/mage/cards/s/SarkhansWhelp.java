package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.constants.SubType;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPlaneswalkerPermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.common.TargetAnyTarget;

/**
 *
 * @author TheElk801
 */
public final class SarkhansWhelp extends CardImpl {

    public SarkhansWhelp(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");

        this.subtype.add(SubType.DRAGON);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever you activate an ability of a Sarkhan planeswalker, Sarkhan's Whelp deals 1 damage to any target.
        this.addAbility(new SarkhansWhelpTriggeredAbility());
    }

    private SarkhansWhelp(final SarkhansWhelp card) {
        super(card);
    }

    @Override
    public SarkhansWhelp copy() {
        return new SarkhansWhelp(this);
    }
}

class SarkhansWhelpTriggeredAbility extends TriggeredAbilityImpl {

    private static final FilterControlledPlaneswalkerPermanent filter
            = new FilterControlledPlaneswalkerPermanent(
                    SubType.SARKHAN,
                    "a Sarkhan planeswalker"
            );

    public SarkhansWhelpTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DamageTargetEffect(1), false);
        this.addTarget(new TargetAnyTarget());
        setTriggerPhrase("Whenever you activate an ability of a Sarkhan planeswalker, ");
    }

    public SarkhansWhelpTriggeredAbility(final SarkhansWhelpTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public SarkhansWhelpTriggeredAbility copy() {
        return new SarkhansWhelpTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ACTIVATED_ABILITY;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent source = game.getPermanentOrLKIBattlefield(event.getSourceId());
        return event.getPlayerId().equals(getControllerId())
                && source != null
                && filter.match(source, game);
    }
}
