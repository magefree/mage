package mage.cards.p;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.abilities.effects.common.PermanentsEnterBattlefieldTappedEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;
import mage.util.CardUtil;
import mage.watchers.Watcher;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PhyrexianCensor extends CardImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent("non-Phyrexian creatures");

    static {
        filter.add(Predicates.not(SubType.PHYREXIAN.getPredicate()));
    }

    public PhyrexianCensor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");

        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Each player can't cast more than one non-Phyrexian spell each turn.
        this.addAbility(new SimpleStaticAbility(new PhyrexianCensorEffect()), new PhyrexianCensorWatcher());

        // Non-Phyrexian creatures enter the battlefield tapped.
        this.addAbility(new SimpleStaticAbility(new PermanentsEnterBattlefieldTappedEffect(filter)));
    }

    private PhyrexianCensor(final PhyrexianCensor card) {
        super(card);
    }

    @Override
    public PhyrexianCensor copy() {
        return new PhyrexianCensor(this);
    }
}

class PhyrexianCensorEffect extends ContinuousRuleModifyingEffectImpl {

    PhyrexianCensorEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Detriment);
        staticText = "Each player can't cast more than one non-Phyrexian spell each turn";
    }

    private PhyrexianCensorEffect(final PhyrexianCensorEffect effect) {
        super(effect);
    }

    @Override
    public String getInfoMessage(Ability source, GameEvent event, Game game) {
        return "Each player can't cast more than one non-Phyrexian spell each turn";
    }

    @Override
    public PhyrexianCensorEffect copy() {
        return new PhyrexianCensorEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.CAST_SPELL;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Card card = game.getCard(event.getSourceId());
        return card != null
                && !card.hasSubtype(SubType.PHYREXIAN, game)
                && PhyrexianCensorWatcher.checkPlayer(event.getPlayerId(), game);
    }
}

class PhyrexianCensorWatcher extends Watcher {

    private final Map<UUID, Integer> map = new HashMap<>();

    PhyrexianCensorWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() != GameEvent.EventType.SPELL_CAST) {
            return;
        }
        Spell spell = game.getSpell(event.getTargetId());
        if (spell != null && !spell.hasSubtype(SubType.PHYREXIAN, game)) {
            map.compute(spell.getControllerId(), CardUtil::setOrIncrementValue);
        }
    }

    @Override
    public void reset() {
        super.reset();
        map.clear();
    }

    public static boolean checkPlayer(UUID playerId, Game game) {
        return game
                .getState()
                .getWatcher(PhyrexianCensorWatcher.class)
                .map
                .getOrDefault(playerId, 0) > 0;
    }
}
