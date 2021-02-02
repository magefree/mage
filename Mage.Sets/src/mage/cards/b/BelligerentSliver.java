
package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterCreaturePermanent;

/**
 *
 * @author Quercitron
 */
public final class BelligerentSliver extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Sliver creatures you control");
    
    static  {
        filter.add(TargetController.YOU.getControllerPredicate());
        filter.add(SubType.SLIVER.getPredicate());
    }
    
    public BelligerentSliver(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{R}");
        this.subtype.add(SubType.SLIVER);

        this.color.setRed(true);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Sliver creatures you control have menace. (They can't be blocked except by two or more creatures.)"
        Effect effect = new GainAbilityAllEffect(new MenaceAbility(), Duration.WhileOnBattlefield, filter);
        effect.setText("Sliver creatures you control have menace. (They can't be blocked except by two or more creatures.)");
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, effect));
    }

    private BelligerentSliver(final BelligerentSliver card) {
        super(card);
    }

    @Override
    public BelligerentSliver copy() {
        return new BelligerentSliver(this);
    }
}
