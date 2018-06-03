
package mage.cards.s;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.effects.common.CounterTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.filter.FilterSpell;
import mage.filter.predicate.mageobject.ConvertedManaCostPredicate;
import mage.game.Game;
import mage.target.Target;
import mage.target.TargetSpell;

/**
 *
 * @author LevelX2
 */
public final class SpellBlast extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("spell with converted mana cost X");

    public SpellBlast(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{X}{U}");


        // Counter target spell with converted mana cost X.
        this.getSpellAbility().addEffect(new CounterTargetEffect());
        this.getSpellAbility().addTarget(new TargetSpell(filter));
    }

    public SpellBlast(final SpellBlast card) {
        super(card);
    }

    @Override
    public SpellBlast copy() {
        return new SpellBlast(this);
    }

    @Override
    public void adjustTargets(Ability ability, Game game) {
        if (ability instanceof SpellAbility) {
            int xValue = ability.getManaCostsToPay().getX();
            ability.getTargets().clear();
            FilterSpell newfilter = new FilterSpell(new StringBuilder("spell with converted mana cost ").append(xValue).toString());
            newfilter.add(new ConvertedManaCostPredicate(ComparisonType.EQUAL_TO, xValue));
            Target target = new TargetSpell(newfilter);
            ability.addTarget(target);
        }

    }
}
