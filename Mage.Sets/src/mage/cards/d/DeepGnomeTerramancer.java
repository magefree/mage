package mage.cards.d;

import java.util.UUID;

// Abilities
import mage.abilities.effects.common.search.SearchLibraryPutInPlayEffect;
import mage.abilities.keyword.FlashAbility;
import mage.abilities.TriggeredAbilityImpl;

// Cards
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;

// Constants
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;

// Filter
import mage.filter.FilterCard;

// Game
import mage.game.events.GameEvent;
import mage.game.Game;
import mage.game.permanent.Permanent;

// Base
import mage.MageInt;

// Target
import mage.target.common.TargetCardInLibrary;

// Watchers
import mage.watchers.common.PlayLandWatcher;

/**
 * @author rgudmundsson
 */
public final class DeepGnomeTerramancer extends CardImpl {

    public DeepGnomeTerramancer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[] { CardType.CREATURE }, "{1}{W}");

        this.subtype.add(SubType.GNOME);
        this.subtype.add(SubType.WIZARD);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // Whenever one or more lands enter the battlefield under an opponent's control
        // without being played, you may search your library for a Plains card, put it
        // onto the battlefield tapped, then shuffle. Do this only once each turn.
        this.addAbility(new DeepGnomeTerramancerTriggeredAbility().setDoOnlyOnce(true), new PlayLandWatcher());
    }

    private DeepGnomeTerramancer(final DeepGnomeTerramancer card) {
        super(card);
    }

    @Override
    public DeepGnomeTerramancer copy() {
        return new DeepGnomeTerramancer(this);
    }
}

class DeepGnomeTerramancerTriggeredAbility extends TriggeredAbilityImpl {

    DeepGnomeTerramancerTriggeredAbility() {
        super(Zone.BATTLEFIELD, null);

        FilterCard filter = new FilterCard("Plains card");
        filter.add(SubType.PLAINS.getPredicate());
        TargetCardInLibrary target = new TargetCardInLibrary(filter);
        addEffect(new SearchLibraryPutInPlayEffect(target, true, true, Outcome.PutLandInPlay));
    }

    DeepGnomeTerramancerTriggeredAbility(DeepGnomeTerramancerTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent land = game.getPermanent(event.getTargetId());
        PlayLandWatcher watcher = game.getState().getWatcher(PlayLandWatcher.class);

        if (land == null || !land.isLand(game)) { // Permanent is not a land
            return false;
        }

        if (land.isControlledBy(this.controllerId)) { // Land enters under ability controllers control
            return false;
        }

        if (watcher.wasLandPlayed(land.getId())) { // Land was played
            return false;
        }

        return true;
    }

    @Override
    public DeepGnomeTerramancerTriggeredAbility copy() {
        return new DeepGnomeTerramancerTriggeredAbility(this);
    }

    @Override
    public String getRule() {
        return "Whenever one or more lands enter the battlefield under an opponent's control without being played, you may search your library for a Plains card, put it onto the battlefield tapped, then shuffle. Do this only once each turn.";
    }
}
