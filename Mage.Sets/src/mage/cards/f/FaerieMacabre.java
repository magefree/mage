
package mage.cards.f;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.DiscardSourceCost;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.target.common.TargetCardInGraveyard;

/**
 *
 * @author Plopman
 */
public final class FaerieMacabre extends CardImpl {

    public FaerieMacabre(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}{B}");
        this.subtype.add(SubType.FAERIE);
        this.subtype.add(SubType.ROGUE);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // Discard Faerie Macabre: Exile up to two target cards from graveyards.
        Ability ability = new SimpleActivatedAbility(Zone.HAND, new ExileTargetEffect(), new DiscardSourceCost());
        ability.addTarget(new TargetCardInGraveyard(0, 2, new FilterCard("cards from graveyards")));
        this.addAbility(ability);
    }

    private FaerieMacabre(final FaerieMacabre card) {
        super(card);
    }

    @Override
    public FaerieMacabre copy() {
        return new FaerieMacabre(this);
    }
}
