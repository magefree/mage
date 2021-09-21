package mage.cards.f;

import mage.abilities.effects.common.DamageAllEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
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
public final class FlameSweep extends CardImpl {

    private static final FilterPermanent filter
            = new FilterCreaturePermanent("creature except for creatures you control with flying");

    static {
        filter.add(FlameSweepPredicate.instance);
    }

    public FlameSweep(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{R}");

        // Flame sweep deals 2 damage to each creature except for creatures you control with flying.
        this.getSpellAbility().addEffect(new DamageAllEffect(2, filter));
    }

    private FlameSweep(final FlameSweep card) {
        super(card);
    }

    @Override
    public FlameSweep copy() {
        return new FlameSweep(this);
    }
}

enum FlameSweepPredicate implements ObjectSourcePlayerPredicate<ObjectSourcePlayer<Permanent>> {
    instance;

    @Override
    public boolean apply(ObjectSourcePlayer<Permanent> input, Game game) {
        Permanent object = input.getObject();
        UUID playerId = input.getPlayerId();
        return !(object.isControlledBy(playerId)
                && object.getAbilities(game).containsClass(FlyingAbility.class));
    }
}