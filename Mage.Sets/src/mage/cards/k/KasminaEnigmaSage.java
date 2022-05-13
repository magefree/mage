package mage.cards.k;

import mage.ApprovingObject;
import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.GetXLoyaltyValue;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.filter.common.FilterInstantOrSorceryCard;
import mage.filter.predicate.mageobject.SharesColorPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.FractalToken;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author TheElk801
 */
public final class KasminaEnigmaSage extends CardImpl {

    public KasminaEnigmaSage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{1}{G}{U}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.KASMINA);
        this.setStartingLoyalty(2);

        // Each other planeswalker you control has the loyalty abilities of Kasmina, Enigma Sage.
        this.addAbility(new SimpleStaticAbility(new KasminaEnigmaSageGainAbilitiesEffect()));

        // +2: Scry 1.
        this.addAbility(new LoyaltyAbility(new ScryEffect(1, false), 2));

        // −X: Create a 0/0 green and blue Fractal creature token. Put X +1/+1 counters on it.
        this.addAbility(new LoyaltyAbility(FractalToken.getEffect(
                GetXLoyaltyValue.instance, "Put X +1/+1 counters on it"
        )));

        // −8: Search your library for an instant or sorcery card that shares a color with this planeswalker, exile that card, then shuffle. You may cast that card without paying its mana cost.
        this.addAbility(new LoyaltyAbility(new KasminaEnigmaSageSearchEffect(), -8));
    }

    private KasminaEnigmaSage(final KasminaEnigmaSage card) {
        super(card);
    }

    @Override
    public KasminaEnigmaSage copy() {
        return new KasminaEnigmaSage(this);
    }
}

class KasminaEnigmaSageGainAbilitiesEffect extends ContinuousEffectImpl {

    KasminaEnigmaSageGainAbilitiesEffect() {
        super(Duration.WhileOnBattlefield, Layer.AbilityAddingRemovingEffects_6, SubLayer.NA, Outcome.AddAbility);
        staticText = "each other planeswalker you control has the loyalty abilities of {this}";
    }

    private KasminaEnigmaSageGainAbilitiesEffect(final KasminaEnigmaSageGainAbilitiesEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent perm = source.getSourcePermanentIfItStillExists(game);
        if (perm == null) {
            return true;
        }
        List<Ability> loyaltyAbilities = perm
                .getAbilities(game)
                .stream()
                .filter(LoyaltyAbility.class::isInstance)
                .collect(Collectors.toList());
        for (Permanent permanent : game.getState().getBattlefield().getActivePermanents(
                StaticFilters.FILTER_CONTROLLED_PERMANENT_PLANESWALKER,
                source.getControllerId(), source, game
        )) {
            if (permanent == null || permanent == perm) {
                continue;
            }
            for (Ability ability : loyaltyAbilities) {
                permanent.addAbility(ability, source.getSourceId(), game);
            }
        }
        return true;
    }

    @Override
    public KasminaEnigmaSageGainAbilitiesEffect copy() {
        return new KasminaEnigmaSageGainAbilitiesEffect(this);
    }
}

class KasminaEnigmaSageSearchEffect extends OneShotEffect {

    KasminaEnigmaSageSearchEffect() {
        super(Outcome.Benefit);
        staticText = "Search your library for an instant or sorcery card that shares a color with this planeswalker, " +
                "exile that card, then shuffle. You may cast that card without paying its mana cost.";
    }

    private KasminaEnigmaSageSearchEffect(final KasminaEnigmaSageSearchEffect effect) {
        super(effect);
    }

    @Override
    public KasminaEnigmaSageSearchEffect copy() {
        return new KasminaEnigmaSageSearchEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent permanent = source.getSourcePermanentOrLKI(game);
        if (controller == null || permanent == null) {
            return false;
        }
        FilterCard filter = new FilterInstantOrSorceryCard(
                "an instant, or sorcery card which shares a color with " + permanent.getLogName()
        );
        filter.add(new SharesColorPredicate(permanent.getColor(game)));
        TargetCardInLibrary target = new TargetCardInLibrary(filter);
        controller.searchLibrary(target, source, game);
        Card card = controller.getLibrary().getCard(target.getFirstTarget(), game);
        if (card != null) {
            controller.moveCards(card, Zone.EXILED, source, game);
        }
        controller.shuffleLibrary(source, game);
        if (card == null || !controller.chooseUse(
                Outcome.PlayForFree, "Cast " + card.getName() + " without paying its mana cost?", source, game
        )) {
            return true;
        }
        game.getState().setValue("PlayFromNotOwnHandZone" + card.getId(), Boolean.TRUE);
        Boolean cardWasCast = controller.cast(controller.chooseAbilityForCast(card, game, true),
                game, true, new ApprovingObject(source, game));
        game.getState().setValue("PlayFromNotOwnHandZone" + card.getId(), null);
        return true;
    }
}
