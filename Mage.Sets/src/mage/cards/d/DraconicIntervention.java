package mage.cards.d;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.costs.common.ExileFromGraveCost;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageAllEffect;
import mage.abilities.effects.common.ExileSpellEffect;
import mage.abilities.effects.common.replacement.DealtDamageToCreatureBySourceDies;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.target.common.TargetCardInYourGraveyard;
import mage.watchers.common.DamagedByWatcher;

import java.util.Collection;
import java.util.Objects;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DraconicIntervention extends CardImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent("non-Dragon creature");

    static {
        filter.add(Predicates.not(SubType.DRAGON.getPredicate()));
    }

    public DraconicIntervention(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{R}{R}");

        // As an additional cost to cast this spell, exile an instant or sorcery card from your graveyard.
        this.getSpellAbility().addCost(new ExileFromGraveCost(
                new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_INSTANT_OR_SORCERY)
        ));

        // Draconic Intervention deals X damage to each non-Dragon creature, where X is the exiled card's mana value. If a creature dealt damage this way would die this turn, exile it instead.
        this.getSpellAbility().addEffect(new DamageAllEffect(DraconicInterventionValue.instance, filter));
        this.getSpellAbility().addEffect(new DealtDamageToCreatureBySourceDies(this, Duration.EndOfTurn));
        this.getSpellAbility().addWatcher(new DamagedByWatcher(false));

        // Exile Draconic Intervention.
        this.getSpellAbility().addEffect(new ExileSpellEffect().concatBy("<br>"));
    }

    private DraconicIntervention(final DraconicIntervention card) {
        super(card);
    }

    @Override
    public DraconicIntervention copy() {
        return new DraconicIntervention(this);
    }
}

enum DraconicInterventionValue implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        return sourceAbility
                .getCosts()
                .stream()
                .filter(ExileFromGraveCost.class::isInstance)
                .map(ExileFromGraveCost.class::cast)
                .map(ExileFromGraveCost::getExiledCards)
                .flatMap(Collection::stream)
                .filter(Objects::nonNull)
                .map(MageObject::getManaValue)
                .findFirst()
                .orElse(0);
    }

    @Override
    public DraconicInterventionValue copy() {
        return instance;
    }

    @Override
    public String toString() {
        return "X";
    }

    @Override
    public String getMessage() {
        return "the exiled card's mana value";
    }
}