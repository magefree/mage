
package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterPermanentCard;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author fireshoes
 */
public final class RamosianRevivalist extends CardImpl {
    
    private static final FilterPermanentCard filter = new FilterPermanentCard("Rebel permanent card with mana value 5 or less from your graveyard");

    static {
        filter.add(SubType.REBEL.getPredicate());
        filter.add(new ManaValuePredicate(ComparisonType.FEWER_THAN, 6));
    }

    public RamosianRevivalist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.REBEL);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // {6}, {tap}: Return target Rebel permanent card with converted mana cost 5 or less from your graveyard to the battlefield.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new ReturnFromGraveyardToBattlefieldTargetEffect(), new GenericManaCost(6));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetCardInYourGraveyard(filter));
        this.addAbility(ability);
    }

    private RamosianRevivalist(final RamosianRevivalist card) {
        super(card);
    }

    @Override
    public RamosianRevivalist copy() {
        return new RamosianRevivalist(this);
    }
}
