package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.continuous.SetPowerToughnessSourceEffect;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.ControllerPredicate;

/**
 * @author TheElk801
 */
public final class DoomedArtisanToken extends TokenImpl {

    private static final FilterCreaturePermanent filter
            = new FilterCreaturePermanent(SubType.SCULPTURE, "Sculptures you control");

    static {
        filter.add(new ControllerPredicate(TargetController.YOU));
    }

    private static final DynamicValue xValue = new PermanentsOnBattlefieldCount(filter);

    public DoomedArtisanToken() {
        super("Sculpture", "colorless Sculpture artifact creature token with \"This creature's power and toughness are each equal to the number of Sculptures you control.\"");
        setOriginalExpansionSetCode("C19");
        cardType.add(CardType.ARTIFACT);
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.SCULPTURE);

        power = new MageInt(0);
        toughness = new MageInt(0);

        // This creature's power and toughness are each equal to the number of Sculpturess you control.
        this.addAbility(new SimpleStaticAbility(new SetPowerToughnessSourceEffect(xValue, Duration.EndOfGame)));
    }

    private DoomedArtisanToken(final DoomedArtisanToken token) {
        super(token);
    }

    public DoomedArtisanToken copy() {
        return new DoomedArtisanToken(this);
    }
}
