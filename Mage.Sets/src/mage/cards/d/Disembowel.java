
package mage.cards.d;

import mage.abilities.Ability;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.Game;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetadjustment.TargetAdjuster;

import java.util.UUID;

/**
 * @author LoneFox
 */
public final class Disembowel extends CardImpl {

    public Disembowel(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{X}{B}");

        // Destroy target creature with converted mana cost X.
        this.getSpellAbility().addEffect(new DestroyTargetEffect("destroy target creature with mana value X"));
        this.getSpellAbility().setTargetAdjuster(DisembowelAdjuster.instance);
    }

    private Disembowel(final Disembowel card) {
        super(card);
    }

    @Override
    public Disembowel copy() {
        return new Disembowel(this);
    }
}

enum DisembowelAdjuster implements TargetAdjuster {
    instance;

    @Override
    public void adjustTargets(Ability ability, Game game) {
        ability.getTargets().clear();
        int xValue = ability.getManaCostsToPay().getX();
        FilterCreaturePermanent filter = new FilterCreaturePermanent("creature with mana value X");
        filter.add(new ManaValuePredicate(ComparisonType.EQUAL_TO, xValue));
        ability.addTarget(new TargetCreaturePermanent(filter));
    }
}