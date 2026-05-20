package mage.cards.e;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.CreatureDiedControlledCondition;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.triggers.BeginningOfEndStepTriggeredAbility;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.game.permanent.token.PestBlackGreenAttacksToken;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class EssenceknitScholar extends CardImpl {

    public EssenceknitScholar(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{B}{B/G}{G}");

        this.subtype.add(SubType.DRYAD);
        this.subtype.add(SubType.WARLOCK);
        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // When this creature enters, create a 1/1 black and green Pest creature token with "Whenever this token attacks, you gain 1 life."
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new PestBlackGreenAttacksToken())));

        // At the beginning of your end step, if a creature died under your control this turn, draw a card.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(
            TargetController.YOU,
            new DrawCardSourceControllerEffect(1),
            false,
            CreatureDiedControlledCondition.instance
        ).addHint(CreatureDiedControlledCondition.getHint()));

    }

    private EssenceknitScholar(final EssenceknitScholar card) {
        super(card);
    }

    @Override
    public EssenceknitScholar copy() {
        return new EssenceknitScholar(this);
    }
}
