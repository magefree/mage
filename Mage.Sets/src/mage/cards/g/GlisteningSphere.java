package mage.cards.g;

import java.util.UUID;

import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.CorruptedCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.counter.ProliferateEffect;
import mage.abilities.effects.mana.AddManaOfAnyColorEffect;
import mage.abilities.mana.ActivateIfConditionManaAbility;
import mage.abilities.mana.AnyColorManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.Zone;

/**
 * @author TheElk801
 */
public final class GlisteningSphere extends CardImpl {

    public GlisteningSphere(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        // Glistening Sphere enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());

        // When Glistening Sphere enters the battlefield, proliferate.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new ProliferateEffect(false)));

        // {T}: Add one mana of any color.
        this.addAbility(new AnyColorManaAbility());

        // Corrupted -- {T}: Add three mana of any one color. Activate only if an opponent has three or more poison counters.
        this.addAbility(new ActivateIfConditionManaAbility(
                Zone.BATTLEFIELD, new AddManaOfAnyColorEffect(3),
                new TapSourceCost(), CorruptedCondition.instance
        ).setAbilityWord(AbilityWord.CORRUPTED).addHint(CorruptedCondition.getHint()));
    }

    private GlisteningSphere(final GlisteningSphere card) {
        super(card);
    }

    @Override
    public GlisteningSphere copy() {
        return new GlisteningSphere(this);
    }
}
