package mage.cards.f;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.MorbidCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.keyword.InvestigateEffect;
import mage.abilities.hint.common.MorbidHint;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FunnelWebRecluse extends CardImpl {

    public FunnelWebRecluse(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{G}");

        this.subtype.add(SubType.SPIDER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(5);

        // Reach
        this.addAbility(ReachAbility.getInstance());

        // Morbid — When Funnel-Web Recluse enters the battlefield, if a creature died this turn, investigate.
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(
                new EntersBattlefieldTriggeredAbility(new InvestigateEffect()),
                MorbidCondition.instance, "<i>Morbid</i> &mdash; When {this} enters the battlefield, " +
                "if a creature died this turn, investigate. <i>(Create a colorless Clue artifact token " +
                "with \"{2}, Sacrifice this artifact: Draw a card.\")</i>"
        ).addHint(MorbidHint.instance));
    }

    private FunnelWebRecluse(final FunnelWebRecluse card) {
        super(card);
    }

    @Override
    public FunnelWebRecluse copy() {
        return new FunnelWebRecluse(this);
    }
}
