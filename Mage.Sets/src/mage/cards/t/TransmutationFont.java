package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInPlayEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.choices.Choice;
import mage.choices.ChoiceImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterArtifactPermanent;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.BloodToken;
import mage.game.permanent.token.ClueArtifactToken;
import mage.game.permanent.token.FoodToken;
import mage.game.permanent.token.Token;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;
import mage.target.common.TargetSacrifice;
import mage.util.CardUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Supplier;

/**
 * @author TheElk801
 */
public final class TransmutationFont extends CardImpl {

    public TransmutationFont(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{5}");

        // {T}: Create your choice of a Blood token, a Clue token, or a Food token.
        this.addAbility(new SimpleActivatedAbility(new TransmutationFontEffect(), new TapSourceCost()));

        // {3}, {T}, Sacrifice three artifact tokens with different names: Search your library for an artifact card, put it onto the battlefield, then shuffle. Activate only as a sorcery.
        Ability ability = new ActivateAsSorceryActivatedAbility(new SearchLibraryPutInPlayEffect(new TargetCardInLibrary(StaticFilters.FILTER_CARD_ARTIFACT)), new GenericManaCost(3));
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeTargetCost(new TransmutationFontTarget()));
        this.addAbility(ability);
    }

    private TransmutationFont(final TransmutationFont card) {
        super(card);
    }

    @Override
    public TransmutationFont copy() {
        return new TransmutationFont(this);
    }
}

class TransmutationFontEffect extends OneShotEffect {

    private static final Map<String, Supplier<Token>> map = new HashMap<>();

    static {
        map.put("Blood", BloodToken::new);
        map.put("Clue", ClueArtifactToken::new);
        map.put("Food", FoodToken::new);
    }

    TransmutationFontEffect() {
        super(Outcome.Benefit);
        staticText = "create your choice of a Blood token, a Clue token, or a Food token";
    }

    private TransmutationFontEffect(final TransmutationFontEffect effect) {
        super(effect);
    }

    @Override
    public TransmutationFontEffect copy() {
        return new TransmutationFontEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Choice choice = new ChoiceImpl(true);
        choice.setMessage("Choose a token to create");
        choice.setChoices(map.keySet());
        player.choose(outcome, choice, game);
        return map.get(choice.getChoice()).get().putOntoBattlefield(1, game, source);
    }
}

class TransmutationFontTarget extends TargetSacrifice {

    private static final FilterPermanent filter
            = new FilterArtifactPermanent("artifact tokens with different names");

    static {
        filter.add(TokenPredicate.TRUE);
    }

    TransmutationFontTarget() {
        super(3, 3, filter);
    }

    private TransmutationFontTarget(final TransmutationFontTarget target) {
        super(target);
    }

    @Override
    public TransmutationFontTarget copy() {
        return new TransmutationFontTarget(this);
    }

    @Override
    public boolean canTarget(UUID playerId, UUID id, Ability source, Game game) {
        if (!super.canTarget(playerId, id, source, game)) {
            return false;
        }
        Permanent permanent = game.getPermanent(id);
        return this
                .getTargets()
                .stream()
                .map(game::getPermanent)
                .filter(Objects::nonNull)
                .noneMatch(p -> CardUtil.haveSameNames(p, permanent));
    }
}
