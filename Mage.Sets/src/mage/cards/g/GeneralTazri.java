
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterCreatureCard;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author LevelX2
 */
public final class GeneralTazri extends CardImpl {

    private static final FilterCreatureCard filter = new FilterCreatureCard("an Ally creature card");

    static {
        filter.add(SubType.ALLY.getPredicate());
    }

    public GeneralTazri(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{W}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ALLY);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // When General Tazri enters the battlefield, you may search your library for an Ally creature card, reveal it, put it into your hand, then shuffle your library.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new SearchLibraryPutInHandEffect(
                new TargetCardInLibrary(filter), true, true), true));
        // {W}{U}{B}{R}{G}: Ally creatures you control get +X/+X until end of turn, where X is the number of colors among those creatures.
        DynamicValue xValue = new GeneralTazriColorCount();
        BoostControlledEffect effect = new BoostControlledEffect(xValue, xValue, Duration.EndOfTurn, new FilterCreaturePermanent(SubType.ALLY, "Ally creatures"), false);
        effect.setLockedIn(true);
        this.addAbility(new SimpleActivatedAbility(
                Zone.BATTLEFIELD,
                effect,
                new ManaCostsImpl<>("{W}{U}{B}{R}{G}")));

    }

    private GeneralTazri(final GeneralTazri card) {
        super(card);
    }

    @Override
    public GeneralTazri copy() {
        return new GeneralTazri(this);
    }
}

class GeneralTazriColorCount implements DynamicValue {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent();

    static {
        filter.add(SubType.ALLY.getPredicate());
    }

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        int count = 0;
        boolean black = false;
        boolean red = false;
        boolean white = false;
        boolean green = false;
        boolean blue = false;
        for (Permanent creature : game.getBattlefield().getAllActivePermanents(filter, sourceAbility.getControllerId(), game)) {
            ObjectColor color = creature.getColor(game);
            black |= color.isBlack();
            red |= color.isRed();
            white |= color.isWhite();
            green |= color.isGreen();
            blue |= color.isBlue();
        }
        count += black ? 1 : 0;
        count += red ? 1 : 0;
        count += white ? 1 : 0;
        count += green ? 1 : 0;
        count += blue ? 1 : 0;
        return count;
    }

    @Override
    public GeneralTazriColorCount copy() {
        return new GeneralTazriColorCount();
    }

    @Override
    public String getMessage() {
        return "the number of colors among those creatures";
    }

    @Override
    public String toString() {
        return "X";
    }
}
