
package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.effects.common.CounterTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.filter.FilterSpell;
import mage.filter.predicate.mageobject.ConvertedManaCostPredicate;
import mage.game.Game;
import mage.target.TargetSpell;
import mage.target.targetadjustment.TargetAdjuster;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class SpellBlast extends CardImpl {

    public SpellBlast(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{X}{U}");

        // Counter target spell with converted mana cost X.
        this.getSpellAbility().addEffect(new CounterTargetEffect().setText("counter target spell with converted mana cost X"));
        this.getSpellAbility().setTargetAdjuster(SpellBlastAdjuster.instance);
    }

    public SpellBlast(final SpellBlast card) {
        super(card);
    }

    @Override
    public SpellBlast copy() {
        return new SpellBlast(this);
    }
}

enum SpellBlastAdjuster implements TargetAdjuster {
    instance;

    @Override
    public void adjustTargets(Ability ability, Game game) {
        int xValue = ability.getManaCostsToPay().getX();
        ability.getTargets().clear();
        FilterSpell filter = new FilterSpell("spell with converted mana cost " + xValue);
        filter.add(new ConvertedManaCostPredicate(ComparisonType.EQUAL_TO, xValue));
        ability.addTarget(new TargetSpell(filter));
    }
}