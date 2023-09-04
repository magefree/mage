package mage.cards.s;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.util.UUID;

/**
 * @author spjspj
 */
public final class Solemnity extends CardImpl {

    public Solemnity(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{W}");

        // Players can't get counters.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new SolemnityEffect()));

        // Counters can't be put on artifacts, creatures, enchantments, or lands.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new SolemnityEffect2()));
    }

    private Solemnity(final Solemnity card) {
        super(card);
    }

    @Override
    public Solemnity copy() {
        return new Solemnity(this);
    }
}

class SolemnityEffect extends ReplacementEffectImpl {

    public SolemnityEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "Players can't get counters";
    }

    private SolemnityEffect(final SolemnityEffect effect) {
        super(effect);
    }

    @Override
    public SolemnityEffect copy() {
        return new SolemnityEffect(this);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        return true;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ADD_COUNTERS;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Player player = game.getPlayer(event.getTargetId());
        return player != null;
    }
}

class SolemnityEffect2 extends ReplacementEffectImpl {

    private static final FilterPermanent filter = new FilterPermanent();

    static {
        filter.add(Predicates.or(
                CardType.ARTIFACT.getPredicate(),
                CardType.CREATURE.getPredicate(),
                CardType.ENCHANTMENT.getPredicate(),
                CardType.LAND.getPredicate()));
    }

    public SolemnityEffect2() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "Counters can't be put on artifacts, creatures, enchantments, or lands";
    }

    private SolemnityEffect2(final SolemnityEffect2 effect) {
        super(effect);
    }

    @Override
    public SolemnityEffect2 copy() {
        return new SolemnityEffect2(this);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        return true;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ADD_COUNTERS;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        MageObject object = game.getObject(event.getTargetId());
        Permanent permanent1 = game.getPermanentEntering(event.getTargetId());
        Permanent permanent2 = game.getPermanent(event.getTargetId());

        if (object instanceof Permanent) {
            return filter.match((Permanent) object, game);
        } else if (permanent1 != null) {
            return filter.match(permanent1, game);
        } else if (permanent2 != null) {
            return filter.match(permanent2, game);
        }

        return false;
    }
}
