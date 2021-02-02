
package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.common.FilterCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class BloodChinRager extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent(SubType.WARRIOR,"Warrior creatures you control");

    static {
        filter.add(TargetController.YOU.getControllerPredicate());
    }

    public BloodChinRager(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{B}");
        this.subtype.add(SubType.HUMAN, SubType.WARRIOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever Blood-Chin Rager attacks, Warrior creatures you control gain menace until end of turn. (They can't be blocked except by two or more creatures.)
        this.addAbility(new AttacksTriggeredAbility(new GainAbilityAllEffect(new MenaceAbility(), Duration.EndOfTurn, filter), false));
    }

    private BloodChinRager(final BloodChinRager card) {
        super(card);
    }

    @Override
    public BloodChinRager copy() {
        return new BloodChinRager(this);
    }
}
