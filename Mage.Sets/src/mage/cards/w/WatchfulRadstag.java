package mage.cards.w;

import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.CreateTokenCopySourceEffect;
import mage.abilities.keyword.EvolveAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class WatchfulRadstag extends CardImpl {

    public WatchfulRadstag(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");

        this.subtype.add(SubType.ELK);
        this.subtype.add(SubType.MUTANT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Evolve
        this.addAbility(new EvolveAbility());

        // Whenever Watchful Radstag evolves, create a token that's a copy of it.
        this.addAbility(new WatchfulRadstagTriggeredAbility());
    }

    private WatchfulRadstag(final WatchfulRadstag card) {
        super(card);
    }

    @Override
    public WatchfulRadstag copy() {
        return new WatchfulRadstag(this);
    }
}

class WatchfulRadstagTriggeredAbility extends TriggeredAbilityImpl {

    WatchfulRadstagTriggeredAbility() {
        super(Zone.BATTLEFIELD, new CreateTokenCopySourceEffect().setText("create a token that's a copy of it"), false);
        setTriggerPhrase("Whenever {this} evolves, ");
    }

    private WatchfulRadstagTriggeredAbility(final WatchfulRadstagTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public WatchfulRadstagTriggeredAbility copy() {
        return new WatchfulRadstagTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.EVOLVED_CREATURE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return event.getTargetId().equals(getSourceId());
    }
}