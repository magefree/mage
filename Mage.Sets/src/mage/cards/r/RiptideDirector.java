
package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterControlledPermanent;

/**
 *
 * @author fireshoes
 */
public final class RiptideDirector extends CardImpl {
    
    private static final FilterControlledPermanent filter = new FilterControlledCreaturePermanent("Wizard you control");

    static {
        filter.add(SubType.WIZARD.getPredicate());
    }

    public RiptideDirector(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{U}{U}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // {2}{U}{U}, {tap}: Draw a card for each Wizard you control.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD,
                new DrawCardSourceControllerEffect(new PermanentsOnBattlefieldCount(filter)),
                new ManaCostsImpl<>("{2}{U}{U}"));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    private RiptideDirector(final RiptideDirector card) {
        super(card);
    }

    @Override
    public RiptideDirector copy() {
        return new RiptideDirector(this);
    }
}
