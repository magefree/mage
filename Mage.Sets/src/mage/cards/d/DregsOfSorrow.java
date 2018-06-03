
package mage.cards.d;

import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.Game;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class DregsOfSorrow extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("nonblack creatures");

    static {
        filter.add(Predicates.not(new ColorPredicate(ObjectColor.BLACK)));
    }

    public DregsOfSorrow(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{X}{4}{B}");


        // Destroy X target nonblack creatures. Draw X cards.
        this.getSpellAbility().addEffect(new DestroyTargetEffect("Destroy X target nonblack creatures"));
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(new ManacostVariableValue()));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(filter));
    }

    @Override
    public void adjustTargets(Ability ability, Game game) {
        if (ability instanceof SpellAbility) {
            ability.getTargets().clear();
            int xValue = ability.getManaCostsToPay().getX();
            ability.addTarget(new TargetCreaturePermanent(xValue, xValue, filter, false));
        }
    }


    public DregsOfSorrow(final DregsOfSorrow card) {
        super(card);
    }

    @Override
    public DregsOfSorrow copy() {
        return new DregsOfSorrow(this);
    }
}
