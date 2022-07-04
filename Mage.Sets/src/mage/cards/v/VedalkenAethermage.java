
package mage.cards.v;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.keyword.CyclingAbility;
import mage.abilities.keyword.FlashAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.FilterPermanent;
import mage.target.TargetPermanent;

/**
 *
 * @author anonymous
 */
public final class VedalkenAethermage extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("Sliver");
    private static final FilterCard filter2 = new FilterCard("Wizard");

    static {
        filter.add(SubType.SLIVER.getPredicate());
        filter2.add(SubType.WIZARD.getPredicate());
    }
    
    public VedalkenAethermage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{U}");
        this.subtype.add(SubType.VEDALKEN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // Flash
        this.addAbility(FlashAbility.getInstance());
        // When Vedalken &Aelig;thermage enters the battlefield, return target Sliver to its owner's hand.
        Ability ability = new EntersBattlefieldTriggeredAbility(new ReturnToHandTargetEffect());
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);

        // Wizardcycling {3}
        this.addAbility(new CyclingAbility(new ManaCostsImpl<>("{3}"), filter2, "Wizardcycling"));
    }

    private VedalkenAethermage(final VedalkenAethermage card) {
        super(card);
    }

    @Override
    public VedalkenAethermage copy() {
        return new VedalkenAethermage(this);
    }
}
