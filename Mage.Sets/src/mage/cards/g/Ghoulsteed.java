
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.DiscardTargetCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.ReturnSourceFromGraveyardToBattlefieldEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInHand;

/**
 *
 * @author fireshoes
 */
public final class Ghoulsteed extends CardImpl {

    public Ghoulsteed(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{B}");
        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.HORSE);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // {2}{B}, Discard two cards: Return Ghouldsteed from your graveyard to the battlefield tapped.
        Ability ability = new SimpleActivatedAbility(Zone.GRAVEYARD, new ReturnSourceFromGraveyardToBattlefieldEffect(true), new ManaCostsImpl<>("{2}{B}"));
        ability.addCost(new DiscardTargetCost(new TargetCardInHand(2, StaticFilters.FILTER_CARD_CARDS)));
        this.addAbility(ability);
    }

    private Ghoulsteed(final Ghoulsteed card) {
        super(card);
    }

    @Override
    public Ghoulsteed copy() {
        return new Ghoulsteed(this);
    }
}
