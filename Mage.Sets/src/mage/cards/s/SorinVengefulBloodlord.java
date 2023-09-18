package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.MyTurnCondition;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.PayVariableLoyaltyCost;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.hint.common.MyTurnHint;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.common.TargetPlayerOrPlaneswalker;
import mage.target.targetadjustment.TargetAdjuster;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SorinVengefulBloodlord extends CardImpl {

    public SorinVengefulBloodlord(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{2}{W}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.SORIN);
        this.setStartingLoyalty(4);

        // As long as it's your turn, creatures and planeswalkers you control have lifelink.
        this.addAbility(new SimpleStaticAbility(new ConditionalContinuousEffect(
                new GainAbilityControlledEffect(
                        LifelinkAbility.getInstance(), Duration.WhileOnBattlefield,
                        StaticFilters.FILTER_PERMANENT_CREATURE_OR_PLANESWALKER_A
                ), MyTurnCondition.instance, "As long as it's your turn, " +
                "creatures and planeswalkers you control have lifelink"
        )).addHint(MyTurnHint.instance));

        // +2: Sorin, Vengeful Bloodlord deals 1 damage to target player or planeswalker.
        Ability ability = new LoyaltyAbility(new DamageTargetEffect(1), 2);
        ability.addTarget(new TargetPlayerOrPlaneswalker());
        this.addAbility(ability);

        // -X: Return target creature card with converted mana cost X from your graveyard to the battlefield. That creature is a vampire in addition to its other types.
        ability = new LoyaltyAbility(new ReturnFromGraveyardToBattlefieldTargetEffect().setText(
                "Return target creature card with mana value X from your graveyard to the battlefield"
        ));
        ability.addEffect(new SorinVengefulBloodlordEffect());
        ability.setTargetAdjuster(SorinVengefulBloodlordAdjuster.instance);
        this.addAbility(ability);
    }

    private SorinVengefulBloodlord(final SorinVengefulBloodlord card) {
        super(card);
    }

    @Override
    public SorinVengefulBloodlord copy() {
        return new SorinVengefulBloodlord(this);
    }
}

enum SorinVengefulBloodlordAdjuster implements TargetAdjuster {
    instance;

    @Override
    public void adjustTargets(Ability ability, Game game) {
        int xValue = 0;
        for (Cost cost : ability.getCosts()) {
            if (cost instanceof PayVariableLoyaltyCost) {
                xValue = ((PayVariableLoyaltyCost) cost).getAmount();
            }
        }
        FilterCard filter = new FilterCreatureCard("creature card with mana value " + xValue + " or less");
        filter.add(new ManaValuePredicate(ComparisonType.FEWER_THAN, xValue + 1));
        ability.getTargets().clear();
        ability.addTarget(new TargetCardInYourGraveyard(filter));
    }
}

class SorinVengefulBloodlordEffect extends ContinuousEffectImpl {
    SorinVengefulBloodlordEffect() {
        super(Duration.Custom, Outcome.Neutral);
        staticText = "That creature is a Vampire in addition to its other types";
    }

    private SorinVengefulBloodlordEffect(final SorinVengefulBloodlordEffect effect) {
        super(effect);
    }

    @Override
    public boolean hasLayer(Layer layer) {
        return layer == Layer.TypeChangingEffects_4;
    }

    @Override
    public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
        Permanent creature;
        if (source.getTargets().getFirstTarget() == null) {
            creature = game.getPermanent(getTargetPointer().getFirst(game, source));
        } else {
            creature = game.getPermanent(source.getTargets().getFirstTarget());
            if (creature == null) {
                creature = game.getPermanentEntering(source.getTargets().getFirstTarget());
            }
        }
        if (creature != null) {
            creature.addSubType(game, SubType.VAMPIRE);
            return true;
        } else {
            this.used = true;
        }
        return false;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public SorinVengefulBloodlordEffect copy() {
        return new SorinVengefulBloodlordEffect(this);
    }
}
