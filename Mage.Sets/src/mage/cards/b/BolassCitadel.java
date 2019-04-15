package mage.cards.b;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.costs.AlternativeCostSourceAbility;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.LoseLifeOpponentsEffect;
import mage.abilities.effects.common.continuous.LookAtTopCardOfLibraryAnyTimeEffect;
import mage.abilities.effects.common.continuous.PlayTheTopCardEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.target.common.TargetControlledPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BolassCitadel extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("nonland permanents");

    static {
        filter.add(Predicates.not(new CardTypePredicate(CardType.LAND)));
    }

    public BolassCitadel(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}{B}{B}{B}");

        this.addSuperType(SuperType.LEGENDARY);

        // You may look at the top card of your library any time.
        this.addAbility(new SimpleStaticAbility(new LookAtTopCardOfLibraryAnyTimeEffect()));

        // You may play the top card of your library. If you cast a spell this way, pay life equal to its converted mana cost rather than pay its mana cost.
        Ability ability = new SimpleStaticAbility(new PlayTheTopCardEffect());
        ability.addEffect(new BolassCitadelCastEffect());
        this.addAbility(ability);

        // {T}, Sacrifice ten nonland permanents: Each opponent loses 10 life.
        ability = new SimpleActivatedAbility(new LoseLifeOpponentsEffect(10), new TapSourceCost());
        ability.addCost(new SacrificeTargetCost(new TargetControlledPermanent(
                10, 10, filter, true
        )));
        this.addAbility(ability);
    }

    private BolassCitadel(final BolassCitadel card) {
        super(card);
    }

    @Override
    public BolassCitadel copy() {
        return new BolassCitadel(this);
    }
}

class BolassCitadelCastEffect extends ContinuousEffectImpl {

    private static final AlternativeCostSourceAbility alternateCost
            = new AlternativeCostSourceAbility(new PayLifeCost(3), BolassCitadelCondition.instance);

    BolassCitadelCastEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Detriment);
        staticText = "If you cast a spell this way, pay life equal " +
                "to its converted mana cost rather than pay its mana cost.";
    }

    private BolassCitadelCastEffect(final BolassCitadelCastEffect effect) {
        super(effect);
    }

    @Override
    public BolassCitadelCastEffect copy() {
        return new BolassCitadelCastEffect(this);
    }

    @Override
    public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            controller.getAlternativeSourceCosts().add(alternateCost);
            return true;
        }
        return false;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public boolean hasLayer(Layer layer) {
        return layer == Layer.RulesEffects;
    }
}

enum BolassCitadelCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(game.getOwnerId(source.getSourceId()));
        if (player == null) {
            return false;
        }
        Spell spell = game.getSpell(source.getSourceId());
        return spell != null && spell.getFromZone() == Zone.LIBRARY;
    }
}
