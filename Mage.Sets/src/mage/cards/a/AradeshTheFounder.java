package mage.cards.a;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import mage.MageInt;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.AttacksCreatureYouControlTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.abilities.keyword.EnlistAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.players.Player;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;
import mage.watchers.Watcher;

/**
 *
 * @author muz
 */
public final class AradeshTheFounder extends CardImpl {

    public AradeshTheFounder(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(1);
        this.toughness = new MageInt(4);

        // Enlist
        this.addAbility(new EnlistAbility());

        // Whenever a creature you control attacks, if it enlisted a creature this combat, the creature that attacked gains double strike until end of turn. If that creature's power is 4 or greater, draw a card.
        this.addAbility(new AttacksCreatureYouControlTriggeredAbility(new AradeshTheFounderEffect(), false, true)
            .withInterveningIf(AradeshEnlistedCondition.instance), new AradeshEnlistWatcher());
    }

    private AradeshTheFounder(final AradeshTheFounder card) {
        super(card);
    }

    @Override
    public AradeshTheFounder copy() {
        return new AradeshTheFounder(this);
    }
}

enum AradeshEnlistedCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        UUID attackerId = source.getEffects().get(0).getTargetPointer().getFirst(game, source);
        if (attackerId == null) {
            return false;
        }
        AradeshEnlistWatcher watcher = game.getState().getWatcher(AradeshEnlistWatcher.class);
        return watcher != null && watcher.didEnlist(new MageObjectReference(attackerId, game));
    }

    @Override
    public String toString() {
        return "it enlisted a creature this combat";
    }
}

class AradeshEnlistWatcher extends Watcher {

    private final Set<MageObjectReference> enlistedAttackers = new HashSet<>();

    AradeshEnlistWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.CREATURE_ENLISTED) {
            enlistedAttackers.add(new MageObjectReference(event.getSourceId(), game));
        }
    }

    @Override
    public void reset() {
        super.reset();
        enlistedAttackers.clear();
    }

    boolean didEnlist(MageObjectReference mor) {
        return enlistedAttackers.contains(mor);
    }
}

class AradeshTheFounderEffect extends OneShotEffect {

    AradeshTheFounderEffect() {
        super(Outcome.Benefit);
        staticText = "the creature that attacked gains double strike until end of turn. " +
                "If that creature's power is 4 or greater, draw a card";
    }

    private AradeshTheFounderEffect(final AradeshTheFounderEffect effect) {
        super(effect);
    }

    @Override
    public AradeshTheFounderEffect copy() {
        return new AradeshTheFounderEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (permanent == null) {
            return false;
        }
        GainAbilityTargetEffect effect = new GainAbilityTargetEffect(DoubleStrikeAbility.getInstance());
        effect.setTargetPointer(new FixedTarget(permanent, game));
        game.addEffect(effect, source);
        if (permanent.getPower().getValue() >= 4) {
            Player controller = game.getPlayer(source.getControllerId());
            if (controller != null) {
                controller.drawCards(1, source, game);
            }
        }
        return true;
    }
}
