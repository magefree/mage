
package mage.cards.j;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.LeavesBattlefieldTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.keyword.MeditateAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetEnchantmentPermanent;

/**
 *
 * @author Styxo
 */
public final class JediInquirer extends CardImpl {

    public JediInquirer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.JEDI);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When Jedi Inquirer leaves the battlefield, you may exile target enchantment.
        LeavesBattlefieldTriggeredAbility ability = new LeavesBattlefieldTriggeredAbility(new ExileTargetEffect(), true);
        ability.addTarget(new TargetEnchantmentPermanent());
        this.addAbility(ability);

        // Meditate {1}{W}
        this.addAbility(new MeditateAbility(new ManaCostsImpl<>("{1}{W}")));
    }

    private JediInquirer(final JediInquirer card) {
        super(card);
    }

    @Override
    public JediInquirer copy() {
        return new JediInquirer(this);
    }
}
