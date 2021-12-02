package mage.cards.m;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.common.cost.CostModificationEffectImpl;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.util.CardUtil;
import mage.watchers.Watcher;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MaelstromMuse extends CardImpl {

    public MaelstromMuse(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}{U/R}{R}");

        this.subtype.add(SubType.DJINN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever Maelstrom Muse attacks, the next instant or sorcery spell you cast this turn costs {X} less to cast, where X is Maelstrom Muse's power as this ability resolves.
        this.addAbility(new AttacksTriggeredAbility(
                new MaelstromMuseEffect(), false
        ), new MaelstromMuseWatcher());
    }

    private MaelstromMuse(final MaelstromMuse card) {
        super(card);
    }

    @Override
    public MaelstromMuse copy() {
        return new MaelstromMuse(this);
    }
}

class MaelstromMuseEffect extends CostModificationEffectImpl {

    private int spellsCast;
    private int sourcePower = 0;

    MaelstromMuseEffect() {
        super(Duration.EndOfTurn, Outcome.Benefit, CostModificationType.REDUCE_COST);
        staticText = "the next instant or sorcery spell you cast this turn costs {X} less to cast, " +
                "where X is {this}'s power as this ability resolves";
    }

    private MaelstromMuseEffect(final MaelstromMuseEffect effect) {
        super(effect);
        this.spellsCast = effect.spellsCast;
        this.sourcePower = effect.sourcePower;
    }

    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);
        MaelstromMuseWatcher watcher = game.getState().getWatcher(MaelstromMuseWatcher.class);
        if (watcher != null) {
            spellsCast = watcher.getCount(source.getControllerId());
        }
        Permanent permanent = source.getSourcePermanentOrLKI(game);
        sourcePower = permanent != null ? Math.max(permanent.getPower().getValue(), 0) : 0;
    }

    @Override
    public boolean apply(Game game, Ability source, Ability abilityToModify) {
        CardUtil.reduceCost(abilityToModify, sourcePower);
        return true;
    }

    @Override
    public boolean applies(Ability abilityToModify, Ability source, Game game) {
        MaelstromMuseWatcher watcher = game.getState().getWatcher(MaelstromMuseWatcher.class);
        if (watcher == null) {
            return false;
        }
        if (watcher.getCount(source.getControllerId()) > spellsCast) {
            discard(); // only one use
            return false;
        }
        if (!(abilityToModify instanceof SpellAbility)
                || !abilityToModify.isControlledBy(source.getControllerId())) {
            return false;
        }
        Card spellCard = ((SpellAbility) abilityToModify).getCharacteristics(game);
        return spellCard != null && spellCard.isInstantOrSorcery(game);
    }

    @Override
    public MaelstromMuseEffect copy() {
        return new MaelstromMuseEffect(this);
    }
}

class MaelstromMuseWatcher extends Watcher {

    private final Map<UUID, Integer> playerMap = new HashMap<>();

    MaelstromMuseWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() != GameEvent.EventType.SPELL_CAST) {
            return;
        }
        Spell spell = game.getSpell(event.getSourceId());
        if (spell != null && spell.isInstantOrSorcery(game)) {
            playerMap.compute(event.getPlayerId(), CardUtil::setOrIncrementValue);
        }
    }

    @Override
    public void reset() {
        super.reset();
        playerMap.clear();
    }

    int getCount(UUID playerId) {
        return playerMap.getOrDefault(playerId, 0);
    }
}
