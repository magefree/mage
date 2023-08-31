package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.BargainedCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.abilities.hint.common.BargainCostWasPaidHint;
import mage.abilities.keyword.BargainAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TenaciousTomeseeker extends CardImpl {

    public TenaciousTomeseeker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Bargain
        this.addAbility(new BargainAbility());

        // When Tenacious Tomeseeker enters the battlefield, if it was bargained, return target instant or sorcery card from your graveyard to your hand.
        Ability ability = new ConditionalInterveningIfTriggeredAbility(
                new EntersBattlefieldTriggeredAbility(new ReturnFromGraveyardToHandTargetEffect()),
                BargainedCondition.instance, "When {this} enters the battlefield, if it was bargained, " +
                "return target instant or sorcery card from your graveyard to your hand."
        );
        ability.addTarget(new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_INSTANT_OR_SORCERY_FROM_YOUR_GRAVEYARD));
        this.addAbility(ability.addHint(BargainCostWasPaidHint.instance));
    }

    private TenaciousTomeseeker(final TenaciousTomeseeker card) {
        super(card);
    }

    @Override
    public TenaciousTomeseeker copy() {
        return new TenaciousTomeseeker(this);
    }
}
