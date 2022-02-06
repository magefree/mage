package mage.cards.m;

import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.effects.CostsLessForExiledCardsEffect;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreatureCard;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicate;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetCardInLibrary;
import mage.target.targetadjustment.TargetAdjuster;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MarchOfBurgeoningLife extends CardImpl {

    private static final FilterCard filter = new FilterCard("green cards from your hand");

    static {
        filter.add(new ColorPredicate(ObjectColor.GREEN));
    }

    public MarchOfBurgeoningLife(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{X}{G}");

        // As an additional cost to cast this spell, you may exile any number of green cards from your hand. This spell costs {2} less to cast for each card exiled this way.
        CostsLessForExiledCardsEffect.addCostAndEffect(this, filter);

        // Choose target creature with mana value less than X. Search your library for a creature card with the same name as that creature, put it onto the battlefield tapped, then shuffle.
        this.getSpellAbility().addEffect(new MarchOfBurgeoningLifeEffect());
        this.getSpellAbility().setTargetAdjuster(MarchOfBurgeoningLifeAdjuster.instance);
    }

    private MarchOfBurgeoningLife(final MarchOfBurgeoningLife card) {
        super(card);
    }

    @Override
    public MarchOfBurgeoningLife copy() {
        return new MarchOfBurgeoningLife(this);
    }
}

enum MarchOfBurgeoningLifeAdjuster implements TargetAdjuster {
    instance;

    @Override
    public void adjustTargets(Ability ability, Game game) {
        int xValue = ability.getManaCostsToPay().getX();
        FilterPermanent filter = new FilterCreaturePermanent("creature with mana value less than " + xValue);
        filter.add(new ManaValuePredicate(ComparisonType.FEWER_THAN, xValue));
        ability.getTargets().clear();
        ability.addTarget(new TargetPermanent(filter));
    }
}

class MarchOfBurgeoningLifeEffect extends OneShotEffect {

    private static class MarchOfBurgeoningLifePredicate implements Predicate<Card> {
        private final Permanent permanent;

        private MarchOfBurgeoningLifePredicate(Permanent permanent) {
            this.permanent = permanent;
        }

        @Override
        public boolean apply(Card input, Game game) {
            return CardUtil.haveSameNames(permanent, input);
        }
    }

    MarchOfBurgeoningLifeEffect() {
        super(Outcome.Benefit);
        staticText = "choose target creature with mana value less than X. Search your library for a creature card " +
                "with the same name as that creature, put it onto the battlefield tapped, then shuffle";
    }

    private MarchOfBurgeoningLifeEffect(final MarchOfBurgeoningLifeEffect effect) {
        super(effect);
    }

    @Override
    public MarchOfBurgeoningLifeEffect copy() {
        return new MarchOfBurgeoningLifeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        if (player == null || permanent == null) {
            return false;
        }
        FilterCard filter = new FilterCreatureCard("creature card with the same name as " + permanent.getName());
        filter.add(new MarchOfBurgeoningLifePredicate(permanent));
        TargetCardInLibrary target = new TargetCardInLibrary(0, 1, filter);
        player.searchLibrary(target, source, game);
        Card card = player.getLibrary().getCard(target.getFirstTarget(), game);
        if (card != null) {
            player.moveCards(
                    card, Zone.BATTLEFIELD, source, game, true,
                    false, false, null
            );
        }
        player.shuffleLibrary(source, game);
        return true;
    }
}
