
package mage.cards.i;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author TheElk801
 */
public final class ImperialAerosaur extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("another creature you control");

    static {
        filter.add(TargetController.YOU.getControllerPredicate());
        filter.add(AnotherPredicate.instance);
    }

    public ImperialAerosaur(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}");

        this.subtype.add(SubType.DINOSAUR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Imperial Aerosaur enters the battlefield, another target creature you control gets +1/+1 and gains flying until end of turn.
        Effect effect = new BoostTargetEffect(1, 1, Duration.EndOfTurn);
        effect.setText("another target creature you control gets +1/+1");
        EntersBattlefieldTriggeredAbility ability = new EntersBattlefieldTriggeredAbility(effect);
        effect = new GainAbilityTargetEffect(FlyingAbility.getInstance(), Duration.EndOfTurn);
        effect.setText("and gains flying until end of turn");
        ability.addEffect(effect);
        ability.addTarget(new TargetCreaturePermanent(filter));
        this.addAbility(ability);
    }

    private ImperialAerosaur(final ImperialAerosaur card) {
        super(card);
    }

    @Override
    public ImperialAerosaur copy() {
        return new ImperialAerosaur(this);
    }
}
