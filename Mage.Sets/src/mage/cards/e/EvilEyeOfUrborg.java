
package mage.cards.e;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.BecomesBlockedByCreatureTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.combat.CantAttackAnyPlayerAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;

/**
 *
 * @author fireshoes
 */
public final class EvilEyeOfUrborg extends CardImpl {
    
    private static final FilterCreaturePermanent cantAttackFilter = new FilterCreaturePermanent("Non-Eye creatures you control");

    static {
        cantAttackFilter.add(Predicates.not((SubType.EYE.getPredicate())));
        cantAttackFilter.add(TargetController.YOU.getControllerPredicate());
    }

    public EvilEyeOfUrborg(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{B}");
        this.subtype.add(SubType.EYE);
        this.power = new MageInt(6);
        this.toughness = new MageInt(3);

        // Non-Eye creatures you control can't attack.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new CantAttackAnyPlayerAllEffect(Duration.WhileOnBattlefield, cantAttackFilter)));
        
        // Whenever Evil Eye of Urborg becomes blocked by a creature, destroy that creature.
        this.addAbility(new BecomesBlockedByCreatureTriggeredAbility(new DestroyTargetEffect(), false));
    }

    private EvilEyeOfUrborg(final EvilEyeOfUrborg card) {
        super(card);
    }

    @Override
    public EvilEyeOfUrborg copy() {
        return new EvilEyeOfUrborg(this);
    }
}
