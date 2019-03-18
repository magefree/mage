
package mage.cards.f;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.target.common.TargetCardInASingleGraveyard;

/**
 *
 * @author cbt33
 */
public final class FamishedGhoul extends CardImpl {

    public FamishedGhoul(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{B}");
        this.subtype.add(SubType.ZOMBIE);

        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // {1}{B}, Sacrifice Famished Ghoul: Exile up to two target cards from a single graveyard.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new ExileTargetEffect(), new ManaCostsImpl("{1}{B}"));
        ability.addCost(new SacrificeSourceCost());
        ability.addTarget(new TargetCardInASingleGraveyard(0, 2, new FilterCard("cards from a single graveyard")));
        this.addAbility(ability);
    }

    public FamishedGhoul(final FamishedGhoul card) {
        super(card);
    }

    @Override
    public FamishedGhoul copy() {
        return new FamishedGhoul(this);
    }
}
