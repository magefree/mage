
package mage.cards.t;

import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.common.ReturnToHandChosenControlledPermanentCost;
import mage.abilities.effects.common.SacrificeSourceUnlessPaysEffect;
import mage.abilities.mana.BlueManaAbility;
import mage.abilities.mana.GreenManaAbility;
import mage.abilities.mana.WhiteManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterControlledLandPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.target.common.TargetControlledPermanent;

import java.util.UUID;

/**
 *
 * @author Plopman
 */
public final class TrevasRuins extends CardImpl {

    private static final FilterControlledLandPermanent filter = new FilterControlledLandPermanent("non-Lair land");
    static{
        filter.add(Predicates.not(new SubtypePredicate(SubType.LAIR)));
    }
    
    public TrevasRuins(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},"");
        this.subtype.add(SubType.LAIR);

        // When Treva's Ruins enters the battlefield, sacrifice it unless you return a non-Lair land you control to its owner's hand.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new SacrificeSourceUnlessPaysEffect(new ReturnToHandChosenControlledPermanentCost(new TargetControlledPermanent(filter)))));
        // {tap}: Add {G}, {W}, or {U}.
        this.addAbility(new GreenManaAbility());
        this.addAbility(new WhiteManaAbility());
        this.addAbility(new BlueManaAbility());
    }

    public TrevasRuins(final TrevasRuins card) {
        super(card);
    }

    @Override
    public TrevasRuins copy() {
        return new TrevasRuins(this);
    }
}
