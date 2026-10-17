package mage.cards.d;

import mage.MageInt;
import mage.abilities.BatchTriggeredAbility;
import mage.abilities.effects.common.search.SearchLibraryPutInPlayEffect;
import mage.abilities.keyword.FlashAbility;
import mage.abilities.TriggeredAbilityImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeBatchEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCardInLibrary;
import mage.watchers.common.PlayLandWatcher;

import java.util.UUID;

/**
 * @author rgudmundsson
 */
public final class DeepGnomeTerramancer extends CardImpl {

    public DeepGnomeTerramancer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");
        this.subtype.add(SubType.GNOME, SubType.WIZARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // Whenever one or more lands enter the battlefield under an opponent's control
        // without being played, you may search your library for a Plains card, put it
        // onto the battlefield tapped, then shuffle. Do this only once each turn.
        this.addAbility(new DeepGnomeTerramancerTriggeredAbility().setDoOnlyOnceEachTurn(true).withFlavorWord("Mold Earth"), new PlayLandWatcher());
    }

    private DeepGnomeTerramancer(final DeepGnomeTerramancer card) {
        super(card);
    }

    @Override
    public DeepGnomeTerramancer copy() {
        return new DeepGnomeTerramancer(this);
    }
}

class DeepGnomeTerramancerTriggeredAbility extends TriggeredAbilityImpl implements BatchTriggeredAbility<ZoneChangeEvent> {

    DeepGnomeTerramancerTriggeredAbility() {
        super(Zone.BATTLEFIELD, null);

        FilterCard filter = new FilterCard("Plains card");
        filter.add(SubType.PLAINS.getPredicate());
        TargetCardInLibrary target = new TargetCardInLibrary(filter);
        addEffect(new SearchLibraryPutInPlayEffect(target, true));
    }

    private DeepGnomeTerramancerTriggeredAbility(final DeepGnomeTerramancerTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE_BATCH;
    }

    @Override
    public boolean checkEvent(ZoneChangeEvent event, Game game) {
        if (event.getToZone() != Zone.BATTLEFIELD) {
            return false;
        }

        Permanent land = event.getTarget();
        if (land == null || !land.isLand(game)) { // Permanent is not a land
            return false;
        }

        if (land.isControlledBy(this.controllerId)) { // Land enters under ability controllers control
            return false;
        }

        PlayLandWatcher watcher = game.getState().getWatcher(PlayLandWatcher.class);
        if (watcher.wasLandPlayed(land, game)) { // Land was played
            return false;
        }

        return true;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        // one or more lands - one trigger per batch, see https://github.com/magefree/mage/issues/10088
        return !getFilteredEvents((ZoneChangeBatchEvent) event, game).isEmpty();
    }

    @Override
    public DeepGnomeTerramancerTriggeredAbility copy() {
        return new DeepGnomeTerramancerTriggeredAbility(this);
    }

    @Override
    public String getRule() {
        return "Whenever one or more lands enter the battlefield under an opponent's control without being played, you may search " +
                "your library for a Plains card, put it onto the battlefield tapped, then shuffle. Do this only once each turn.";
    }
}
