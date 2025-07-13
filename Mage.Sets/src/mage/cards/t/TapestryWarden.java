package mage.cards.t;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.ruleModifying.CombatDamageByToughnessControlledEffect;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.ToughnessGreaterThanPowerPredicate;
import mage.game.Controllable;
import mage.game.Game;
import mage.game.events.GameEvent;

import java.util.Optional;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TapestryWarden extends CardImpl {

    private static final FilterCreaturePermanent filter
            = new FilterCreaturePermanent("creature you control with toughness greater than its power");

    static {
        filter.add(ToughnessGreaterThanPowerPredicate.instance);
    }

    public TapestryWarden(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{3}{G}");

        this.subtype.add(SubType.ROBOT);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Each creature you control with toughness greater than its power assigns combat damage equal to its toughness rather than its power.
        this.addAbility(new SimpleStaticAbility(new CombatDamageByToughnessControlledEffect(filter)));

        // Each creature you control with toughness greater than its power stations permanents using its toughness rather than its power.
        this.addAbility(new SimpleStaticAbility(new TapestryWardenEffect()));
    }

    private TapestryWarden(final TapestryWarden card) {
        super(card);
    }

    @Override
    public TapestryWarden copy() {
        return new TapestryWarden(this);
    }
}

class TapestryWardenEffect extends ReplacementEffectImpl {

    TapestryWardenEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "each creature you control with toughness greater than its power " +
                "stations permanents using its toughness rather than its power";
    }

    private TapestryWardenEffect(final TapestryWardenEffect effect) {
        super(effect);
    }

    @Override
    public TapestryWardenEffect copy() {
        return new TapestryWardenEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.STATION_PERMANENT;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Optional.ofNullable(event)
                .map(GameEvent::getTargetId)
                .map(game::getPermanent)
                .map(MageObject::getToughness)
                .map(MageInt::getValue)
                .ifPresent(event::setAmount);
        return false;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return Optional
                .ofNullable(event)
                .map(GameEvent::getTargetId)
                .map(game::getPermanent)
                .filter(permanent -> permanent.getPower().getValue() < permanent.getToughness().getValue())
                .map(Controllable::getControllerId)
                .filter(source::isControlledBy)
                .isPresent();
    }
}
