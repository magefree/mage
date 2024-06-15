package mage.cards.t;

import mage.abilities.common.EntersBattlefieldTappedUnlessAbility;
import mage.abilities.condition.common.TwoOrMoreOpponentsCondition;
import mage.abilities.mana.BlueManaAbility;
import mage.abilities.mana.RedManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TrainingCenter extends CardImpl {

    public TrainingCenter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // Training Center enters the battlefield tapped unless you have two or more opponents.
        this.addAbility(new EntersBattlefieldTappedUnlessAbility(TwoOrMoreOpponentsCondition.instance));

        // {T}: Add {U}.
        this.addAbility(new BlueManaAbility());

        // {T}: Add {R}.
        this.addAbility(new RedManaAbility());
    }

    private TrainingCenter(final TrainingCenter card) {
        super(card);
    }

    @Override
    public TrainingCenter copy() {
        return new TrainingCenter(this);
    }
}
