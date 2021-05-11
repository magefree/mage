
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.combat.CantAttackIfDefenderControlsPermanent;
import mage.abilities.effects.common.combat.CantBlockCreaturesSourceEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.ComparisonType;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.PowerPredicate;
import mage.filter.predicate.permanent.TappedPredicate;

/**
 *
 * @author BursegSardaukar
 *
 */
public final class GoblinMutant extends CardImpl {

    static final private FilterCreaturePermanent filter = new FilterCreaturePermanent("untapped creature with power 3 or greater");
    static final private FilterCreaturePermanent filter2 = new FilterCreaturePermanent("creatures with power 3 or greater");

    static {
        filter.add(Predicates.and(new PowerPredicate(ComparisonType.MORE_THAN, 2), TappedPredicate.UNTAPPED));
        filter2.add(new PowerPredicate(ComparisonType.MORE_THAN, 2));
    }

    public GoblinMutant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{R}{R}");
        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.MUTANT);

        this.power = new MageInt(5);
        this.toughness = new MageInt(3);

        //Trample
        this.addAbility(TrampleAbility.getInstance());

        // Goblin Mutant can't attack if defending player controls an untapped creature with power 3 or greater.
        Effect effect = new CantAttackIfDefenderControlsPermanent(filter);
        effect.setText("{this} can't attack if defending player controls an untapped creature with power 3 or greater.");
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, effect));

        //Goblin Mutant can't block creatures with power 3 or greater.
        Effect effectBlock = new CantBlockCreaturesSourceEffect(filter2);
        effectBlock.setText("{this} can't block creatures with power 3 or greater.");
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, effectBlock));
    }

    private GoblinMutant(final GoblinMutant card) {
        super(card);
    }

    @Override
    public GoblinMutant copy() {
        return new GoblinMutant(this);
    }
}
