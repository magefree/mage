package mage.cards.p;

import mage.ApprovingObject;
import mage.abilities.Ability;
import mage.abilities.costs.common.TapTargetCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ExileSpellEffect;
import mage.abilities.keyword.ReplicateAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInGraveyard;
import mage.target.common.TargetControlledPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PsionicRitual extends CardImpl {

    private static final FilterControlledPermanent filter
            = new FilterControlledPermanent(SubType.HORROR, "an untapped Horror you control");

    static {
        filter.add(TappedPredicate.UNTAPPED);
    }

    public PsionicRitual(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{U}{U}");

        // Replicate—Tap an untapped Horror you control.
        this.addAbility(new ReplicateAbility(new TapTargetCost(new TargetControlledPermanent(filter))));

        // Exile target instant or sorcery card from a graveyard and copy it. You may cast the copy without paying its mana cost.
        this.getSpellAbility().addEffect(new PsionicRitualEffect());
        this.getSpellAbility().addTarget(new TargetCardInGraveyard(StaticFilters.FILTER_CARD_INSTANT_OR_SORCERY));

        // Exile Psionic Ritual.
        this.getSpellAbility().addEffect(new ExileSpellEffect().concatBy("<br>"));
    }

    private PsionicRitual(final PsionicRitual card) {
        super(card);
    }

    @Override
    public PsionicRitual copy() {
        return new PsionicRitual(this);
    }
}

class PsionicRitualEffect extends OneShotEffect {

    PsionicRitualEffect() {
        super(Outcome.Benefit);
        staticText = "exile target instant or sorcery card from a graveyard " +
                "and copy it. You may cast the copy without paying its mana cost";
    }

    private PsionicRitualEffect(final PsionicRitualEffect effect) {
        super(effect);
    }

    @Override
    public PsionicRitualEffect copy() {
        return new PsionicRitualEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Card card = game.getCard(getTargetPointer().getFirst(game, source));
        if (player == null || card == null) {
            return false;
        }
        player.moveCards(card, Zone.EXILED, source, game);
        Card copiedCard = game.copyCard(card, source, source.getControllerId());
        if (copiedCard == null) {
            return false;
        }
        game.getExile().add(source.getSourceId(), "", copiedCard);
        game.getState().setZone(copiedCard.getId(), Zone.EXILED);
        if (copiedCard.getSpellAbility() == null || !player.chooseUse(
                outcome, "Cast the copied card without paying mana cost?", source, game
        )) {
            return false;
        }
        game.getState().setValue("PlayFromNotOwnHandZone" + copiedCard.getId(), Boolean.TRUE);
        player.cast(
                player.chooseAbilityForCast(copiedCard, game, true),
                game, true, new ApprovingObject(source, game)
        );
        game.getState().setValue("PlayFromNotOwnHandZone" + copiedCard.getId(), null);
        return true;
    }
}
