
package mage.cards.t;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.combat.CantBeBlockedAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterLandPermanent;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 * @author North
 */
public final class Tanglewalker extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledCreaturePermanent();

    static {
        filter.add(TanglewalkerPredicate.instance);
    }

    public Tanglewalker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");
        this.subtype.add(SubType.DRYAD);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Each creature you control can't be blocked as long as defending player controls an artifact land.
        this.addAbility(new SimpleStaticAbility(new CantBeBlockedAllEffect(filter, Duration.WhileOnBattlefield)
                .setText("Each creature you control can't be blocked as long as defending player controls an artifact land")));
    }

    private Tanglewalker(final Tanglewalker card) {
        super(card);
    }

    @Override
    public Tanglewalker copy() {
        return new Tanglewalker(this);
    }
}

enum TanglewalkerPredicate implements ObjectSourcePlayerPredicate<Permanent> {
    instance; // Each creature must independently evaluate if the player it's attacking has an artifact land

    private static final FilterPermanent filter = new FilterLandPermanent();

    static {
        filter.add(CardType.ARTIFACT.getPredicate());
    }

    @Override
    public boolean apply(ObjectSourcePlayer<Permanent> input, Game game) {
        UUID defendingPlayer = game.getCombat().getDefendingPlayerId(input.getObject().getId(), game);
        return defendingPlayer != null && game.getBattlefield().countAll(filter, defendingPlayer, game) > 0;
    }

    @Override
    public String toString() {
        return "";
    }
}
