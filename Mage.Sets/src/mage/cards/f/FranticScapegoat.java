package mage.cards.f;

import mage.MageInt;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.SuspectSourceEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.PermanentReferenceInCollectionPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeBatchEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.TargetPermanent;

import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author notgreat
 */
public final class FranticScapegoat extends CardImpl {

    public FranticScapegoat(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{R}");

        this.subtype.add(SubType.GOAT);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // When Frantic Scapegoat enters the battlefield, suspect it.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new SuspectSourceEffect()));

        // Whenever one or more other creatures enter the battlefield under your control, if Frantic Scapegoat is suspected, you may suspect one of the other creatures. If you do, Frantic Scapegoat is no longer suspected.
        this.addAbility(new FranticScapegoatTriggeredAbility());

    }

    private FranticScapegoat(final FranticScapegoat card) {
        super(card);
    }

    @Override
    public FranticScapegoat copy() {
        return new FranticScapegoat(this);
    }
}

//Based on Lightmine Field
class FranticScapegoatTriggeredAbility extends TriggeredAbilityImpl {

    FranticScapegoatTriggeredAbility() {
        super(Zone.BATTLEFIELD, new FranticScapegoatSuspectEffect(), true);
    }

    private FranticScapegoatTriggeredAbility(final FranticScapegoatTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE_BATCH;
    }

    @Override
    public boolean checkInterveningIfClause(Game game) {
        Permanent source = getSourcePermanentIfItStillExists(game);
        return (source != null && source.isSuspected());
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        ZoneChangeBatchEvent zEvent = (ZoneChangeBatchEvent) event;
        Set<MageObjectReference> enteringCreatures = zEvent.getEvents().stream()
                .filter(z -> z.getToZone() == Zone.BATTLEFIELD)
                .filter(z -> this.controllerId.equals(z.getPlayerId()))
                .map(ZoneChangeEvent::getTarget)
                .filter(Objects::nonNull)
                .filter(permanent -> permanent.isCreature(game))
                .map(p -> new MageObjectReference(p, game))
                .collect(Collectors.toSet());
        if (!enteringCreatures.isEmpty()) {
            this.getEffects().setValue("franticScapegoatEnteringCreatures", enteringCreatures);
            return true;
        }
        return false;
    }

    @Override
    public FranticScapegoatTriggeredAbility copy() {
        return new FranticScapegoatTriggeredAbility(this);
    }

    @Override
    public String getRule() {
        return "Whenever one or more other creatures enter the battlefield under your control, if {this} is suspected, you may suspect one of the other creatures. If you do, {this} is no longer suspected.";
    }
}

class FranticScapegoatSuspectEffect extends OneShotEffect {

    FranticScapegoatSuspectEffect() {
        super(Outcome.Benefit);
        this.staticText = "Suspect one of the other creatures";
    }

    private FranticScapegoatSuspectEffect(final FranticScapegoatSuspectEffect effect) {
        super(effect);
    }

    @Override
    public FranticScapegoatSuspectEffect copy() {
        return new FranticScapegoatSuspectEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Set<MageObjectReference> enteringSet = (Set<MageObjectReference>) getValue("franticScapegoatEnteringCreatures");
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null && enteringSet != null) {
            Permanent suspect = null;
            if (enteringSet.size() > 1) {
                FilterCreaturePermanent filter = new FilterCreaturePermanent("one of those creatures");
                filter.add(new PermanentReferenceInCollectionPredicate(enteringSet));
                Target target = new TargetPermanent(filter);
                target.withNotTarget(true);
                if (controller.choose(outcome, target, source, game)) {
                    suspect = game.getPermanent(target.getFirstTarget());
                }
            } else { //There is only 1 creature in the set
                for (MageObjectReference s : enteringSet) {
                    suspect = s.getPermanent(game);
                }
            }
            if (suspect != null) {
                suspect.setSuspected(true, game, source);
                Permanent scapegoat = source.getSourcePermanentIfItStillExists(game);
                if (scapegoat != null) {
                    scapegoat.setSuspected(false, game, source);
                }
            }
        }
        return true;
    }
}
