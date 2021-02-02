
package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.target.common.TargetCardInOpponentsGraveyard;

/**
 *
 * @author nickymikail
 */
public final class RuinRat extends CardImpl {

    public RuinRat(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");
        
        this.subtype.add(SubType.RAT);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Deathtouch
        this.addAbility(DeathtouchAbility.getInstance());

        // When Ruin Rat dies, exile target card from an opponent's graveyard.
        DiesSourceTriggeredAbility ability = new DiesSourceTriggeredAbility(new ExileTargetEffect());
        ability.addTarget(new TargetCardInOpponentsGraveyard(new FilterCard("card from an opponent's graveyard")));
        this.addAbility(ability);

    }

    private RuinRat(final RuinRat card) {
        super(card);
    }

    @Override
    public RuinRat copy() {
        return new RuinRat(this);
    }
}
