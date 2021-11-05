package mage.cards.t;

import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.mana.BlackManaAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TheUpsideDown extends CardImpl {

    public TheUpsideDown(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");
        this.addSuperType(SuperType.LEGENDARY);

        this.nightCard = true;

        // When this land transforms into The Upside Down, return target creature card from your graveyard to the battlefield.
        this.addAbility(new TheUpsideDownTransformAbility());

        // When the creature put onto the battlefield with The Upside Down leaves the battlefield, transform The Upside Down.
        this.addAbility(new TheUpsideDownLeavesAbility());

        // {T}, Pay 1 life: Add {B}.
        Ability ability = new BlackManaAbility();
        ability.addCost(new PayLifeCost(1));
        this.addAbility(ability);
    }

    private TheUpsideDown(final TheUpsideDown card) {
        super(card);
    }

    @Override
    public TheUpsideDown copy() {
        return new TheUpsideDown(this);
    }

    static String makeKey(Ability source, Game game) {
        return "TheUpsideDown_" + source.getSourceId() + '_' + source.getSourceObjectZoneChangeCounter();
    }
}

class TheUpsideDownTransformAbility extends TriggeredAbilityImpl {

    public TheUpsideDownTransformAbility() {
        super(Zone.BATTLEFIELD, new TheUpsideDownEffect(), false);
        this.addTarget(new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_CREATURE_YOUR_GRAVEYARD));
    }

    public TheUpsideDownTransformAbility(final TheUpsideDownTransformAbility ability) {
        super(ability);
    }

    @Override
    public TheUpsideDownTransformAbility copy() {
        return new TheUpsideDownTransformAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.TRANSFORMED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (!event.getTargetId().equals(this.getSourceId())) {
            return false;
        }
        Permanent permanent = getSourcePermanentIfItStillExists(game);
        return permanent != null && !permanent.isTransformed();
    }

    @Override
    public String getRule() {
        return "When this land transforms into {this}, " +
                "return target creature card from your graveyard to the battlefield.";
    }
}

class TheUpsideDownEffect extends OneShotEffect {

    TheUpsideDownEffect() {
        super(Outcome.Benefit);
    }

    private TheUpsideDownEffect(final TheUpsideDownEffect effect) {
        super(effect);
    }

    @Override
    public TheUpsideDownEffect copy() {
        return new TheUpsideDownEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Card card = game.getCard(getTargetPointer().getFirst(game, source));
        if (player == null || card == null) {
            return false;
        }
        player.moveCards(card, Zone.BATTLEFIELD, source, game);
        Permanent permanent = game.getPermanent(card.getId());
        if (permanent == null) {
            return false;
        }
        String key = TheUpsideDown.makeKey(source, game);
        Set<MageObjectReference> morSet;
        if (game.getState().getValue(key) != null) {
            morSet = (Set<MageObjectReference>) game.getState().getValue(key);
        } else {
            morSet = new HashSet<>();
            game.getState().setValue(key, morSet);
        }
        morSet.add(new MageObjectReference(permanent, game));
        return true;
    }
}

class TheUpsideDownLeavesAbility extends TriggeredAbilityImpl {

    TheUpsideDownLeavesAbility() {
        super(Zone.BATTLEFIELD, new TransformSourceEffect());
    }

    private TheUpsideDownLeavesAbility(final TheUpsideDownLeavesAbility ability) {
        super(ability);
    }

    @Override
    public TheUpsideDownLeavesAbility copy() {
        return new TheUpsideDownLeavesAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
        Set<MageObjectReference> morSet = (Set<MageObjectReference>) game.getState().getValue(TheUpsideDown.makeKey(this, game));
        return morSet != null
                && !morSet.isEmpty()
                && morSet.stream().anyMatch(mor -> mor.refersTo(zEvent.getTarget(), game));
    }

    @Override
    public String getRule() {
        return "When the creature put onto the battlefield with {this} leaves the battlefield, transform {this}.";
    }
}
