package mage.cards.i;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.ChangelingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.ShapeshifterToken;

/**
 * @author TheElk801
 */
public final class IrregularCohort extends CardImpl {

    public IrregularCohort(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}{W}");

        this.subtype.add(SubType.SHAPESHIFTER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Changeling
        this.addAbility(new ChangelingAbility());

        // When Irregular Cohort enters the battlefield, create a 2/2 colorless Shapeshifter creature token with changeling.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new ShapeshifterToken())));
    }

    private IrregularCohort(final IrregularCohort card) {
        super(card);
    }

    @Override
    public IrregularCohort copy() {
        return new IrregularCohort(this);
    }
}
