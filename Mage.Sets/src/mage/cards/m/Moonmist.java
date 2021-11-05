package mage.cards.m;

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
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 * @author BetaSteward
 */
public final class Moonmist extends CardImpl {

    private static final FilterCreaturePermanent filter
            = new FilterCreaturePermanent("creatures other than Werewolves and Wolves");

    static {
        filter.add(Predicates.not(SubType.WEREWOLF.getPredicate()));
        filter.add(Predicates.not(SubType.WOLF.getPredicate()));
    }

    public Moonmist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{G}");

        // Transform all Humans. Prevent all combat damage that would be dealt this turn by creatures other than Werewolves and Wolves.
        this.getSpellAbility().addEffect(new MoonmistEffect());
        this.getSpellAbility().addEffect(new PreventAllDamageByAllPermanentsEffect(filter, Duration.EndOfTurn, true));
    }

    private Moonmist(final Moonmist card) {
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
        filter.add(SubType.HUMAN.getPredicate());
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
        for (Permanent permanent : game.getBattlefield().getActivePermanents(filter, source.getControllerId(), game)) {
            permanent.transform(source, game);
        }
        return true;
    }

    @Override
    public MoonmistEffect copy() {
        return new MoonmistEffect(this);
    }
}
