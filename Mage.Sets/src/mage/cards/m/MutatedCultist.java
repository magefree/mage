package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.RemoveAllCountersSourceCost;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CastSourceTriggeredAbility;
import mage.abilities.effects.common.cost.CostModificationEffectImpl;
import mage.abilities.effects.common.cost.SpellCostReductionForEachSourceEffect;
import mage.abilities.effects.common.cost.SpellsCostReductionAllEffect;
import mage.constants.*;
import mage.abilities.keyword.DevoidAbility;
import mage.abilities.keyword.DeathtouchAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.counters.CounterType;
import mage.filter.FilterOpponent;
import mage.filter.FilterPermanent;
import mage.filter.FilterPlayer;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.common.FilterPermanentOrPlayer;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.filter.predicate.permanent.CounterAnyPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetPermanentOrPlayer;
import mage.util.CardUtil;
import mage.watchers.common.CastSpellLastTurnWatcher;

/**
 *
 * @author grimreap124
 */
public final class MutatedCultist extends CardImpl {

    private static final FilterPermanentOrPlayer filter =
            new FilterPermanentOrPlayer(
                    "target permanent or opponent",
                    new FilterPermanent(), new FilterOpponent()
            );

    public MutatedCultist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");
        
        this.subtype.add(SubType.ELDRAZI);
        this.subtype.add(SubType.HORROR);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Devoid
        this.addAbility(new DevoidAbility(this.color));

        // When you cast this spell, remove all counters from up to one target permanent or opponent. The next spell you cast this turn costs {1} less to cast for each counter removed this way.
        Ability ability = new CastSourceTriggeredAbility(new MutatedCultistEffect());
        ability.addTarget(new TargetPermanentOrPlayer(filter));
        this.addAbility(ability);
        // Deathtouch
        this.addAbility(DeathtouchAbility.getInstance());

    }

    private MutatedCultist(final MutatedCultist card) {
        super(card);
    }

    @Override
    public MutatedCultist copy() {
        return new MutatedCultist(this);
    }
}

class MutatedCultistEffect extends OneShotEffect {

    MutatedCultistEffect() {
        super(Outcome.BoostCreature);
        this.staticText = "remove all counters from up to one target permanent or opponent. The next spell you cast this turn costs {1} less to cast for each counter removed this way.";
    }

    private MutatedCultistEffect(final MutatedCultistEffect effect) {
        super(effect);
    }

    @Override
    public MutatedCultistEffect copy() {
        return new MutatedCultistEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int countersRemoved = 0;
        UUID targetId = source.getTargets().getFirstTarget();

        Player targetedPlayer = game.getPlayer(targetId);
        if (targetedPlayer != null) {
            countersRemoved = targetedPlayer.loseAllCounters(source, game);
        }
        Permanent targetPermanent = game.getPermanent(targetId);
        if (targetPermanent != null) {
            countersRemoved = targetPermanent.removeAllCounters(source, game);
        }
        if (countersRemoved > 0) {
            game.informPlayers("Removed " + countersRemoved);
            game.addEffect(new MutatedCultistSpellsCostReductionEffect(countersRemoved).setDuration(Duration.OneUse), source);
            return true;
        }
        return false;
    }
}

class MutatedCultistSpellsCostReductionEffect extends CostModificationEffectImpl {

    int spellsCast;
    int reductionAmount;
    
    public MutatedCultistSpellsCostReductionEffect(int reductionAmount) {
        super(Duration.EndOfTurn, Outcome.Benefit, CostModificationType.REDUCE_COST);
        this.reductionAmount = reductionAmount;
    }

    protected MutatedCultistSpellsCostReductionEffect(final MutatedCultistSpellsCostReductionEffect effect) {
        super(effect);
        this.spellsCast = effect.spellsCast;
        this.reductionAmount = effect.reductionAmount;
    }

    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);
        CastSpellLastTurnWatcher watcher = game.getState().getWatcher(CastSpellLastTurnWatcher.class);
        if (watcher != null) {
            spellsCast =  watcher.getAmountOfSpellsPlayerCastOnCurrentTurn(source.getControllerId());
        }
    }

    @Override
    public boolean apply(Game game, Ability source, Ability abilityToModify) {
        CardUtil.reduceCost(abilityToModify, reductionAmount);
        return true;
    }

    @Override
    public boolean applies(Ability abilityToModify, Ability source, Game game) {
        CastSpellLastTurnWatcher watcher = game.getState().getWatcher(CastSpellLastTurnWatcher.class);
        if (watcher != null) {
            if (watcher.getAmountOfSpellsPlayerCastOnCurrentTurn(source.getControllerId()) > spellsCast) {
                discard(); // only one use 
                return false;
            }
        }        
        if (abilityToModify instanceof SpellAbility) {
            return abilityToModify.isControlledBy(source.getControllerId());
        }
        return false;
    }

    @Override
    public MutatedCultistSpellsCostReductionEffect copy() {
        return new MutatedCultistSpellsCostReductionEffect(this);
    }
}
