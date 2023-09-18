package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.dynamicvalue.common.CardsDrawnThisTurnDynamicValue;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetAnyTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ThunderingDjinn extends CardImpl {

    public ThunderingDjinn(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}{R}");

        this.subtype.add(SubType.DJINN);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever Thundering Djinn attacks, it deals damage to any target equal to the number of cards you've drawn this turn.
        Ability ability = new AttacksTriggeredAbility(
                new DamageTargetEffect(CardsDrawnThisTurnDynamicValue.instance)
                        .setText("it deals damage to any target equal to the number of cards you've drawn this turn."),
                false
        );
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability.addHint(CardsDrawnThisTurnDynamicValue.getHint()));
    }

    private ThunderingDjinn(final ThunderingDjinn card) {
        super(card);
    }

    @Override
    public ThunderingDjinn copy() {
        return new ThunderingDjinn(this);
    }
}
