package mage.cards.f;

import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.UseAttachedCost;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.FishNoAbilityToken;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 *
 * @author ciaccona007
 */
public final class FishingPole extends CardImpl {

    public FishingPole(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}");
        
        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature has "{1}, {T}, Tap Fishing Pole: Put a bait counter on Fishing Pole."
        this.addAbility(new SimpleStaticAbility(new FishingPoleEffect()));

        // Whenever equipped creature becomes untapped, remove a bait counter from this Equipment. If you do, create a 1/1 blue Fish creature token.
        this.addAbility(new FishingPoleTriggeredAbility(Zone.BATTLEFIELD, new DoIfCostPaid(
                new CreateTokenEffect(new FishNoAbilityToken()),
                new RemoveCountersSourceCost(CounterType.BAIT.createInstance()).setText("remove a bait counter from this Equipment"),
                null, false
        ), false));

        // Equip {2}
        this.addAbility(new EquipAbility(Outcome.AddAbility, new GenericManaCost(2), false));
    }

    private FishingPole(final FishingPole card) {
        super(card);
    }

    @Override
    public FishingPole copy() {
        return new FishingPole(this);
    }
}

class FishingPoleTriggeredAbility extends TriggeredAbilityImpl {

    public FishingPoleTriggeredAbility(Zone zone, Effect effect, boolean optional) {
        super(zone, effect, optional);
        setTriggerPhrase("Whenever equipped creature becomes untapped, ");
    }

    protected FishingPoleTriggeredAbility(final FishingPoleTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public FishingPoleTriggeredAbility copy() {
        return new FishingPoleTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.UNTAPPED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent equipment = game.getPermanent(this.getSourceId());
        if (equipment == null || equipment.getAttachedTo() == null) {
            return false;
        }
        Permanent equipped = game.getPermanent(equipment.getAttachedTo());
        return equipped != null && event.getTargetId().equals(equipped.getId());
    }
}

class FishingPoleEffect extends ContinuousEffectImpl {

    FishingPoleEffect() {
        super(Duration.WhileOnBattlefield, Layer.AbilityAddingRemovingEffects_6, SubLayer.NA, Outcome.AddAbility);
        staticText = "equipped creature has \"{1}, {T}, Tap {this}: Put a bait counter on {this}.\"";
    }

    private FishingPoleEffect(final FishingPoleEffect effect) {
        super(effect);
    }

    @Override
    public FishingPoleEffect copy() {
        return new FishingPoleEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent fishingPole = source.getSourcePermanentIfItStillExists(game);
        if (fishingPole == null) {
            return false;
        }
        Permanent creature = game.getPermanent(fishingPole.getAttachedTo());
        if (creature == null) {
            return false;
        }
        Ability ability = new SimpleActivatedAbility(
                new AddCountersTargetEffect(CounterType.BAIT.createInstance())
                        .setTargetPointer(new FixedTarget(fishingPole, game))
                        .setText("put a bait counter on " + fishingPole.getName()),
                new GenericManaCost(1)
        );
        ability.addCost(new TapSourceCost());
        ability.addCost(new TapAttachmentCost().setMageObjectReference(source, game));
        creature.addAbility(ability, source.getSourceId(), game);
        return true;
    }
}

class TapAttachmentCost extends UseAttachedCost {

    public TapAttachmentCost() {
        super();
    }

    protected TapAttachmentCost(final TapAttachmentCost cost) {
        super(cost);
    }

    @Override
    public boolean pay(Ability ability, Game game, Ability source, UUID controllerId, boolean noMana, Cost costToPay) {
        if (mageObjectReference == null) {
            return false;
        }
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (permanent == null) {
            return paid;
        }
        for (UUID attachmentId : permanent.getAttachments()) {
            if (!this.mageObjectReference.refersTo(attachmentId, game)) {
                continue;
            }
            Permanent attachment = game.getPermanent(attachmentId);
            paid = attachment != null
                    && attachment.isControlledBy(controllerId)
                    && attachment.tap(source, game);
            if (paid) {
                break;
            }
        }
        return paid;
    }

    @Override
    public TapAttachmentCost copy() {
        return new TapAttachmentCost(this);
    }

    @Override
    public String getText() {
        return "tap " + this.name;
    }
}
