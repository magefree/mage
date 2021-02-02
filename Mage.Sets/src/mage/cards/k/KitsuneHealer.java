

package mage.cards.k;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.PreventDamageToTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.target.common.TargetAnyTarget;
import mage.target.common.TargetCreaturePermanent;

/**
 * @author Loki
 */
public final class KitsuneHealer extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("legendary creature");

    static {
        filter.add(SuperType.LEGENDARY.getPredicate());
    }

    public KitsuneHealer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{W}");
        this.subtype.add(SubType.FOX);
        this.subtype.add(SubType.CLERIC);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);
        
        // {T}: Prevent the next 1 damage that would be dealt to any target this turn.        
        Ability firstAbility = new SimpleActivatedAbility(Zone.BATTLEFIELD, new PreventDamageToTargetEffect(Duration.EndOfTurn, 1), new TapSourceCost());
        firstAbility.addTarget(new TargetAnyTarget());
        this.addAbility(firstAbility);
        // {T}: Prevent all damage that would be dealt to target legendary creature this turn.
        Ability secondAbility = new SimpleActivatedAbility(Zone.BATTLEFIELD, new PreventDamageToTargetEffect(Duration.EndOfTurn, Integer.MAX_VALUE), new TapSourceCost());
        secondAbility.addTarget(new TargetCreaturePermanent(filter));
        this.addAbility(secondAbility);
    }

    private KitsuneHealer(final KitsuneHealer card) {
        super(card);
    }

    @Override
    public KitsuneHealer copy() {
        return new KitsuneHealer(this);
    }

}
