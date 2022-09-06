package mage.cards.k;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.ExileSourceCost;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.costs.mana.VariableManaCost;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DestroyAllEffect;
import mage.abilities.effects.common.cost.CostModificationEffectImpl;
import mage.abilities.mana.ActivatedManaAbilityImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterNonlandPermanent;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.targetadjustment.TargetAdjuster;

import java.util.Optional;
import java.util.UUID;

/**
 * @author Alex-Vasile
 */
public class KarnsSylex extends CardImpl {
    public KarnsSylex(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{}, "");

        // Karn’s Sylex
        this.addAbility(new EntersBattlefieldTappedAbility());

        // Players can’t pay life to cast spells or to activate abilities that aren’t mana abilities.
        this.addAbility(new SimpleStaticAbility(new KarnsSylexCantPayLifeEffect()));

        // {X}, {T}, Exile Karn’s Sylex: Destroy each nonland permanent with mana value X or less. Activate only as a sorcery.
        Ability ability = new ActivateAsSorceryActivatedAbility(new KarnsSylexDestroyEffect(), new ManaCostsImpl<>("{X}"));
        ability.addCost(new TapSourceCost());
        ability.addCost(new ExileSourceCost());
        this.addAbility(ability);
    }

    private KarnsSylex(final KarnsSylex card) {
        super(card);
    }

    @Override
    public KarnsSylex copy() {
        return new KarnsSylex(this);
    }
}

class KarnsSylexCantPayLifeEffect extends ContinuousRuleModifyingEffectImpl {

    KarnsSylexCantPayLifeEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Detriment);
        staticText = "players can’t pay life to cast spells or to activate abilities that aren’t mana abilities";
    }

    private KarnsSylexCantPayLifeEffect(final KarnsSylexCantPayLifeEffect effect) {
        super(effect);
    }

    @Override
    public KarnsSylexCantPayLifeEffect copy() {
        return new KarnsSylexCantPayLifeEffect(this);
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        MageObject object = game.getObject(event.getSourceId());
        if (event.getType() == GameEvent.EventType.CAST_SPELL) {
            if (object == null) {
                return false;
            }
//            return object != null && ((CardImpl) object).getManaCost().stream().anyMatch(manaCost -> manaCost.i)
        } else if (event.getType() == GameEvent.EventType.ACTIVATE_ABILITY) {
            Optional<Ability> abilityOptional = game.getAbility(event.getTargetId(), event.getSourceId());
            if (!abilityOptional.isPresent()) {
                return false;
            }
            Ability ability = abilityOptional.get();
            boolean lifeCost = ability.getCosts().stream().anyMatch(cost -> cost instanceof PayLifeCost);
            return lifeCost && !(ability instanceof ActivatedManaAbilityImpl);
        } else {
            return false;
        }
        return false;
    }
}

class KarnsSylexDestroyEffect extends OneShotEffect {

    KarnsSylexDestroyEffect() {
        super(Outcome.DestroyPermanent);
        staticText = "Destroy each nonland permanent with mana value X or less.";
    }

    private KarnsSylexDestroyEffect(final KarnsSylexDestroyEffect effect) {
        super(effect);
    }

    public KarnsSylexDestroyEffect copy() {
        return new KarnsSylexDestroyEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        FilterNonlandPermanent filter = new FilterNonlandPermanent();
        filter.add(new ManaValuePredicate(ComparisonType.FEWER_THAN, source.getManaCostsToPay().getX() + 1));

        boolean destroyed = false;
        for (Permanent permanent : game.getState().getBattlefield().getActivePermanents(filter, source.getControllerId(), game)) {
            destroyed |= permanent.destroy(source, game);
        }
        return destroyed;
    }
}