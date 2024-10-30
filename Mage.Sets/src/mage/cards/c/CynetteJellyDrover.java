package mage.cards.c;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldOrDiesSourceTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.game.permanent.token.JellyfishToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CynetteJellyDrover extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creatures you control with flying");

    static {
        filter.add(TargetController.YOU.getControllerPredicate());
        filter.add(new AbilityPredicate(FlyingAbility.class));
    }

    public CynetteJellyDrover(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When Cynette, Jelly Drover enters or dies, create a 1/1 blue Jellyfish creature token with flying.
        this.addAbility(new EntersBattlefieldOrDiesSourceTriggeredAbility(
                new CreateTokenEffect(new JellyfishToken()), false
        ));

        // Creatures you control with flying get +1/+1.
        this.addAbility(new SimpleStaticAbility(new BoostAllEffect(
                1, 1, Duration.WhileOnBattlefield, filter, false
        )));
    }

    private CynetteJellyDrover(final CynetteJellyDrover card) {
        super(card);
    }

    @Override
    public CynetteJellyDrover copy() {
        return new CynetteJellyDrover(this);
    }
}
