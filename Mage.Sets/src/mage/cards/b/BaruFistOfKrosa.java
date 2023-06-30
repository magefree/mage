
package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.abilityword.GrandeurAbility;
import mage.abilities.common.EntersBattlefieldAllTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterControlledLandPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.common.FilterLandPermanent;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.Game;
import mage.game.permanent.token.BaruFistOfKrosaToken;
import mage.game.permanent.token.Token;

/**
 *
 * @author emerald000
 */
public final class BaruFistOfKrosa extends CardImpl {

    private static final FilterLandPermanent forestFilter = new FilterLandPermanent("Forest");
    private static final FilterCreaturePermanent greenCreatureFilter = new FilterCreaturePermanent("green creatures you control");

    static {
        forestFilter.add(SubType.FOREST.getPredicate());
        greenCreatureFilter.add(TargetController.YOU.getControllerPredicate());
        greenCreatureFilter.add(new ColorPredicate(ObjectColor.GREEN));
    }

    public BaruFistOfKrosa(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}{G}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN, SubType.DRUID);

        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Whenever a Forest enters the battlefield, green creatures you control get +1/+1 and gain trample until end of turn.
        Ability ability = new EntersBattlefieldAllTriggeredAbility(new BoostControlledEffect(1, 1, Duration.EndOfTurn, greenCreatureFilter), forestFilter, "Whenever a Forest enters the battlefield, green creatures you control get +1/+1 and gain trample until end of turn.");
        ability.addEffect(new GainAbilityControlledEffect(TrampleAbility.getInstance(), Duration.EndOfTurn, greenCreatureFilter));
        this.addAbility(ability);

        // Grandeur - Discard another card named Baru, Fist of Krosa: Create an X/X green Wurm creature token, where X is the number of lands you control.
        this.addAbility(new GrandeurAbility(new BaruFistOfKrosaEffect(), "Baru, Fist of Krosa"));
    }

    private BaruFistOfKrosa(final BaruFistOfKrosa card) {
        super(card);
    }

    @Override
    public BaruFistOfKrosa copy() {
        return new BaruFistOfKrosa(this);
    }
}

class BaruFistOfKrosaEffect extends OneShotEffect {

    static final FilterControlledPermanent filter = new FilterControlledLandPermanent("lands you control");

    BaruFistOfKrosaEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "create an X/X green Wurm creature token, where X is the number of lands you control.";
    }

    BaruFistOfKrosaEffect(final BaruFistOfKrosaEffect effect) {
        super(effect);
    }

    @Override
    public BaruFistOfKrosaEffect copy() {
        return new BaruFistOfKrosaEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int xValue = game.getBattlefield().countAll(filter, source.getControllerId(), game);
        Token token = new BaruFistOfKrosaToken(xValue);
        token.putOntoBattlefield(1, game, source, source.getControllerId());
        return true;
    }
}
