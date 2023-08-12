package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterLandPermanent;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetLandPermanent;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class ChokingSands extends CardImpl {

    private static final FilterLandPermanent filter = new FilterLandPermanent("non-Swamp land");

    static {
        filter.add(Predicates.not(SubType.SWAMP.getPredicate()));
    }

    public ChokingSands(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{B}{B}");

        // Destroy target non-Swamp land.
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addTarget(new TargetLandPermanent(filter));

        // If that land was nonbasic, Choking Sands deals 2 damage to the land's controller.
        this.getSpellAbility().addEffect(new ChokingSandsEffect());
    }

    private ChokingSands(final ChokingSands card) {
        super(card);
    }

    @Override
    public ChokingSands copy() {
        return new ChokingSands(this);
    }
}

class ChokingSandsEffect extends OneShotEffect {

    public ChokingSandsEffect() {
        super(Outcome.Damage);
        this.staticText = "If that land was nonbasic, Choking Sands deals 2 damage to the land's controller";
    }

    public ChokingSandsEffect(final ChokingSandsEffect effect) {
        super(effect);
    }

    @Override
    public ChokingSandsEffect copy() {
        return new ChokingSandsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = (Permanent) game.getLastKnownInformation(source.getFirstTarget(), Zone.BATTLEFIELD);
        if (permanent != null && !permanent.isBasic(game)) {
            Player player = game.getPlayer(permanent.getControllerId());
            if (player != null) {
                player.damage(2, source.getSourceId(), source, game);
                return true;
            }
        }
        return false;
    }
}