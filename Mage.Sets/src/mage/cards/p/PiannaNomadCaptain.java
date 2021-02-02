
package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.SuperType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.AttackingPredicate;

/**
 *
 * @author cbt33
 */
public final class PiannaNomadCaptain extends CardImpl {
    
    static final FilterCreaturePermanent filter = new FilterCreaturePermanent("attacking creatures");
    
    static {
        filter.add(AttackingPredicate.instance);
    }

    public PiannaNomadCaptain(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{W}{W}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.NOMAD);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever Pianna, Nomad Captain attacks, attacking creatures get +1/+1 until end of turn.
        this.addAbility(new AttacksTriggeredAbility(new BoostAllEffect(1, 1, Duration.EndOfTurn, filter, false), false));
    }

    private PiannaNomadCaptain(final PiannaNomadCaptain card) {
        super(card);
    }

    @Override
    public PiannaNomadCaptain copy() {
        return new PiannaNomadCaptain(this);
    }
}
