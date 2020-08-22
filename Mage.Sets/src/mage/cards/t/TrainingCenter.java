package mage.cards.t;

import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.condition.common.OneOpponentCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.TapSourceEffect;
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
        this.addAbility(new EntersBattlefieldAbility(
                new ConditionalOneShotEffect(
                        new TapSourceEffect(),
                        OneOpponentCondition.instance,
                        "tapped unless you have two or more opponents"
                ), "tapped unless you have two or more opponents"
        ));

        // {T}: Add {U} or {R}.
        this.addAbility(new BlueManaAbility());
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
