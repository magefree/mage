package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.ExileFromGraveCost;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenCopyTargetEffect;
import mage.abilities.effects.common.MillCardsControllerEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledArtifactPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.PermanentCard;
import mage.target.TargetPermanent;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.common.TargetControlledPermanent;
import mage.util.CardUtil;

import java.util.Collection;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TawnosSolemnSurvivor extends CardImpl {

    private static final FilterPermanent filter
            = new FilterControlledArtifactPermanent("artifact token you control");
    private static final FilterControlledPermanent filter2
            = new FilterControlledArtifactPermanent("artifact tokens");

    static {
        filter.add(TokenPredicate.TRUE);
        filter2.add(TokenPredicate.TRUE);
    }

    public TawnosSolemnSurvivor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ARTIFICER);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // {2}, {T}: Create a token that's a copy of up to one target artifact token you control. Mill two cards.
        Ability ability = new SimpleActivatedAbility(new CreateTokenCopyTargetEffect(), new GenericManaCost(2));
        ability.addCost(new TapSourceCost());
        ability.addEffect(new MillCardsControllerEffect(2));
        ability.addTarget(new TargetPermanent(0, 1, filter));
        this.addAbility(ability);

        // {1}{W}{U}{B}, {T}, Sacrifice two artifact tokens, Exile an artifact or creature card from your graveyard: Create a token that's a copy of the exiled card, except it's an artifact in addition to its other types. Activate only as a sorcery.
        ability = new ActivateAsSorceryActivatedAbility(
                new TawnosSolemnSurvivorEffect(), new ManaCostsImpl<>("{1}{W}{U}{B}")
        );
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeTargetCost(new TargetControlledPermanent(2, filter2)));
        ability.addCost(new ExileFromGraveCost(
                new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_ARTIFACT_OR_CREATURE)
        ));
        this.addAbility(ability);
    }

    private TawnosSolemnSurvivor(final TawnosSolemnSurvivor card) {
        super(card);
    }

    @Override
    public TawnosSolemnSurvivor copy() {
        return new TawnosSolemnSurvivor(this);
    }
}

class TawnosSolemnSurvivorEffect extends OneShotEffect {

    TawnosSolemnSurvivorEffect() {
        super(Outcome.Benefit);
        staticText = "create a token that's a copy of the exiled card, " +
                "except it's an artifact in addition to its other types";
    }

    private TawnosSolemnSurvivorEffect(final TawnosSolemnSurvivorEffect effect) {
        super(effect);
    }

    @Override
    public TawnosSolemnSurvivorEffect copy() {
        return new TawnosSolemnSurvivorEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Card card = CardUtil
                .castStream(source.getCosts().stream(), ExileFromGraveCost.class)
                .map(ExileFromGraveCost::getExiledCards)
                .flatMap(Collection::stream)
                .findFirst()
                .orElse(null);
        if (card == null) {
            return false;
        }
        Permanent permanent = new PermanentCard(CardUtil.getDefaultCardSideForBattlefield(game, card), source.getControllerId(), game);
        return new CreateTokenCopyTargetEffect(
                null, CardType.ARTIFACT, false
        ).setSavedPermanent(permanent).apply(game, source);
    }
}
