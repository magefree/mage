
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.condition.InvertCondition;
import mage.abilities.condition.common.IsPhaseCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.continuous.ExchangeControlTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class DjinnOfInfiniteDeceits extends CardImpl {

    private static final String rule = "Exchange control of two target nonlegendary creatures";
    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("nonlegendary creature");
    static {
        filter.add(Predicates.not(SuperType.LEGENDARY.getPredicate()));
    }

    public DjinnOfInfiniteDeceits(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{U}{U}");
        this.subtype.add(SubType.DJINN);

        this.power = new MageInt(2);
        this.toughness = new MageInt(7);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // {tap}: Exchange control of two target nonlegendary creatures. You can't activate this ability during combat.
        Ability ability = new ActivateIfConditionActivatedAbility(
                Zone.BATTLEFIELD, 
                new ExchangeControlTargetEffect(Duration.EndOfGame, rule), 
                new TapSourceCost(), 
                new InvertCondition(new IsPhaseCondition(TurnPhase.COMBAT)));
        ability.addTarget(new TargetCreaturePermanent(2,2, filter, false));
        this.addAbility(ability);
    }

    private DjinnOfInfiniteDeceits(final DjinnOfInfiniteDeceits card) {
        super(card);
    }

    @Override
    public DjinnOfInfiniteDeceits copy() {
        return new DjinnOfInfiniteDeceits(this);
    }
}
