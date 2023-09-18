package mage.cards.c;

import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class CylianSunsinger extends CardImpl {

    private static final FilterCreaturePermanent filter
            = new FilterCreaturePermanent("{this} and each other creature with the same name as it");

    static {
        filter.add(CylianSunsingerPredicate.instance);
    }

    public CylianSunsinger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.SHAMAN);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // {R}{G}{W}: Cylian Sunsinger and each other creature with the same name as it get +3/+3 until end of turn.
        this.addAbility(new SimpleActivatedAbility(new BoostAllEffect(
                3, 3, Duration.EndOfTurn, filter, false
        ), new ManaCostsImpl<>("{R}{G}{W}")));
    }

    private CylianSunsinger(final CylianSunsinger card) {
        super(card);
    }

    @Override
    public CylianSunsinger copy() {
        return new CylianSunsinger(this);
    }
}

enum CylianSunsingerPredicate implements ObjectSourcePlayerPredicate<Permanent> {
    instance;

    @Override
    public boolean apply(ObjectSourcePlayer<Permanent> input, Game game) {
        return input.getObject().getId().equals(input.getSourceId()) || CardUtil.haveSameNames(
                input.getObject(), game.getPermanentOrLKIBattlefield(input.getSourceId())
        );
    }
}
