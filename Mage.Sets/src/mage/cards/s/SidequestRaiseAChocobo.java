package mage.cards.s;

import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.abilities.keyword.TransformAbility;
import mage.abilities.triggers.BeginningOfFirstMainTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.SubType;
import mage.filter.common.FilterControlledPermanent;
import mage.game.permanent.token.ChocoboToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SidequestRaiseAChocobo extends CardImpl {

    private static final Condition condition = new PermanentsOnTheBattlefieldCondition(
            new FilterControlledPermanent(SubType.BIRD, "you control four or more Birds"),
            ComparisonType.MORE_THAN, 3
    );
    private static final Hint hint = new ValueHint(
            "Birds you control", new PermanentsOnBattlefieldCount(new FilterControlledPermanent(SubType.BIRD))
    );

    public SidequestRaiseAChocobo(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{G}");

        this.secondSideCardClazz = mage.cards.b.BlackChocobo.class;

        // When this enchantment enters, create a 2/2 green Bird creature token with "Whenever a land you control enters, this token gets +1/+0 until end of turn."
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new ChocoboToken())));

        // At the beginning of your first main phase, if you control four or more Birds, transform this enchantment.
        this.addAbility(new TransformAbility());
        this.addAbility(new BeginningOfFirstMainTriggeredAbility(new TransformSourceEffect())
                .withInterveningIf(condition).addHint(hint));
    }

    private SidequestRaiseAChocobo(final SidequestRaiseAChocobo card) {
        super(card);
    }

    @Override
    public SidequestRaiseAChocobo copy() {
        return new SidequestRaiseAChocobo(this);
    }
}
