
package mage.cards.d;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.ConvertedManaCostPredicate;
import mage.game.Game;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LoneFox
 */
public final class Disembowel extends CardImpl {

    public Disembowel(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{X}{B}");

        // Destroy target creature with converted mana cost X.
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(new FilterCreaturePermanent("creature with converted mana cost X")));
    }

    @Override
    public void adjustTargets(Ability ability, Game game) {
        if(ability instanceof SpellAbility) {
            ability.getTargets().clear();
            int xValue = ability.getManaCostsToPay().getX();
            FilterCreaturePermanent filter = new FilterCreaturePermanent("creature with converted mana cost X");
            filter.add(new ConvertedManaCostPredicate(ComparisonType.EQUAL_TO, xValue));
            ability.addTarget(new TargetCreaturePermanent(filter));
        }
    }

    public Disembowel(final Disembowel card) {
        super(card);
    }

    @Override
    public Disembowel copy() {
        return new Disembowel(this);
    }
}
