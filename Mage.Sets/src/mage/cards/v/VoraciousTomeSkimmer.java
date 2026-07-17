package mage.cards.v;

import mage.MageInt;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.condition.common.OpponentsTurnCondition;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class VoraciousTomeSkimmer extends CardImpl {

    public VoraciousTomeSkimmer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{U/B}{U/B}{U/B}");

        this.subtype.add(SubType.FAERIE);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever you cast a spell during an opponent's turn, you may pay 1 life. If you do, draw a card.
        this.addAbility(new SpellCastControllerTriggeredAbility(
                new DoIfCostPaid(new DrawCardSourceControllerEffect(1), new PayLifeCost(1)), false
        ).withTriggerCondition(OpponentsTurnCondition.instance));
    }

    private VoraciousTomeSkimmer(final VoraciousTomeSkimmer card) {
        super(card);
    }

    @Override
    public VoraciousTomeSkimmer copy() {
        return new VoraciousTomeSkimmer(this);
    }
}
