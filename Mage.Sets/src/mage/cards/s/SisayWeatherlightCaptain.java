package mage.cards.s;

import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInPlayEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.filter.common.FilterPermanentCard;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SisayWeatherlightCaptain extends CardImpl {

    public SisayWeatherlightCaptain(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Sisay, Weatherlight Captain gets +1/+1 for each color among other legendary permanents you control.
        this.addAbility(new SimpleStaticAbility(new BoostSourceEffect(
                SisayWeatherlightCaptainValue.instance,
                SisayWeatherlightCaptainValue.instance,
                Duration.WhileOnBattlefield
        )));

        // {W}{U}{B}{R}{G}: Search your library for a legendary permanent card with converted mana cost less than Sisay's power, put that card onto the battlefield, then shuffle your library.
        this.addAbility(new SimpleActivatedAbility(
                new SisayWeatherlightCaptainEffect(),
                new ManaCostsImpl<>("{W}{U}{B}{R}{G}")
        ));
    }

    private SisayWeatherlightCaptain(final SisayWeatherlightCaptain card) {
        super(card);
    }

    @Override
    public SisayWeatherlightCaptain copy() {
        return new SisayWeatherlightCaptain(this);
    }
}

enum SisayWeatherlightCaptainValue implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        ObjectColor color = new ObjectColor();
        for (Permanent permanent : game.getBattlefield().getAllActivePermanents(
                StaticFilters.FILTER_PERMANENT_LEGENDARY, sourceAbility.getControllerId(), game
        )) {
            if (permanent != null && !permanent.getId().equals(sourceAbility.getSourceId())) {
                color.addColor(permanent.getColor(game));
            }
        }
        return color.getColorCount();
    }

    @Override
    public DynamicValue copy() {
        return instance;
    }

    @Override
    public String getMessage() {
        return "color among other legendary permanents you control";
    }

    @Override
    public String toString() {
        return "1";
    }
}

class SisayWeatherlightCaptainEffect extends OneShotEffect {

    SisayWeatherlightCaptainEffect() {
        super(Outcome.Benefit);
        staticText = "Search your library for a legendary permanent card with mana value " +
                "less than {this}'s power, put that card onto the battlefield, then shuffle.";
    }

    private SisayWeatherlightCaptainEffect(final SisayWeatherlightCaptainEffect effect) {
        super(effect);
    }

    @Override
    public SisayWeatherlightCaptainEffect copy() {
        return new SisayWeatherlightCaptainEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanentOrLKIBattlefield(source.getSourceId());
        if (permanent == null) {
            return false;
        }
        int power = permanent.getPower().getValue();
        FilterCard filter = new FilterPermanentCard("legendary permanent card with mana value less than " + power);
        filter.add(SuperType.LEGENDARY.getPredicate());
        filter.add(new ManaValuePredicate(ComparisonType.FEWER_THAN, power));
        return new SearchLibraryPutInPlayEffect(new TargetCardInLibrary(filter)).apply(game, source);
    }
}