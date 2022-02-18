package mage.cards.m;

import mage.abilities.Ability;
import mage.abilities.costs.common.TapTargetCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.PutOnLibraryTargetEffect;
import mage.abilities.keyword.ChannelAbility;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetControlledPermanent;
import mage.target.common.TargetNonlandPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MoonsnarePrototype extends CardImpl {

    private static final FilterControlledPermanent filter
            = new FilterControlledPermanent("untapped artifact or creature you control");

    static {
        filter.add(TappedPredicate.UNTAPPED);
        filter.add(Predicates.or(
                CardType.ARTIFACT.getPredicate(),
                CardType.CREATURE.getPredicate()
        ));
    }

    public MoonsnarePrototype(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{U}");

        // {T}, Tap an untapped artifact or creature you control: Add {C}.
        Ability ability = new ColorlessManaAbility();
        ability.addCost(new TapTargetCost(new TargetControlledPermanent(filter)));
        this.addAbility(ability);

        // Channel — {4}{U}, Discard Moonsnare Prototype: The owner of target nonland permanent puts it on the top or bottom of their library.
        ability = new ChannelAbility("{4}{U}", new MoonsnarePrototypeEffect());
        ability.addTarget(new TargetNonlandPermanent());
        this.addAbility(ability);
    }

    private MoonsnarePrototype(final MoonsnarePrototype card) {
        super(card);
    }

    @Override
    public MoonsnarePrototype copy() {
        return new MoonsnarePrototype(this);
    }
}

class MoonsnarePrototypeEffect extends OneShotEffect {

    MoonsnarePrototypeEffect() {
        super(Outcome.Benefit);
        staticText = "the owner of target nonland permanent puts it on the top or bottom of their library";
    }

    private MoonsnarePrototypeEffect(final MoonsnarePrototypeEffect effect) {
        super(effect);
    }

    @Override
    public MoonsnarePrototypeEffect copy() {
        return new MoonsnarePrototypeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(game.getOwnerId(source.getFirstTarget()));
        if (player == null) {
            return false;
        }
        if (player.chooseUse(Outcome.Detriment, "Put the targeted object on the top or bottom of your library?",
                "", "Top", "Bottom", source, game)) {
            return new PutOnLibraryTargetEffect(true).apply(game, source);
        }
        return new PutOnLibraryTargetEffect(false).apply(game, source);
    }
}
