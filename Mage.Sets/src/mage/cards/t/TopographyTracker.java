package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.Game;
import mage.game.events.ExploreEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.MapToken;

/**
 *
 * @author Grath
 */
public final class TopographyTracker extends CardImpl {

    public TopographyTracker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");
        
        this.subtype.add(SubType.MERFOLK);
        this.subtype.add(SubType.SCOUT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When Topography Tracker enters the battlefield, create a Map token.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new MapToken()), false));

        // If a creature you control would explore, instead it explores, then it explores again.
        this.addAbility(new SimpleStaticAbility(new TopographyTrackerEffect()));
    }

    private TopographyTracker(final TopographyTracker card) {
        super(card);
    }

    @Override
    public TopographyTracker copy() {
        return new TopographyTracker(this);
    }
}

class TopographyTrackerEffect extends ReplacementEffectImpl {

    TopographyTrackerEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "If a creature you control would explore, instead it explores, then it explores again.";
    }

    private TopographyTrackerEffect(final TopographyTrackerEffect effect) {
        super(effect);
    }

    @Override
    public TopographyTrackerEffect copy() {
        return new TopographyTrackerEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.EXPLORE;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Permanent permanent = game.getPermanent(event.getTargetId()); 
        return permanent != null && permanent.isControlledBy(event.getPlayerId());
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        ExploreEvent exploreEvent = (ExploreEvent)event;
        exploreEvent.doubleExplores();
        return false;
    }
}