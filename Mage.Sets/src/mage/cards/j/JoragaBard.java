
package mage.cards.j;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.AllyEntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.filter.predicate.permanent.ControllerPredicate;

/**
 *
 * @author North
 */
public final class JoragaBard extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Ally creatures you control");

    static {
        filter.add(new SubtypePredicate(SubType.ALLY));
        filter.add(new ControllerPredicate(TargetController.YOU));
    }

    public JoragaBard(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{G}");
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.ROGUE);
        this.subtype.add(SubType.ALLY);

        this.power = new MageInt(1);
        this.toughness = new MageInt(4);

        this.addAbility(new AllyEntersBattlefieldTriggeredAbility(new GainAbilityAllEffect(VigilanceAbility.getInstance(), Duration.EndOfTurn, filter), true));
    }

    public JoragaBard(final JoragaBard card) {
        super(card);
    }

    @Override
    public JoragaBard copy() {
        return new JoragaBard(this);
    }
}
