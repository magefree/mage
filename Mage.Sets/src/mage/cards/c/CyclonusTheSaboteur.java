package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.SourceMatchesFilterCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.effects.keyword.ConniveSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.LivingMetalAbility;
import mage.abilities.keyword.MoreThanMeetsTheEyeAbility;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.predicate.mageobject.PowerPredicate;
import mage.game.Game;
import mage.game.turn.TurnMod;

import java.util.UUID;

/**
 * @author mllagostera
 */
public final class CyclonusTheSaboteur extends TransformingDoubleFacedCard {

    private static final FilterPermanent filter = new FilterPermanent();
    static { filter.add(new PowerPredicate(ComparisonType.MORE_THAN,4)); }
    private static final Condition condition = new SourceMatchesFilterCondition(filter);

    public CyclonusTheSaboteur(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new SuperType[]{SuperType.LEGENDARY}, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, new SubType[]{SubType.ROBOT}, "{2}{U}{B}",
                "Cyclonus, Cybertronian Fighter",
                new SuperType[]{SuperType.LEGENDARY}, new CardType[]{CardType.ARTIFACT}, new SubType[]{SubType.VEHICLE}, "UB");

        // Cyclonus, the Saboteur
        this.getLeftHalfCard().setPT(2, 5);

        // More Than Meets the Eye {5}{U}{B}
        this.getLeftHalfCard().addAbility(new MoreThanMeetsTheEyeAbility(this, "{5}{U}{B}"));

        // Flying
        this.getLeftHalfCard().addAbility(FlyingAbility.getInstance());

        // Whenever Cyclonus deals combat damage to a player, it connives. Then if Cyclonus's power is 5 or greater, convert it.
        Ability ability = new DealsCombatDamageToAPlayerTriggeredAbility(
                new ConniveSourceEffect("it"), false, true
        );
        ability.addEffect(new ConditionalOneShotEffect(
                new TransformSourceEffect(), condition,
                "Then if {this}'s power is 5 or greater, convert it."
        ));
        this.getLeftHalfCard().addAbility(ability);

        // Cyclonus, Cybertronian Fighter
        this.getRightHalfCard().setPT(5, 5);

        // Living metal
        this.getRightHalfCard().addAbility(new LivingMetalAbility());

        // Flying
        this.getRightHalfCard().addAbility(FlyingAbility.getInstance());

        // Whenever Cyclonus deals combat damage to a player, transform it. If you do, there is an additional beginning phase after this phase.
        Ability abilityBack = new DealsCombatDamageToAPlayerTriggeredAbility(
                new CyclonusCybertronianFighterEffect(),
                false
        );
        this.getRightHalfCard().addAbility(abilityBack);
    }

    private CyclonusTheSaboteur(final CyclonusTheSaboteur card) {
        super(card);
    }

    @Override
    public CyclonusTheSaboteur copy() {
        return new CyclonusTheSaboteur(this);
    }
}

class CyclonusCybertronianFighterEffect extends TransformSourceEffect {

    CyclonusCybertronianFighterEffect() {
        super();
        staticText = "convert it. If you do, there is an additional beginning phase after this phase";
    }

    private CyclonusCybertronianFighterEffect(final CyclonusCybertronianFighterEffect effect) {
        super(effect);
    }

    @Override
    public CyclonusCybertronianFighterEffect copy() {
        return new CyclonusCybertronianFighterEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        if (!super.apply(game, source)) {
            return false;
        }
        TurnMod beginning = new TurnMod(game.getState().getActivePlayerId()).withExtraPhase(TurnPhase.BEGINNING);
        game.getState().getTurnMods().add(beginning);
        return true;
    }
}
