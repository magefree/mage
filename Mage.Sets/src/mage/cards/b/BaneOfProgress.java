
package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author LevelX2
 */
public final class BaneOfProgress extends CardImpl {

    public BaneOfProgress(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{G}{G}");
        this.subtype.add(SubType.ELEMENTAL);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When Bane of Progress enters the battlefield, destroy all artifacts and enchantments. Put a +1/+1 counter on Bane of Progress for each permanent destroyed this way.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new BaneOfProgressEffect(), false));
    }

    private BaneOfProgress(final BaneOfProgress card) {
        super(card);
    }

    @Override
    public BaneOfProgress copy() {
        return new BaneOfProgress(this);
    }
}

class BaneOfProgressEffect extends OneShotEffect {

    private static final FilterPermanent filter = new FilterPermanent();
    static {
        filter.add(Predicates.or(
                CardType.ARTIFACT.getPredicate(),
                CardType.ENCHANTMENT.getPredicate()));
    }
    public BaneOfProgressEffect() {
        super(Outcome.DestroyPermanent);
        this.staticText = "destroy all artifacts and enchantments. Put a +1/+1 counter on {this} for each permanent destroyed this way";
    }

    public BaneOfProgressEffect(final BaneOfProgressEffect effect) {
        super(effect);
    }

    @Override
    public BaneOfProgressEffect copy() {
        return new BaneOfProgressEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int destroyedPermanents = 0;
        for (Permanent permanent : game.getBattlefield().getActivePermanents(filter, source.getControllerId(), source, game)) {
            if (permanent.destroy(source, game, false)) {
                destroyedPermanents++;
            }
        }
        if (destroyedPermanents > 0) {
            return new AddCountersSourceEffect(CounterType.P1P1.createInstance(destroyedPermanents),true).apply(game, source);
        }
        return true;
    }
}
