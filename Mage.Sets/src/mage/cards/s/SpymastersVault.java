package mage.cards.s;

import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.keyword.ConniveSourceEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.abilities.mana.BlackManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.WatcherScope;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.watchers.Watcher;

/**
 *
 * @author grimreap124
 */
public final class SpymastersVault extends CardImpl {

    public SpymastersVault(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[] { CardType.LAND }, "");

        // Spymaster's Vault enters the battlefield tapped unless you control a Swamp.
        // {T}: Add {B}.
        this.addAbility(new BlackManaAbility());
        // {B}, {T}: Target creature you control connives X, where X is the number of creatures that died this turn.
        Ability ability = new SimpleActivatedAbility(new SpymastersVaultEffect(), new ManaCostsImpl<>("{B}"));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetControlledCreaturePermanent());
        this.addAbility(ability.addHint(SpymastersVaultCount.getHint()));
    }

    private SpymastersVault(final SpymastersVault card) {
        super(card);
    }

    @Override
    public SpymastersVault copy() {
        return new SpymastersVault(this);
    }
}

class SpymastersVaultEffect extends OneShotEffect {

    SpymastersVaultEffect() {
        super(Outcome.Benefit);
        staticText = "Target creature you control connives X, where X is the number of creatures that died this turn";
    }

    private SpymastersVaultEffect(final SpymastersVaultEffect effect) {
        super(effect);
    }

    @Override
    public SpymastersVaultEffect copy() {
        return new SpymastersVaultEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int deaths = SpymastersVaultCount.instance.calculate(game, source, this);

        if (deaths < 1) {
            return false;
        }
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        if (permanent == null) {
            return false;
        }
        return ConniveSourceEffect.connive(permanent, deaths, source, game);
    }
}

enum SpymastersVaultCount implements DynamicValue {
    instance;

    private static final Hint hint = new ValueHint("Number of creatures that died this turn", instance);

    public static Hint getHint() {
        return hint;
    }

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        CreaturesDiedThisTurnWatcher watcher = game.getState().getWatcher(CreaturesDiedThisTurnWatcher.class);
        return watcher == null ? 0 : watcher.getCreaturesDiedThisTurn();
    }

    @Override
    public SpymastersVaultCount copy() {
        return instance;
    }

    @Override
    public String getMessage() {
        return "";
    }
}

class CreaturesDiedThisTurnWatcher extends Watcher {

    public int getCreaturesDiedThisTurn() {
        return creaturesDiedThisTurn;
    }

    private int creaturesDiedThisTurn = 0;

    public CreaturesDiedThisTurnWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.ZONE_CHANGE && ((ZoneChangeEvent) event).isDiesEvent()) {
            MageObject mageObject = game.getLastKnownInformation(event.getTargetId(), Zone.BATTLEFIELD);
            if (mageObject != null && mageObject.isCreature(game)) {
                creaturesDiedThisTurn++;
            }
        }
    }

    @Override
    public void reset() {
        super.reset();
        creaturesDiedThisTurn = 0;
    }

}