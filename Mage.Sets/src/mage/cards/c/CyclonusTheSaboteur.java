package mage.cards.c;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.SourceMatchesFilterCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.effects.keyword.ConniveSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.MoreThanMeetsTheEyeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.predicate.mageobject.PowerPredicate;

import java.util.UUID;

/**
 * @author mllagostera
 */
public final class CyclonusTheSaboteur extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent();

    static {
        filter.add(new PowerPredicate(ComparisonType.MORE_THAN,4));
    }

    private static final Condition condition = new SourceMatchesFilterCondition(filter);

    public CyclonusTheSaboteur(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{2}{U}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ROBOT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(5);
        this.secondSideCardClazz = mage.cards.c.CyclonusCybertronianFighter.class;

        // More Than Meets the Eye {5}{U}{B}
        this.addAbility(new MoreThanMeetsTheEyeAbility(this, "{5}{U}{B}"));

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever Cyclonus deals combat damage to a player, it connives.
        Ability ability = new DealsCombatDamageToAPlayerTriggeredAbility(
            new ConniveSourceEffect("it"),
             false,
              true
            );

        // Then if Cyclonus's power is 5 or greater, convert it
        ability.addEffect((new ConditionalOneShotEffect(
                new TransformSourceEffect(),
                condition, 
                "Then if {this}'s power is 5 or greater, convert it."
        )));
        
        this.addAbility(ability);
    }

    private CyclonusTheSaboteur(final CyclonusTheSaboteur card) {
        super(card);
    }

    @Override
    public CyclonusTheSaboteur copy() {
        return new CyclonusTheSaboteur(this);
    }
}
