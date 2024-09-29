package mage.cards.m;

import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.ruleModifying.CantCastOrActivateOpponentsYourTurnEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.common.FilterControlledPermanent;
import mage.game.permanent.token.SoldierArtifactToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MyrelShieldOfArgive extends CardImpl {

    private static final DynamicValue xValue = new PermanentsOnBattlefieldCount(
            new FilterControlledPermanent(SubType.SOLDIER, "Soldiers you control"), null
    );
    private static final Hint hint = new ValueHint("Soldiers you control", xValue);

    public MyrelShieldOfArgive(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // During your turn, your opponents can't cast spells or activate abilities of artifacts, creatures, or enchantments.
        this.addAbility(new SimpleStaticAbility(new CantCastOrActivateOpponentsYourTurnEffect()));

        // Whenever Myrel, Shield of Argive attacks, create X 1/1 colorless Soldier artifact creature tokens, where X is the number of Soldiers you control.
        this.addAbility(new AttacksTriggeredAbility(
                new CreateTokenEffect(new SoldierArtifactToken(), xValue)
        ).addHint(hint));
    }

    private MyrelShieldOfArgive(final MyrelShieldOfArgive card) {
        super(card);
    }

    @Override
    public MyrelShieldOfArgive copy() {
        return new MyrelShieldOfArgive(this);
    }
}
