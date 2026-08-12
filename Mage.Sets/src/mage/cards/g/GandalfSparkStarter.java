package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.target.common.TargetAnyTargetAmount;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.DamageMultiEffect;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class GandalfSparkStarter extends CardImpl {

    public GandalfSparkStarter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{R}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.AVATAR);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // Reach
        this.addAbility(ReachAbility.getInstance());

        // When Gandalf enters, he deals 3 damage divided as you choose among one, two, or three targets.
        Ability ability = new EntersBattlefieldTriggeredAbility(new DamageMultiEffect("he"));
        ability.addTarget(new TargetAnyTargetAmount(3));
        this.addAbility(ability);
    }

    private GandalfSparkStarter(final GandalfSparkStarter card) {
        super(card);
    }

    @Override
    public GandalfSparkStarter copy() {
        return new GandalfSparkStarter(this);
    }
}
