package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.ColorsAmongControlledPermanentsCount;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInPlayEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
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

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Sisay, Weatherlight Captain gets +1/+1 for each color among other legendary permanents you control.
        this.addAbility(new SimpleStaticAbility(new BoostSourceEffect(
                ColorsAmongControlledPermanentsCount.OTHER_LEGENDARY,
                ColorsAmongControlledPermanentsCount.OTHER_LEGENDARY,
                Duration.WhileOnBattlefield
        )).addHint(ColorsAmongControlledPermanentsCount.OTHER_LEGENDARY.getHint()));

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
