
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.combat.CantAttackAnyPlayerAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.NamePredicate;

/**
 *
 * @author jeffwadsworth
 */
public final class AkronLegionnaire extends CardImpl {
    
    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent();
    
    static {
        filter.add(TargetController.YOU.getControllerPredicate());
        filter.add(Predicates.not(new NamePredicate("Akron Legionnaire")));
        filter.add(Predicates.not(CardType.ARTIFACT.getPredicate()));
    }

    public AkronLegionnaire(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{6}{W}{W}");
        this.subtype.add(SubType.GIANT);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(8);
        this.toughness = new MageInt(4);

        // Except for creatures named Akron Legionnaire and artifact creatures, creatures you control can't attack.
        Effect effect = new CantAttackAnyPlayerAllEffect(Duration.WhileOnBattlefield, filter);
        effect.setText("Except for creatures named Akron Legionnaire and artifact creatures, creatures you control can't attack");
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, effect));
        
    }

    private AkronLegionnaire(final AkronLegionnaire card) {
        super(card);
    }

    @Override
    public AkronLegionnaire copy() {
        return new AkronLegionnaire(this);
    }
}
