
package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SourceBecomesTargetTriggeredAbility;
import mage.abilities.costs.common.DiscardTargetCost;
import mage.abilities.effects.common.SacrificeSourceUnlessPaysEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterLandCard;
import mage.target.common.TargetCardInHand;

/**
 *
 * @author cbt33
 */
public final class CursedMonstrosity extends CardImpl {

    public CursedMonstrosity(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{B}");
        this.subtype.add(SubType.HORROR);

        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // Whenever Cursed Monstrosity becomes the target of a spell or ability, sacrifice it unless you discard a land card.
        this.addAbility(new SourceBecomesTargetTriggeredAbility(
                            new SacrificeSourceUnlessPaysEffect(
                            new DiscardTargetCost(new TargetCardInHand(new FilterLandCard()))
                )));
    }

    private CursedMonstrosity(final CursedMonstrosity card) {
        super(card);
    }

    @Override
    public CursedMonstrosity copy() {
        return new CursedMonstrosity(this);
    }
}
