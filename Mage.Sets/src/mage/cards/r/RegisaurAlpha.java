
package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.game.permanent.token.DinosaurToken;

/**
 *
 * @author TheElk801
 */
public final class RegisaurAlpha extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("Dinosaurs");

    static {
        filter.add(SubType.DINOSAUR.getPredicate());
        filter.add(TargetController.YOU.getControllerPredicate());
    }

    public RegisaurAlpha(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}{G}");

        this.subtype.add(SubType.DINOSAUR);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Other Dinosaurs you control have haste.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD,
                new GainAbilityControlledEffect(HasteAbility.getInstance(), Duration.WhileOnBattlefield, filter, true)));

        // When Regisaur Alpha enters the battlefield, create a 3/3 green Dinosaur creature token with trample.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new DinosaurToken())));
    }

    private RegisaurAlpha(final RegisaurAlpha card) {
        super(card);
    }

    @Override
    public RegisaurAlpha copy() {
        return new RegisaurAlpha(this);
    }
}
