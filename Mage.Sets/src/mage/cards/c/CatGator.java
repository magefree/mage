package mage.cards.c;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterControlledPermanent;
import mage.target.common.TargetAnyTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CatGator extends CardImpl {

    private static final DynamicValue xValue = new PermanentsOnBattlefieldCount(
            new FilterControlledPermanent(SubType.SWAMP, "Swamps you control")
    );
    private static final Hint hint = new ValueHint("Swamps you control", xValue);

    public CatGator(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{6}{B}");

        this.subtype.add(SubType.FISH);
        this.subtype.add(SubType.CROCODILE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());

        // When this creature enters, it deals damage equal to the number of Swamps you control to any target.
        Ability ability = new EntersBattlefieldTriggeredAbility(new DamageTargetEffect(xValue)
                .setText("it deals damage equal to the number of Swamps you control to any target"));
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability.addHint(hint));
    }

    private CatGator(final CatGator card) {
        super(card);
    }

    @Override
    public CatGator copy() {
        return new CatGator(this);
    }
}
