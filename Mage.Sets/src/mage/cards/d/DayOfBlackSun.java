package mage.cards.d;

import mage.abilities.dynamicvalue.common.GetXValue;
import mage.abilities.effects.common.DestroyAllEffect;
import mage.abilities.effects.common.continuous.LoseAllAbilitiesAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DayOfBlackSun extends CardImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent();

    static {
        filter.add(DayOfBlackSunPredicate.instance);
    }

    public DayOfBlackSun(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{X}{B}{B}");

        // Each creature with mana value X or less loses all abilities until end of turn. Destroy those creatures.
        this.getSpellAbility().addEffect(new LoseAllAbilitiesAllEffect(filter, Duration.EndOfTurn)
                .setText("each creature with mana value X or less loses all abilities until end of turn"));
        this.getSpellAbility().addEffect(new DestroyAllEffect(filter).setText("Destroy those creatures"));
    }

    private DayOfBlackSun(final DayOfBlackSun card) {
        super(card);
    }

    @Override
    public DayOfBlackSun copy() {
        return new DayOfBlackSun(this);
    }
}

enum DayOfBlackSunPredicate implements ObjectSourcePlayerPredicate<Permanent> {
    instance;

    @Override
    public boolean apply(ObjectSourcePlayer<Permanent> input, Game game) {
        return input.getObject().getManaValue() <= GetXValue.instance.calculate(game, input.getSource(), null);
    }
}
