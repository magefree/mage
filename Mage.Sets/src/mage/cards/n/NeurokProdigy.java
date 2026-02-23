

package mage.cards.n;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.DiscardTargetCost;
import mage.abilities.effects.common.ReturnToHandSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInHand;

/**
 * @author Loki
 */
public final class NeurokProdigy extends CardImpl {

    public NeurokProdigy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{U}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);

        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Discard an artifact card: Return Neurok Prodigy to its owner's hand.
        this.addAbility(new SimpleActivatedAbility(new ReturnToHandSourceEffect(true), new DiscardTargetCost(new TargetCardInHand(StaticFilters.FILTER_CARD_ARTIFACT_AN))));
    }

    private NeurokProdigy(final NeurokProdigy card) {
        super(card);
    }

    @Override
    public NeurokProdigy copy() {
        return new NeurokProdigy(this);
    }

}
