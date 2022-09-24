package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.continuous.SetBasePowerToughnessSourceEffect;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.common.FilterCreaturePermanent;

/**
 * @author TheElk801
 */
public final class DoomedArtisanToken extends TokenImpl {

    private static final FilterCreaturePermanent filter
            = new FilterCreaturePermanent(SubType.SCULPTURE, "Sculptures you control");

    static {
        filter.add(TargetController.YOU.getControllerPredicate());
    }

    private static final DynamicValue xValue = new PermanentsOnBattlefieldCount(filter);

    public DoomedArtisanToken() {
        super("Sculpture Token", "colorless Sculpture artifact creature token with \"This creature's power and toughness are each equal to the number of Sculptures you control.\"");
        setOriginalExpansionSetCode("C19");
        cardType.add(CardType.ARTIFACT);
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.SCULPTURE);

        power = new MageInt(0);
        toughness = new MageInt(0);

        // This creature's power and toughness are each equal to the number of Sculpturess you control.
        this.addAbility(new SimpleStaticAbility(new SetBasePowerToughnessSourceEffect(xValue, Duration.EndOfGame)));
    }

    private DoomedArtisanToken(final DoomedArtisanToken token) {
        super(token);
    }

    public DoomedArtisanToken copy() {
        return new DoomedArtisanToken(this);
    }
}
