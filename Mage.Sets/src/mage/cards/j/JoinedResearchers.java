package mage.cards.j;

import mage.MageInt;
import mage.abilities.condition.common.OpponentHasMoreCardsInHandCondition;
import mage.abilities.effects.common.BecomePreparedSourceEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.DrawCardTargetEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.triggers.BeginningOfEndStepTriggeredAbility;
import mage.cards.CardSetInfo;
import mage.cards.PrepareCard;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class JoinedResearchers extends PrepareCard {

    public JoinedResearchers(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}", "Secret Rendezvous", new CardType[]{CardType.SORCERY}, "{1}{W}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLERIC);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // First strike
        this.addAbility(FirstStrikeAbility.getInstance());

        // At the beginning of each end step, if an opponent has more cards in hand than you, this creature becomes prepared.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(new BecomePreparedSourceEffect())
                .withInterveningIf(OpponentHasMoreCardsInHandCondition.instance)
                .addHint(OpponentHasMoreCardsInHandCondition.getHint()));

        // Secret Rendezvous
        // Sorcery {1}{W}{W}
        // You and target opponent each draw three cards.
        this.getSpellCard().getSpellAbility().addEffect(new DrawCardSourceControllerEffect(3).setText("you"));
        this.getSpellCard().getSpellAbility().addEffect(new DrawCardTargetEffect(3).setText("and target opponent each draw three cards"));
        this.getSpellCard().getSpellAbility().addTarget(new TargetOpponent());
    }

    private JoinedResearchers(final JoinedResearchers card) {
        super(card);
    }

    @Override
    public JoinedResearchers copy() {
        return new JoinedResearchers(this);
    }
}
