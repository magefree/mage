
package mage.cards.d;

import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.common.ReturnToHandChosenControlledPermanentCost;
import mage.abilities.effects.common.SacrificeSourceUnlessPaysEffect;
import mage.abilities.mana.BlackManaAbility;
import mage.abilities.mana.GreenManaAbility;
import mage.abilities.mana.RedManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterControlledLandPermanent;
import mage.filter.predicate.Predicates;
import mage.target.common.TargetControlledPermanent;

import java.util.UUID;

/**
 *
 * @author Plopman
 */
public final class DarigaazsCaldera extends CardImpl {

    private static final FilterControlledLandPermanent filter = new FilterControlledLandPermanent("non-Lair land");
    static{
        filter.add(Predicates.not(SubType.LAIR.getPredicate()));
    }
    
    public DarigaazsCaldera(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},"");
        this.subtype.add(SubType.LAIR);

        // When Darigaaz's Caldera enters the battlefield, sacrifice it unless you return a non-Lair land you control to its owner's hand.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new SacrificeSourceUnlessPaysEffect(new ReturnToHandChosenControlledPermanentCost(new TargetControlledPermanent(filter)))));
        // {tap}: Add {B}, {R}, or {G}.
        this.addAbility(new BlackManaAbility());
        this.addAbility(new RedManaAbility());
        this.addAbility(new GreenManaAbility());
    }

    private DarigaazsCaldera(final DarigaazsCaldera card) {
        super(card);
    }

    @Override
    public DarigaazsCaldera copy() {
        return new DarigaazsCaldera(this);
    }
}
