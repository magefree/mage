
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.mana.SimpleManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;

/**
 *
 * @author fireshoes
 */
public final class ApprenticeWizard extends CardImpl {

    public ApprenticeWizard(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{U}{U}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(0);
        this.toughness = new MageInt(1);

        // {U}, {tap}: Add {C}{C}{C}.
        Ability ability = new SimpleManaAbility(Zone.BATTLEFIELD, Mana.ColorlessMana(3), new ManaCostsImpl<>("{U}"));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    private ApprenticeWizard(final ApprenticeWizard card) {
        super(card);
    }

    @Override
    public ApprenticeWizard copy() {
        return new ApprenticeWizard(this);
    }
}