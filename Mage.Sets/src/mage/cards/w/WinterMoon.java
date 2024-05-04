package mage.cards.w;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.RestrictionUntapNotMoreThanEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SuperType;
import mage.filter.common.FilterControlledLandPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class WinterMoon extends CardImpl {

    public WinterMoon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");

        // Players can't untap more than one nonbasic land during their untap steps.
        this.addAbility(new SimpleStaticAbility(new WinterMoonEffect()));
    }

    private WinterMoon(final WinterMoon card) {
        super(card);
    }

    @Override
    public WinterMoon copy() {
        return new WinterMoon(this);
    }
}

class WinterMoonEffect extends RestrictionUntapNotMoreThanEffect {

    private static final FilterControlledPermanent filter = new FilterControlledLandPermanent();

    static {
        filter.add(Predicates.not(SuperType.BASIC.getPredicate()));
    }

    WinterMoonEffect() {
        super(Duration.WhileOnBattlefield, 1, filter);
        staticText = "players can't untap more than one nonbasic land during their untap steps";
    }

    private WinterMoonEffect(final WinterMoonEffect effect) {
        super(effect);
    }

    @Override
    public WinterMoonEffect copy() {
        return new WinterMoonEffect(this);
    }

    @Override
    public boolean applies(Player player, Ability source, Game game) {
        return true;
    }
}
