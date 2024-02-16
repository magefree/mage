
package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.BeginningOfCombatTriggeredAbility;
import mage.abilities.effects.common.combat.AttackIfAbleTargetRandomOpponentSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.TargetController;

/**
 *
 * @author LevelX2
 */
public final class RuhanOfTheFomori extends CardImpl {

    public RuhanOfTheFomori(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}{R}{W}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.GIANT);
        this.subtype.add(SubType.WARRIOR);

        this.power = new MageInt(7);
        this.toughness = new MageInt(7);

        // At the beginning of combat on your turn, choose an opponent at random. Ruhan of the Fomori attacks that player this combat if able.
        this.addAbility(new BeginningOfCombatTriggeredAbility(new AttackIfAbleTargetRandomOpponentSourceEffect(), TargetController.YOU, false));
    }

    private RuhanOfTheFomori(final RuhanOfTheFomori card) {
        super(card);
    }

    @Override
    public RuhanOfTheFomori copy() {
        return new RuhanOfTheFomori(this);
    }
}
