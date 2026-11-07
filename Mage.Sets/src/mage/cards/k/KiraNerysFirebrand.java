package mage.cards.k;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.constants.*;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.EntersTheBattlefieldEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

/**
 *
 * @author anonymous
 */
public final class KiraNerysFirebrand extends CardImpl {

    public KiraNerysFirebrand(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}{W}");
        
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.BAJORAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // If a creature you control would enter while you control an attacking creature, instead it enters tapped and attacking.
        this.addAbility(new SimpleStaticAbility(new KiraNerysFirebrandEffect()));

        // Whenever another creature you control enters, it gets +1/+1 until end of turn.
        this.addAbility(new EntersBattlefieldControlledTriggeredAbility(Zone.BATTLEFIELD,
                new BoostTargetEffect(1, 1, Duration.EndOfTurn).setText("it gets +1/+1"),
                StaticFilters.FILTER_ANOTHER_CREATURE, false, SetTargetPointer.PERMANENT));

    }

    private KiraNerysFirebrand(final KiraNerysFirebrand card) {
        super(card);
    }

    @Override
    public KiraNerysFirebrand copy() {
        return new KiraNerysFirebrand(this);
    }
}

class KiraNerysFirebrandEffect extends ReplacementEffectImpl {

    public KiraNerysFirebrandEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit, false);
    }

    protected KiraNerysFirebrandEffect(final KiraNerysFirebrandEffect effect) {
        super(effect);
    }

    @Override
    public KiraNerysFirebrandEffect copy() {
        return new KiraNerysFirebrandEffect(this);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Permanent target = ((EntersTheBattlefieldEvent) event).getTarget();
        if (target != null) {
            // enters tapped - it does not become tapped (no tapped event and triggers)
            target.setTapped(true);
            target.setEntersAttacking(game.getCombat().getPossibleDefenders(target.getId(), null, game));
        }
        return false;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Permanent permanent = ((EntersTheBattlefieldEvent) event).getTarget();
        if (permanent == null
                || !permanent.isCreature(game)
                || !permanent.isControlledBy(source.getControllerId())) {
            return false;
        }
        // while you control an attacking creature
        return game.getCombat()
                .getAttackers()
                .stream()
                .map(game::getPermanent)
                .anyMatch(attacker -> attacker != null && attacker.isControlledBy(source.getControllerId()));
    }
}
