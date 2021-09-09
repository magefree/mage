package mage.cards.d;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlashbackAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TimingRule;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author weirddan455
 */
public final class DireStrainRampage extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("artifact, enchantment, or land");

    static {
        filter.add(Predicates.or(
                CardType.ARTIFACT.getPredicate(),
                CardType.ENCHANTMENT.getPredicate(),
                CardType.LAND.getPredicate()
        ));
    }

    public DireStrainRampage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{R}{G}");

        // Destroy target artifact, enchantment, or land. If a land was destroyed this way,
        // its controller may search their library for up to two basic land cards, put them onto the battlefield tapped, then shuffle.
        // Otherwise, its controller may search their library for a basic land card, put it onto the battlefield tapped, then shuffle.
        this.getSpellAbility().addEffect(new DireStrainRampageEffect());
        this.getSpellAbility().addTarget(new TargetPermanent(filter));

        // Flashback {3}{R}{G}
        this.addAbility(new FlashbackAbility(new ManaCostsImpl<>("{3}{R}{G}"), TimingRule.SORCERY));
    }

    private DireStrainRampage(final DireStrainRampage card) {
        super(card);
    }

    @Override
    public DireStrainRampage copy() {
        return new DireStrainRampage(this);
    }
}

class DireStrainRampageEffect extends OneShotEffect {

    public DireStrainRampageEffect() {
        super(Outcome.DestroyPermanent);
        staticText = "Destroy target artifact, enchantment, or land. If a land was destroyed this way, " +
                "its controller may search their library for up to two basic land cards, put them onto the battlefield tapped, then shuffle. " +
                "Otherwise, its controller may search their library for a basic land card, put it onto the battlefield tapped, then shuffle";
    }

    private DireStrainRampageEffect(final DireStrainRampageEffect effect) {
        super(effect);
    }

    @Override
    public DireStrainRampageEffect copy() {
        return new DireStrainRampageEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        if (permanent == null) {
            return false;
        }
        Player player = game.getPlayer(permanent.getControllerId());
        boolean landTargeted = permanent.isLand(game);
        boolean destroyed = permanent.destroy(source, game, false);
        boolean twoLands = false;
        if (landTargeted && destroyed) {
            // Check that no replacement happened (card actually went to the graveyard and "was destroyed this way")
            if (game.getState().getZone(permanent.getId()) == Zone.GRAVEYARD) {
                twoLands = true;
            }
        }
        String searchString;
        if (twoLands) {
            searchString = "Search your library for up to two basic land cards, put them onto the battlefield tapped, then shuffle?";
        } else {
            searchString = "Search your library for a basic land card, put it onto the battlefield tapped, then shuffle?";
        }
        if (player != null && player.chooseUse(Outcome.PutLandInPlay, searchString, source, game)) {
            TargetCardInLibrary target;
            if (twoLands) {
                target = new TargetCardInLibrary(0, 2, StaticFilters.FILTER_CARD_BASIC_LANDS);
            } else {
                target = new TargetCardInLibrary(1, 1, StaticFilters.FILTER_CARD_BASIC_LAND);
            }
            if (player.searchLibrary(target, source, game)) {
                Set<Card> lands = new HashSet<>();
                for (UUID cardId : target.getTargets()) {
                    Card card = game.getCard(cardId);
                    if (card != null) {
                        lands.add(card);
                    }
                }
                if (!lands.isEmpty()) {
                    player.moveCards(lands, Zone.BATTLEFIELD, source, game, true, false, false, null);
                }
            }
            player.shuffleLibrary(source, game);
        }
        return true;
    }
}
