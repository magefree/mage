package mage.cards.i;

import mage.abilities.effects.common.DestroyAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicate;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class IridianMaelstrom extends CardImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent();

    static {
        filter.add(IridianMaelstromPredicate.instance);
    }

    public IridianMaelstrom(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{W}{U}{B}{R}{G}");

        // Destroy each creature that isn't all colors.
        this.getSpellAbility().addEffect(new DestroyAllEffect(filter).setText("destroy each creature that isn't all colors"));
    }

    private IridianMaelstrom(final IridianMaelstrom card) {
        super(card);
    }

    @Override
    public IridianMaelstrom copy() {
        return new IridianMaelstrom(this);
    }
}

enum IridianMaelstromPredicate implements Predicate<Permanent> {
    instance;

    @Override
    public boolean apply(Permanent input, Game game) {
        return input.getColor(game).getColorCount() < 5;
    }
}
