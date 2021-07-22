
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapTargetCost;
import mage.abilities.effects.common.PreventDamageByTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.target.TargetSpell;
import mage.target.common.TargetControlledPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author L_J
 */
public final class ShieldmageElder extends CardImpl {

    private static final FilterControlledPermanent filter1 = new FilterControlledPermanent("untapped Clerics you control");

    static {
        filter1.add(TappedPredicate.UNTAPPED);
        filter1.add(SubType.CLERIC.getPredicate());
    }
    private static final FilterControlledPermanent filter2 = new FilterControlledPermanent("untapped Wizards you control");

    static {
        filter2.add(TappedPredicate.UNTAPPED);
        filter2.add(SubType.WIZARD.getPredicate());
    }

    public ShieldmageElder(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{5}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLERIC);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Tap two untapped Clerics you control: Prevent all damage target creature would deal this turn.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new PreventDamageByTargetEffect(Duration.EndOfTurn), new TapTargetCost(new TargetControlledPermanent(2, 2, filter1, false)));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);

        // Tap two untapped Wizards you control: Prevent all damage target spell would deal this turn.
        Ability ability2 = new SimpleActivatedAbility(Zone.BATTLEFIELD, new PreventDamageByTargetEffect(Duration.EndOfTurn), new TapTargetCost(new TargetControlledPermanent(2, 2, filter2, false)));
        ability2.addTarget(new TargetSpell());
        this.addAbility(ability2);
    }

    private ShieldmageElder(final ShieldmageElder card) {
        super(card);
    }

    @Override
    public ShieldmageElder copy() {
        return new ShieldmageElder(this);
    }
}
