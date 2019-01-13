
package mage.cards.m;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.PreventAllDamageByAllPermanentsEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author BetaSteward
 */
public final class Moonmist extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creatures other than Werewolves and Wolves");

    static {
        filter.add(Predicates.not(new SubtypePredicate(SubType.WEREWOLF)));
        filter.add(Predicates.not(new SubtypePredicate(SubType.WOLF)));
    }

    public Moonmist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{G}");


        // Transform all Humans. Prevent all combat damage that would be dealt this turn by creatures other than Werewolves and Wolves.
        this.getSpellAbility().addEffect(new MoonmistEffect());
        this.getSpellAbility().addEffect(new PreventAllDamageByAllPermanentsEffect(filter, Duration.EndOfTurn, true));
    }

    public Moonmist(final Moonmist card) {
        super(card);
    }

    @Override
    public Moonmist copy() {
        return new Moonmist(this);
    }
}

class MoonmistEffect extends OneShotEffect {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("humans");

    static {
        filter.add(new SubtypePredicate(SubType.HUMAN));
    }

    public MoonmistEffect() {
        super(Outcome.PreventDamage);
        staticText = "Transform all Humans";
    }

    public MoonmistEffect(final MoonmistEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (Permanent permanent: game.getBattlefield().getActivePermanents(filter, source.getControllerId(), game)) {
            if (permanent.isTransformable()) {
                permanent.transform(game);
                game.informPlayers(permanent.getName() + " transforms into " + permanent.getSecondCardFace().getName());
            }
        }
        return true;
    }

    @Override
    public MoonmistEffect copy() {
        return new MoonmistEffect(this);
    }

}
