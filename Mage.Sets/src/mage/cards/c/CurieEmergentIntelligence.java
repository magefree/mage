package mage.cards.c;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.ExileTargetCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Outcome;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.PermanentCard;
import mage.MageInt;
import mage.MageObject;
import mage.target.common.TargetControlledPermanent;
import mage.util.functions.CopyApplier;

/**
 *
 * @author jam1garner
 */
public final class CurieEmergentIntelligence extends CardImpl {

    private static final FilterControlledPermanent filter =
            new FilterControlledPermanent("another nontoken artifact creature you control");

    static {
        filter.add(AnotherPredicate.instance);
        filter.add(Predicates.and(
            CardType.ARTIFACT.getPredicate(),
            CardType.CREATURE.getPredicate()
        ));
        filter.add(TokenPredicate.FALSE);
    }

    public CurieEmergentIntelligence(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{1}{U}");
        
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ROBOT);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Whenever Curie, Emergent Intelligence deals combat damage to a player, draw cards equal to its base power.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(
            new DrawCardSourceControllerEffect(CurieEmergentIntelligenceValue.NON_NEGATIVE).setText("draw cards equal to its base power"), false
        ));

        // {1}{U}, Exile another nontoken artifact creature you control: Curie becomes a copy of the exiled creature, except it has 
        // "Whenever this creature deals combat damage to a player, draw cards equal to its base power."
        Ability ability = new SimpleActivatedAbility(new CurieEmergentIntelligenceCopyEffect(), new ManaCostsImpl<>("{1}{U}"));
        ability.addCost(new ExileTargetCost(new TargetControlledPermanent(filter)));
        this.addAbility(ability);
    }

    private CurieEmergentIntelligence(final CurieEmergentIntelligence card) {
        super(card);
    }

    @Override
    public CurieEmergentIntelligence copy() {
        return new CurieEmergentIntelligence(this);
    }
}

enum CurieEmergentIntelligenceValue implements DynamicValue {
    NON_NEGATIVE;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        Permanent sourcePermanent = sourceAbility.getSourcePermanentOrLKI(game);
        if (sourcePermanent == null) {
            return 0;
        }

        // Minimum of 0 needed to account for Spinal Parasite
        return Math.max(0, sourcePermanent.getPower().getModifiedBaseValue());
    }

    @Override
    public CurieEmergentIntelligenceValue copy() {
        return this;
    }

    @Override
    public String toString() {
        return "X";
    }

    @Override
    public String getMessage() {
        return "{this}'s power";
    }
}

class CurieEmergentIntelligenceCopyEffect extends OneShotEffect {

    private static final CopyApplier applier = new CopyApplier() {
        @Override
        public boolean apply(Game game, MageObject blueprint, Ability source, UUID targetObjectId) {
            blueprint.getAbilities().add(new DealsCombatDamageToAPlayerTriggeredAbility(
                new DrawCardSourceControllerEffect(CurieEmergentIntelligenceValue.NON_NEGATIVE).setText("draw cards equal to its base power"), false
            ));
            return true;
        }
    };

    CurieEmergentIntelligenceCopyEffect() {
        super(Outcome.Benefit);
        this.setText("{this} becomes a copy of the exiled creature, except it has \"Whenever this creature deals combat damage to a player, draw cards equal to its base power.\"");
    }

    private CurieEmergentIntelligenceCopyEffect(final CurieEmergentIntelligenceCopyEffect effect) {
        super(effect);
    }

    @Override
    public CurieEmergentIntelligenceCopyEffect copy() {
        return new CurieEmergentIntelligenceCopyEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (Cost c : source.getCosts()) {
            if (c.isPaid() && c instanceof ExileTargetCost) {
                for (Permanent exiled : ((ExileTargetCost) c).getPermanents()) {
                    if (exiled != null) {
                        game.copyPermanent(
                            Duration.WhileOnBattlefield,
                            exiled,
                            source.getSourceId(), source, applier
                        );

                        return true;
                    } else {
                        return false;
                    }
                }
            }
        }

        return false;
    }
}
