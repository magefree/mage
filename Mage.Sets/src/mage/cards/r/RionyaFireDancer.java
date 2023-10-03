package mage.cards.r;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfCombatTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenCopyTargetEffect;
import mage.abilities.hint.Hint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;
import mage.target.TargetPermanent;
import mage.util.CardUtil;
import mage.watchers.Watcher;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RionyaFireDancer extends CardImpl {

    public RionyaFireDancer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // At the beginning of combat on your turn, create X tokens that are copies of another target creature you control, where X is one plus the number of instant and sorcery spells you've cast this turn. They gain haste. Exile them at the beginning of the next end step.
        Ability ability = new BeginningOfCombatTriggeredAbility(
                new RionyaFireDancerEffect(), TargetController.YOU, false
        );
        ability.addTarget(new TargetPermanent(StaticFilters.FILTER_ANOTHER_CREATURE_YOU_CONTROL));
        this.addAbility(ability.addHint(RionyaFireDancerHint.instance), new RionyaFireDancerWatcher());
    }

    private RionyaFireDancer(final RionyaFireDancer card) {
        super(card);
    }

    @Override
    public RionyaFireDancer copy() {
        return new RionyaFireDancer(this);
    }
}

enum RionyaFireDancerHint implements Hint {
    instance;

    @Override
    public String getText(Game game, Ability ability) {
        return "Instants and sorceries you've cast this turn: "
                + RionyaFireDancerWatcher.getValue(ability.getControllerId(), game);
    }

    @Override
    public RionyaFireDancerHint copy() {
        return instance;
    }
}

class RionyaFireDancerEffect extends OneShotEffect {

    RionyaFireDancerEffect() {
        super(Outcome.Benefit);
        staticText = "create X tokens that are copies of another target creature you control, " +
                "where X is one plus the number of instant and sorcery spells you've cast this turn. " +
                "They gain haste. Exile them at the beginning of the next end step";
    }

    private RionyaFireDancerEffect(final RionyaFireDancerEffect effect) {
        super(effect);
    }

    @Override
    public RionyaFireDancerEffect copy() {
        return new RionyaFireDancerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        CreateTokenCopyTargetEffect effect = new CreateTokenCopyTargetEffect(
                source.getControllerId(), null, true,
                RionyaFireDancerWatcher.getValue(source.getControllerId(), game) + 1
        );
        effect.apply(game, source);
        effect.exileTokensCreatedAtNextEndStep(game, source);
        return true;
    }
}

class RionyaFireDancerWatcher extends Watcher {

    private final Map<UUID, Integer> playerMap = new HashMap<>();

    RionyaFireDancerWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() != GameEvent.EventType.SPELL_CAST) {
            return;
        }
        Spell spell = game.getSpell(event.getTargetId());
        if (spell != null && spell.isInstantOrSorcery(game)) {
            playerMap.compute(spell.getControllerId(), CardUtil::setOrIncrementValue);
        }
    }

    @Override
    public void reset() {
        super.reset();
        playerMap.clear();
    }

    static int getValue(UUID playerId, Game game) {
        RionyaFireDancerWatcher watcher = game.getState().getWatcher(RionyaFireDancerWatcher.class);
        return watcher == null ? 0 : watcher.playerMap.getOrDefault(playerId, 0);
    }
}
