package mage.cards.e;

import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.DealsDamageToAPlayerAttachedTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.IntimidateAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.*;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;

import java.util.Optional;
import java.util.UUID;

/**
 * @author BetaSteward
 */
public final class ElbrusTheBindingBlade extends TransformingDoubleFacedCard {

    public ElbrusTheBindingBlade(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new SuperType[]{SuperType.LEGENDARY}, new CardType[]{CardType.ARTIFACT}, new SubType[]{SubType.EQUIPMENT}, "{7}",
                "Withengar Unbound",
                new SuperType[]{SuperType.LEGENDARY}, new CardType[]{CardType.CREATURE}, new SubType[]{SubType.DEMON}, "B"
        );

        // Elbrus, the Binding Blade
        // Equipped creature gets +1/+0.
        this.getLeftHalfCard().addAbility(new SimpleStaticAbility(new BoostEquippedEffect(1, 0)));

        // When equipped creature deals combat damage to a player, unattach Elbrus, the Binding Blade, then transform it.
        Ability ability = new DealsDamageToAPlayerAttachedTriggeredAbility(
                new ElbrusTheBindingBladeEffect(), "equipped", false
        );
        ability.addEffect(new TransformSourceEffect(true).concatBy(", then"));
        this.getLeftHalfCard().addAbility(ability);

        // Equip {1}
        this.getLeftHalfCard().addAbility(new EquipAbility(1, false));

        // Withengar Unbound
        this.getRightHalfCard().setPT(13, 13);

        // Flying
        this.getRightHalfCard().addAbility(FlyingAbility.getInstance());

        // Intimidate
        this.getRightHalfCard().addAbility(IntimidateAbility.getInstance());

        // Trample
        this.getRightHalfCard().addAbility(TrampleAbility.getInstance());

        // Whenever a player loses the game, put thirteen +1/+1 counters on Withengar Unbound.
        this.getRightHalfCard().addAbility(new WithengarUnboundTriggeredAbility());
    }

    private ElbrusTheBindingBlade(final ElbrusTheBindingBlade card) {
        super(card);
    }

    @Override
    public ElbrusTheBindingBlade copy() {
        return new ElbrusTheBindingBlade(this);
    }
}

class ElbrusTheBindingBladeEffect extends OneShotEffect {
    ElbrusTheBindingBladeEffect() {
        super(Outcome.BecomeCreature);
        staticText = "unattach {this}";
    }

    private ElbrusTheBindingBladeEffect(final ElbrusTheBindingBladeEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Optional.ofNullable(source.getSourcePermanentIfItStillExists(game)).ifPresent(permanent -> permanent.unattach(game));
        return true;
    }

    @Override
    public ElbrusTheBindingBladeEffect copy() {
        return new ElbrusTheBindingBladeEffect(this);
    }
}

class WithengarUnboundTriggeredAbility extends TriggeredAbilityImpl {

    WithengarUnboundTriggeredAbility() {
        super(Zone.BATTLEFIELD, new mage.abilities.effects.common.counter.AddCountersSourceEffect(CounterType.P1P1.createInstance(13)), false);
        setTriggerPhrase("Whenever a player loses the game, ");
    }

    private WithengarUnboundTriggeredAbility(final WithengarUnboundTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public WithengarUnboundTriggeredAbility copy() {
        return new WithengarUnboundTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.LOST;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return true;
    }
}
