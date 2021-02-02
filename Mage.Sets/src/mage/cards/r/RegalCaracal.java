
package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.permanent.token.CatToken2;

/**
 *
 * @author fireshoes
 */
public final class RegalCaracal extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Cats");

    static {
        filter.add(SubType.CAT.getPredicate());
        filter.add(TargetController.YOU.getControllerPredicate());
    }

    public RegalCaracal(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}{W}");

        this.subtype.add(SubType.CAT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Other Cats you control get +1/+1 and have lifelink.
        Ability ability = new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostControlledEffect(1, 1, Duration.WhileOnBattlefield, filter, true));
        GainAbilityControlledEffect effect = new GainAbilityControlledEffect(LifelinkAbility.getInstance(), Duration.WhileOnBattlefield, filter, true);
        effect.setText("and have lifelink");
        ability.addEffect(effect);
        this.addAbility(ability);

        // When Regal Caracal enters the battlefield, create two 1/1 white Cat creature tokens with lifelink.
        Effect effect2 = new CreateTokenEffect(new CatToken2(), 2);
        effect2.setText("create two 1/1 white Cat creature tokens with lifelink");
        this.addAbility(new EntersBattlefieldTriggeredAbility(effect2));
    }

    private RegalCaracal(final RegalCaracal card) {
        super(card);
    }

    @Override
    public RegalCaracal copy() {
        return new RegalCaracal(this);
    }
}
