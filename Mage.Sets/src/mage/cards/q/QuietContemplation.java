
package mage.cards.q;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.DontUntapInControllersNextUntapStepTargetEffect;
import mage.abilities.effects.common.TapTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.filter.FilterSpell;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class QuietContemplation extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creature an opponent controls");
    private static final FilterSpell filterNonCreature = new FilterSpell("a noncreature spell");

    static {
        filterNonCreature.add(Predicates.not(new CardTypePredicate(CardType.CREATURE)));
        filter.add(new ControllerPredicate(TargetController.OPPONENT));
    }
       
    public QuietContemplation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{2}{U}");


        // Whenever you cast a noncreature spell, you may pay {1}. If you do, tap target creature an opponent controls and it doesn't untap during its controller's next untap step.
        DoIfCostPaid doIfCostPaid = new DoIfCostPaid(new TapTargetEffect(), new GenericManaCost(1),"Tap creature?");        
        Effect effect = new DontUntapInControllersNextUntapStepTargetEffect();
        effect.setText("and it doesn't untap during its controller's next untap step");
        doIfCostPaid.addEffect(effect);
        Ability ability = new SpellCastControllerTriggeredAbility(doIfCostPaid, filterNonCreature, false);
        ability.addTarget(new TargetCreaturePermanent(filter));
        this.addAbility(ability);        
    }

    public QuietContemplation(final QuietContemplation card) {
        super(card);
    }

    @Override
    public QuietContemplation copy() {
        return new QuietContemplation(this);
    }
}
