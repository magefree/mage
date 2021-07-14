package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.Game;
import mage.game.permanent.token.FaerieToken;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetadjustment.TargetAdjuster;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class StolenByTheFae extends CardImpl {

    public StolenByTheFae(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{X}{U}{U}");

        // Return target creature with converted mana cost X to its owner's hand. You create X 1/1 blue Faerie creature tokens with flying.
        this.getSpellAbility().addEffect(new ReturnToHandTargetEffect()
                .setText("Return target creature with mana value X to its owner's hand"));
        this.getSpellAbility().addEffect(new CreateTokenEffect(new FaerieToken(), ManacostVariableValue.REGULAR)
                .setText("You create X 1/1 blue Faerie creature tokens with flying"));
        this.getSpellAbility().setTargetAdjuster(StolenByTheFaeAdjuster.instance);
    }

    private StolenByTheFae(final StolenByTheFae card) {
        super(card);
    }

    @Override
    public StolenByTheFae copy() {
        return new StolenByTheFae(this);
    }
}

enum StolenByTheFaeAdjuster implements TargetAdjuster {
    instance;

    @Override
    public void adjustTargets(Ability ability, Game game) {
        ability.getTargets().clear();
        int xValue = ability.getManaCostsToPay().getX();
        FilterCreaturePermanent filter = new FilterCreaturePermanent("creature with mana value " + xValue);
        filter.add(new ManaValuePredicate(ComparisonType.EQUAL_TO, xValue));
        ability.addTarget(new TargetCreaturePermanent(filter));
    }
}