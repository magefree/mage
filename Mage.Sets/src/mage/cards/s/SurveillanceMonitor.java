package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.CollectEvidenceTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.common.CollectEvidenceCost;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.ThopterColorlessToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SurveillanceMonitor extends CardImpl {

    public SurveillanceMonitor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}");

        this.subtype.add(SubType.VEDALKEN);
        this.subtype.add(SubType.DETECTIVE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // When Surveillance Monitor enters the battlefield, you may collect evidence 4.
        this.addAbility(new EntersBattlefieldTriggeredAbility(
                new DoIfCostPaid(null, new CollectEvidenceCost(4))
        ));

        // Whenever you collect evidence, create a 1/1 colorless Thopter artifact creature token with flying.
        this.addAbility(new CollectEvidenceTriggeredAbility(
                new CreateTokenEffect(new ThopterColorlessToken()), false
        ));
    }

    private SurveillanceMonitor(final SurveillanceMonitor card) {
        super(card);
    }

    @Override
    public SurveillanceMonitor copy() {
        return new SurveillanceMonitor(this);
    }
}
