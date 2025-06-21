package mage.cards.t;

import mage.abilities.dynamicvalue.common.GreatestAmongPermanentsValue;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author L_J
 */
public final class Topple extends CardImpl {

    private static final FilterPermanent filter
            = new FilterCreaturePermanent("creature with the greatest power among creatures on the battlefield");

    static {
        filter.add(TopplePredicate.instance);
    }

    public Topple(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{W}");

        // Exile target creature with the greatest power among creatures on the battlefield.
        this.getSpellAbility().addEffect(new ExileTargetEffect());
        this.getSpellAbility().addTarget(new TargetPermanent(filter));
    }

    private Topple(final Topple card) {
        super(card);
    }

    @Override
    public Topple copy() {
        return new Topple(this);
    }
}

enum TopplePredicate implements ObjectSourcePlayerPredicate<Permanent> {
    instance;

    @Override
    public boolean apply(ObjectSourcePlayer<Permanent> input, Game game) {
        return input.getObject().getPower().getValue()
                >= GreatestAmongPermanentsValue.POWER_ALL_CREATURES.calculate(game, input.getSource(), null);
    }
}
