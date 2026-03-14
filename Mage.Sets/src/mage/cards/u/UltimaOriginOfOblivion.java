package mage.cards.u;

import mage.MageInt;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.effects.mana.BasicManaEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.mana.ColorlessManaAbility;
import mage.abilities.mana.TriggeredManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.TappedForManaEvent;
import mage.game.permanent.Permanent;
import mage.target.common.TargetLandPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class UltimaOriginOfOblivion extends CardImpl {

    public UltimaOriginOfOblivion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.GOD);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever Ultima attacks, put a blight counter on target land. For as long as that land has a blight counter on it, it loses all land types and abilities and has "{T}: Add {C}."
        Ability ability = new AttacksTriggeredAbility(new AddCountersTargetEffect(CounterType.BLIGHT.createInstance()));
        ability.addEffect(new UltimaOriginOfOblivionEffect());
        ability.addTarget(new TargetLandPermanent());
        this.addAbility(ability);

        // Whenever you tap a land for {C}, add an additional {C}.
        this.addAbility(new UltimaOriginOfOblivionTriggeredManaAbility());
    }

    private UltimaOriginOfOblivion(final UltimaOriginOfOblivion card) {
        super(card);
    }

    @Override
    public UltimaOriginOfOblivion copy() {
        return new UltimaOriginOfOblivion(this);
    }
}

class UltimaOriginOfOblivionEffect extends ContinuousEffectImpl {

    UltimaOriginOfOblivionEffect() {
        super(Duration.Custom, Outcome.Benefit);
        staticText = "For as long as that land has a blight counter on it, " +
                "it loses all land types and abilities and has \"{T}: Add {C}.\"";
    }

    private UltimaOriginOfOblivionEffect(final UltimaOriginOfOblivionEffect effect) {
        super(effect);
    }

    @Override
    public UltimaOriginOfOblivionEffect copy() {
        return new UltimaOriginOfOblivionEffect(this);
    }

    @Override
    public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (permanent == null || !permanent.getCounters(game).containsKey(CounterType.BLIGHT)) {
            discard();
            return false;
        }
        switch (layer) {
            case TypeChangingEffects_4:
                permanent.removeAllAbilities(source.getSourceId(), game);
                permanent.addAbility(new ColorlessManaAbility(), source.getSourceId(), game);
                return true;
            case AbilityAddingRemovingEffects_6:
                permanent.removeAllSubTypes(game, SubTypeSet.NonBasicLandType);
                permanent.removeAllSubTypes(game, SubTypeSet.BasicLandType);
                return true;
            default:
                return false;
        }
    }

    @Override
    public boolean hasLayer(Layer layer) {
        switch (layer) {
            case AbilityAddingRemovingEffects_6:
            case TypeChangingEffects_4:
                return true;
            default:
                return false;
        }
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }
}

class UltimaOriginOfOblivionTriggeredManaAbility extends TriggeredManaAbility {

    UltimaOriginOfOblivionTriggeredManaAbility() {
        super(Zone.BATTLEFIELD, new BasicManaEffect(Mana.ColorlessMana(1)));
    }

    private UltimaOriginOfOblivionTriggeredManaAbility(final UltimaOriginOfOblivionTriggeredManaAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.TAPPED_FOR_MANA;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        TappedForManaEvent mEvent = (TappedForManaEvent) event;
        Permanent permanent = mEvent.getPermanent();
        return permanent != null
                && permanent.isLand(game)
                && isControlledBy(event.getPlayerId())
                && mEvent.getMana().getColorless() > 0;
    }

    @Override
    public UltimaOriginOfOblivionTriggeredManaAbility copy() {
        return new UltimaOriginOfOblivionTriggeredManaAbility(this);
    }

    @Override
    public String getRule() {
        return "Whenever you tap a land for {C}, add an additional {C}.";
    }
}
