
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.combat.CantAttackIfDefenderControlsPermanent;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.ComparisonType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.PowerPredicate;
import mage.filter.predicate.permanent.TappedPredicate;

/**
 * @author BursegSardaukar
 */
public final class MoggJailer extends CardImpl {

    static final private FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("untapped creature with power 2 or less");

    static {
        filter.add(Predicates.and(new PowerPredicate(ComparisonType.FEWER_THAN, 2), TappedPredicate.UNTAPPED));
    }

    public MoggJailer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");
        this.subtype.add(SubType.GOBLIN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Mogg Jailer can't attack if defending player controls an untapped creature with power 2 or less.
        Effect effect = new CantAttackIfDefenderControlsPermanent(filter);
        effect.setText("Mogg Jailer can't attack if defending player controls an untapped creature with power 2 or less.");
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, effect));
    }

    private MoggJailer(final MoggJailer card) {
        super(card);
    }

    @Override
    public MoggJailer copy() {
        return new MoggJailer(this);
    }
}
