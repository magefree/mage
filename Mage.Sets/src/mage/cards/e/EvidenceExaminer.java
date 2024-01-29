package mage.cards.e;

import mage.MageInt;
import mage.abilities.common.BeginningOfCombatTriggeredAbility;
import mage.abilities.common.CollectEvidenceTriggeredAbility;
import mage.abilities.costs.common.CollectEvidenceCost;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.keyword.InvestigateEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class EvidenceExaminer extends CardImpl {

    public EvidenceExaminer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{G}{U}");

        this.subtype.add(SubType.MERFOLK);
        this.subtype.add(SubType.DETECTIVE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // At the beginning of combat on your turn, you may collect evidence 4.
        this.addAbility(new BeginningOfCombatTriggeredAbility(
                new DoIfCostPaid(null, new CollectEvidenceCost(4)),
                TargetController.YOU, false
        ));

        // Whenever you collect evidence, investigate.
        this.addAbility(new CollectEvidenceTriggeredAbility(new InvestigateEffect(), false));
    }

    private EvidenceExaminer(final EvidenceExaminer card) {
        super(card);
    }

    @Override
    public EvidenceExaminer copy() {
        return new EvidenceExaminer(this);
    }
}
