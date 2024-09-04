
package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterControlledPermanent;
import mage.target.common.TargetAnyTarget;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class SpawnOfThraxes extends CardImpl {

    private static final DynamicValue xValue = new PermanentsOnBattlefieldCount(new FilterControlledPermanent(SubType.MOUNTAIN));
    private static final Hint hint = new ValueHint("Mountains you control", xValue);

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("Mountains you control");

    static {
        filter.add(SubType.MOUNTAIN.getPredicate());
    }

    public SpawnOfThraxes(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{R}{R}");
        this.subtype.add(SubType.DRAGON);

        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // When Spawn of Thraxes enters the battlefield, it deals damage to any target equal to the number of Mountains you control.
        Ability ability = new EntersBattlefieldTriggeredAbility(new DamageTargetEffect(new PermanentsOnBattlefieldCount(filter))
                .setText("it deals damage to any target equal to the number of Mountains you control"));
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);
        this.getSpellAbility().addHint(hint);
    }

    private SpawnOfThraxes(final SpawnOfThraxes card) {
        super(card);
    }

    @Override
    public SpawnOfThraxes copy() {
        return new SpawnOfThraxes(this);
    }
}
