package mage.cards.d;

import mage.MageInt;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.combat.CantAttackBlockAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.game.permanent.token.DoomedArtisanToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DoomedArtisan extends CardImpl {

    private static final FilterCreaturePermanent filter
            = new FilterCreaturePermanent(SubType.SCULPTURE, "Sculptures you control");

    static {
        filter.add(new ControllerPredicate(TargetController.YOU));
    }

    public DoomedArtisan(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ARTIFICER);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Sculptures you control can't attack or block.
        this.addAbility(new SimpleStaticAbility(new CantAttackBlockAllEffect(Duration.WhileOnBattlefield, filter)));

        // At the beginning of your end step, create a colorless Sculpture artifact creature token with "This creature's power and toughness are equal to the number of Sculptures you control"
        this.addAbility(new BeginningOfEndStepTriggeredAbility(
                new CreateTokenEffect(new DoomedArtisanToken()), TargetController.YOU, false)
        );
    }

    private DoomedArtisan(final DoomedArtisan card) {
        super(card);
    }

    @Override
    public DoomedArtisan copy() {
        return new DoomedArtisan(this);
    }
}
