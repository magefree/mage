package mage.cards.k;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.cost.CostModificationEffectImpl;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.HasteAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
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
public final class KazaRoilChaser extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledPermanent(SubType.WIZARD);
    private static final DynamicValue xValue = new PermanentsOnBattlefieldCount(filter);
    private static final Hint hint = new ValueHint("Wizards you control", xValue);

    public KazaRoilChaser(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{U}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // {T}: The next instant or sorcery spell you cast this turn costs {X} less to cast, where X is the number of Wizards you control as this ability resolves.
        this.addAbility(new SimpleActivatedAbility(
                new KazaRoilChaserEffect(), new TapSourceCost()
        ).addHint(hint), new KazaRoilChaserWatcher());
    }

    private KazaRoilChaser(final KazaRoilChaser card) {
        super(card);
    }

    @Override
    public KazaRoilChaser copy() {
        return new KazaRoilChaser(this);
    }
}

class KazaRoilChaserEffect extends CostModificationEffectImpl {

    private static final FilterPermanent filter = new FilterControlledPermanent(SubType.WIZARD);
    private int spellsCast;
    private int wizardCount = 0;

    KazaRoilChaserEffect() {
        super(Duration.EndOfTurn, Outcome.Benefit, CostModificationType.REDUCE_COST);
        staticText = "the next instant or sorcery spell you cast this turn costs {X} less to cast, " +
                "where X is the number of Wizards you control as this ability resolves";
    }

    private KazaRoilChaserEffect(final KazaRoilChaserEffect effect) {
        super(effect);
        this.spellsCast = effect.spellsCast;
        this.wizardCount = effect.wizardCount;
    }

    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);
        KazaRoilChaserWatcher watcher = game.getState().getWatcher(KazaRoilChaserWatcher.class);
        if (watcher != null) {
            spellsCast = watcher.getCount(source.getControllerId());
        }
        wizardCount = game.getBattlefield().count(filter, source.getControllerId(), source, game);
    }

    @Override
    public boolean apply(Game game, Ability source, Ability abilityToModify) {
        CardUtil.reduceCost(abilityToModify, wizardCount);
        return true;
    }

    @Override
    public boolean applies(Ability abilityToModify, Ability source, Game game) {
        KazaRoilChaserWatcher watcher = game.getState().getWatcher(KazaRoilChaserWatcher.class);
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
    public KazaRoilChaserEffect copy() {
        return new KazaRoilChaserEffect(this);
    }
}

class KazaRoilChaserWatcher extends Watcher {

    private final Map<UUID, Integer> playerMap = new HashMap<>();

    KazaRoilChaserWatcher() {
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
