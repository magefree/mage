
package mage.cards.j;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.keyword.FlashAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.AnotherPredicate;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author Styxo
 */
public final class JediSentinel extends CardImpl {

    private static final FilterControlledCreaturePermanent filter1 = new FilterControlledCreaturePermanent("another target creature you control");
    private static final FilterCreaturePermanent filter2 = new FilterCreaturePermanent("creature you don't control");

    static {
        filter1.add(new AnotherPredicate());
        filter2.add(new ControllerPredicate(TargetController.NOT_YOU));
    }

    public JediSentinel(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{G}{U}{W}");
        this.subtype.add(SubType.TWILEK);
        this.subtype.add(SubType.JEDI);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // When Jedi Sentinel enters the battlefield, return another target creature you control and target creature you don't control to their owners' hands.
        Effect effect = new ReturnToHandTargetEffect(true);
        effect.setText("return another target creature you control and target creature you don't control to their owners' hands");
        Ability ability = new EntersBattlefieldTriggeredAbility(effect);
        ability.addTarget(new TargetControlledCreaturePermanent(filter1));
        ability.addTarget(new TargetCreaturePermanent(filter2));
        this.addAbility(ability);

    }

    public JediSentinel(final JediSentinel card) {
        super(card);
    }

    @Override
    public JediSentinel copy() {
        return new JediSentinel(this);
    }
}
