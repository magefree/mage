package mage.cards.m;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.FlashAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.FilterPermanent;
import mage.target.common.TargetCreatureOrPlaneswalker;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MunitionsExpert extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent(SubType.GOBLIN, "Goblins you control");

    static {
        filter.add(TargetController.YOU.getControllerPredicate());
    }

    private static final DynamicValue xValue = new PermanentsOnBattlefieldCount(filter);

    public MunitionsExpert(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{B}{R}");

        this.subtype.add(SubType.GOBLIN);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // When Munitions Expert enters the battlefield, you may have it deal damage to target creature or planeswalker equal to the number of Goblins you control.
        Ability ability = new EntersBattlefieldTriggeredAbility(
                new DamageTargetEffect(xValue)
                        .setText("it deal damage to target creature or planeswalker " +
                                "equal to the number of Goblins you control"),
                true
        );
        ability.addTarget(new TargetCreatureOrPlaneswalker());
        this.addAbility(ability);
    }

    private MunitionsExpert(final MunitionsExpert card) {
        super(card);
    }

    @Override
    public MunitionsExpert copy() {
        return new MunitionsExpert(this);
    }
}
