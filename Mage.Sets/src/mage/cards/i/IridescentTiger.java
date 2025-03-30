package mage.cards.i;

import mage.MageInt;
import mage.Mana;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.CastFromEverywhereSourceCondition;
import mage.abilities.effects.mana.BasicManaEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class IridescentTiger extends CardImpl {

    public IridescentTiger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{R}");

        this.subtype.add(SubType.CAT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // When this creature enters, if you cast it, add {W}{U}{B}{R}{G}.
        this.addAbility(new EntersBattlefieldTriggeredAbility(
                new BasicManaEffect(new Mana(1, 1, 1, 1, 1, 0, 0, 0))
        ).withInterveningIf(CastFromEverywhereSourceCondition.instance));
    }

    private IridescentTiger(final IridescentTiger card) {
        super(card);
    }

    @Override
    public IridescentTiger copy() {
        return new IridescentTiger(this);
    }
}
