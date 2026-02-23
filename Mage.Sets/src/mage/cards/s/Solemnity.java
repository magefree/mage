package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.ruleModifying.CantHaveCountersAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;

import java.util.UUID;

/**
 * @author spjspj
 */
public final class Solemnity extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent();

    static {
        filter.add(Predicates.or(
                CardType.ARTIFACT.getPredicate(),
                CardType.CREATURE.getPredicate(),
                CardType.ENCHANTMENT.getPredicate(),
                CardType.LAND.getPredicate()
        ));
    }

    public Solemnity(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{W}");

        // Players can't get counters.
        this.addAbility(new SimpleStaticAbility(new SolemnityEffect()));

        // Counters can't be put on artifacts, creatures, enchantments, or lands.
        this.addAbility(new SimpleStaticAbility(new CantHaveCountersAllEffect(filter, null)
                .setText("counters can't be put on artifacts, creatures, enchantments, or lands")));
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

    SolemnityEffect() {
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
