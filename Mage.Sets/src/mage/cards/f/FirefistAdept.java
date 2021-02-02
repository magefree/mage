
package mage.cards.f;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.target.common.TargetOpponentsCreaturePermanent;

/**
 *
 * @author TheElk801
 */
public final class FirefistAdept extends CardImpl {

    private static final FilterControlledCreaturePermanent filterCount = new FilterControlledCreaturePermanent("Wizards you control");

    static {
        filterCount.add(SubType.WIZARD.getPredicate());
    }

    public FirefistAdept(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{R}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // When Firefist Adept enters the battlefield, it deals X damage to target creature an opponent controls, where X is the number of Wizards you control.
        Ability ability = new EntersBattlefieldTriggeredAbility(
                new DamageTargetEffect(new PermanentsOnBattlefieldCount(filterCount))
                        .setText("it deals X damage to target creature an opponent controls, where X is the number of Wizards you control.")
        );
        ability.addTarget(new TargetOpponentsCreaturePermanent());
        this.addAbility(ability);
    }

    private FirefistAdept(final FirefistAdept card) {
        super(card);
    }

    @Override
    public FirefistAdept copy() {
        return new FirefistAdept(this);
    }
}
