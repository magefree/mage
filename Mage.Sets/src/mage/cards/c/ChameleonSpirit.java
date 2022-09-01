package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.ChooseColorEffect;
import mage.abilities.effects.common.continuous.SetBasePowerToughnessSourceEffect;
import mage.constants.*;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.filter.FilterPermanent;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author jeffwadsworth
 */
public final class ChameleonSpirit extends CardImpl {

    public ChameleonSpirit(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}");

        this.subtype.add(SubType.ILLUSION);
        this.subtype.add(SubType.SPIRIT);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // As Chameleon Spirit enters the battlefield, choose a color.
        this.addAbility(new EntersBattlefieldAbility(new ChooseColorEffect(Outcome.Neutral)));

        // Chameleon Spirit's power and toughness are each equal to the number 
        // of permanents of the chosen color your opponents control.
        this.addAbility(new SimpleStaticAbility(
                Zone.ALL,
                new SetBasePowerToughnessSourceEffect(
                        PermanentsOfTheChosenColorOpponentsControlCount.instance,
                        Duration.EndOfGame)));
    }

    private ChameleonSpirit(final ChameleonSpirit card) {
        super(card);
    }

    @Override
    public ChameleonSpirit copy() {
        return new ChameleonSpirit(this);
    }
}

enum PermanentsOfTheChosenColorOpponentsControlCount implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        int number = 0;
        FilterPermanent filter = new FilterPermanent();
        filter.add(new ColorPredicate((ObjectColor) game.getState().getValue(sourceAbility.getSourceId() + "_color")));
        for (Permanent permanent : game.getBattlefield().getAllActivePermanents()) {
            if (permanent != null
                    && game.getOpponents(sourceAbility.getControllerId()).contains(permanent.getControllerId())
                    && filter.match(permanent, game)) {
                number++;
            }
        }
        return number;
    }

    @Override
    public PermanentsOfTheChosenColorOpponentsControlCount copy() {
        return PermanentsOfTheChosenColorOpponentsControlCount.instance;
    }

    @Override
    public String toString() {
        return "X";
    }

    @Override
    public String getMessage() {
        return "permanents of the chosen color your opponents control";
    }

}
