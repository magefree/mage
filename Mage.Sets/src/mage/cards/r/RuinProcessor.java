
package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.costs.common.ExileOpponentsCardFromExileToGraveyardCost;
import mage.abilities.effects.common.CastSourceTriggeredAbility;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LevelX2
 */
public final class RuinProcessor extends CardImpl {

    public RuinProcessor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{7}");
        this.subtype.add(SubType.ELDRAZI);
        this.subtype.add(SubType.PROCESSOR);
        this.power = new MageInt(7);
        this.toughness = new MageInt(8);

        // When you cast Ruin Processor, you may put a card an opponent owns from exile into that player's graveyard. If you do, you gain 5 life.
        this.addAbility(new CastSourceTriggeredAbility(
                new DoIfCostPaid(new GainLifeEffect(5), new ExileOpponentsCardFromExileToGraveyardCost(true)), false));

    }

    private RuinProcessor(final RuinProcessor card) {
        super(card);
    }

    @Override
    public RuinProcessor copy() {
        return new RuinProcessor(this);
    }
}
