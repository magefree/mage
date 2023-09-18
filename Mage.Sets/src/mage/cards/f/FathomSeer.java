
package mage.cards.f;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.TurnedFaceUpSourceTriggeredAbility;
import mage.abilities.costs.common.ReturnToHandChosenControlledPermanentCost;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.keyword.MorphAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterControlledPermanent;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author LevelX2
 */
public final class FathomSeer extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("Islands");

    static {
        filter.add(SubType.ISLAND.getPredicate());
    }
    public FathomSeer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{U}");
        this.subtype.add(SubType.ILLUSION);

        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Morph-Return two Islands you control to their owner's hand.
        this.addAbility(new MorphAbility(new ReturnToHandChosenControlledPermanentCost(new TargetControlledPermanent(2,2, filter, true))));
        // When Fathom Seer is turned face up, draw two cards.
        this.addAbility(new TurnedFaceUpSourceTriggeredAbility(new DrawCardSourceControllerEffect(2)));
    }

    private FathomSeer(final FathomSeer card) {
        super(card);
    }

    @Override
    public FathomSeer copy() {
        return new FathomSeer(this);
    }
}
