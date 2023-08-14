package mage.cards.v;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.CastCardFromGraveyardThenExileItEffect;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author weirddan455
 */
public final class VoharVodalianDesecrator extends CardImpl {

    public VoharVodalianDesecrator(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{U}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.MERFOLK);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // {T}: Draw a card, then discard a card. If you discarded an instant or sorcery card this way, each opponent loses 1 life and you gain 1 life.
        this.addAbility(new SimpleActivatedAbility(new VoharVodalianDesecratorEffect(), new TapSourceCost()));

        // {2}, Sacrifice Vohar, Vodalian Desecrator: You may cast target instant or sorcery card from your graveyard this turn. If that spell would be put into your graveyard, exile it instead. Activate only as a sorcery.
        Ability ability = new ActivateAsSorceryActivatedAbility(new CastCardFromGraveyardThenExileItEffect()
                .setText("You may cast target instant or sorcery card from your graveyard this turn. If that spell would be put into your graveyard, exile it instead."),
                new GenericManaCost(2)
        );
        ability.addCost(new SacrificeSourceCost());
        ability.addTarget(new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_INSTANT_OR_SORCERY_FROM_YOUR_GRAVEYARD));
        this.addAbility(ability);
    }

    private VoharVodalianDesecrator(final VoharVodalianDesecrator card) {
        super(card);
    }

    @Override
    public VoharVodalianDesecrator copy() {
        return new VoharVodalianDesecrator(this);
    }
}

class VoharVodalianDesecratorEffect extends OneShotEffect {

    public VoharVodalianDesecratorEffect() {
        super(Outcome.DrawCard);
        this.staticText = "Draw a card, then discard a card. If you discarded an instant or sorcery card this way, each opponent loses 1 life and you gain 1 life.";
    }

    private VoharVodalianDesecratorEffect(final VoharVodalianDesecratorEffect effect) {
        super(effect);
    }

    @Override
    public VoharVodalianDesecratorEffect copy() {
        return new VoharVodalianDesecratorEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        controller.drawCards(1, source, game);
        for (UUID cardId : controller.discard(1, false, false, source, game)) {
            Card card = game.getCard(cardId);
            if (card != null && card.isInstantOrSorcery(game)) {
                for (UUID opponentId : game.getOpponents(controller.getId())) {
                    Player opponent = game.getPlayer(opponentId);
                    if (opponent != null) {
                        opponent.loseLife(1, game, source, false);
                    }
                }
                controller.gainLife(1, game, source);
                break;
            }
        }
        return true;
    }
}
