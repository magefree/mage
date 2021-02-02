
package mage.cards.i;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.LookLibraryControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;

/**
 *
 * @author LoneFox
 */
public final class InformationDealer extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("Wizards on the battlefield");

    static {
        filter.add(SubType.WIZARD.getPredicate());
    }

    public InformationDealer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{U}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {tap}: Look at the top X cards of your library, where X is the number of Wizards on the battlefield, then put them back in any order.
        Effect effect = new LookLibraryControllerEffect(new PermanentsOnBattlefieldCount(filter));
        effect.setText("Look at the top X cards of your library, where X is the number of Wizards on the battlefield, then put them back in any order.");
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, effect, new TapSourceCost()));
    }

    private InformationDealer(final InformationDealer card) {
        super(card);
    }

    @Override
    public InformationDealer copy() {
        return new InformationDealer(this);
    }
}
