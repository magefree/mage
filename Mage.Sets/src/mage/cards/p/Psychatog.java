
package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.costs.common.ExileFromGraveCost;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author LevelX2
 */
public final class Psychatog extends CardImpl {

    public Psychatog(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{U}{B}");
        this.subtype.add(SubType.ATOG);

        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // Discard a card: Psychatog gets +1/+1 until end of turn.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new BoostSourceEffect(1,1,Duration.EndOfTurn), new DiscardCardCost()));
        
        // Exile two cards from your graveyard: Psychatog gets +1/+1 until end of turn.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new BoostSourceEffect(1,1,Duration.EndOfTurn), new ExileFromGraveCost(new TargetCardInYourGraveyard(2, StaticFilters.FILTER_CARDS_FROM_YOUR_GRAVEYARD))));

    }

    private Psychatog(final Psychatog card) {
        super(card);
    }

    @Override
    public Psychatog copy() {
        return new Psychatog(this);
    }
}
