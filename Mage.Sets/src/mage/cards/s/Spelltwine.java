package mage.cards.s;

import java.util.UUID;
import mage.ApprovingObject;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ExileSpellEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInOpponentsGraveyard;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author jeffwadsworth and magenoxx_at_gmail.com
 */
public final class Spelltwine extends CardImpl {

    private static final FilterCard filter = new FilterCard(
            "instant or sorcery card from your graveyard");
    private static final FilterCard filter2 = new FilterCard(
            "instant or sorcery card from an opponent's graveyard");

    static {
        filter.add(Predicates.or(
                CardType.INSTANT.getPredicate(),
                CardType.SORCERY.getPredicate()));

        filter2.add(Predicates.or(
                CardType.INSTANT.getPredicate(),
                CardType.SORCERY.getPredicate()));
    }

    public Spelltwine(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{5}{U}");

        // Exile target instant or sorcery card from your graveyard and target 
        // instant or sorcery card from an opponent's graveyard. Copy those cards. 
        // Cast the copies if able without paying their mana costs. Exile Spelltwine.
        this.getSpellAbility().addEffect(new SpelltwineEffect());
        this.getSpellAbility().addTarget(new TargetCardInYourGraveyard(filter));
        this.getSpellAbility().addTarget(new TargetCardInOpponentsGraveyard(filter2));
        this.getSpellAbility().addEffect(new ExileSpellEffect());

    }

    private Spelltwine(final Spelltwine card) {
        super(card);
    }

    @Override
    public Spelltwine copy() {
        return new Spelltwine(this);
    }
}

class SpelltwineEffect extends OneShotEffect {

    public SpelltwineEffect() {
        super(Outcome.PlayForFree);
        staticText = "Exile target instant or sorcery card from your graveyard and "
                + "target instant or sorcery card from an opponent's graveyard. "
                + "Copy those cards. Cast the copies if able without paying their mana costs";
    }

    private SpelltwineEffect(final SpelltwineEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Card cardOne = game.getCard(source.getTargets().get(0).getFirstTarget());
        Card cardTwo = game.getCard(source.getTargets().get(1).getFirstTarget());
        if (controller != null) {
            if (cardOne != null) {
                controller.moveCards(cardOne, Zone.EXILED, source, game);
            }
            if (cardTwo != null) {
                controller.moveCards(cardTwo, Zone.EXILED, source, game);
            }
            boolean castCardOne = true;
            ApprovingObject approvingObject = new ApprovingObject(source, game);
            if (cardOne != null 
                    && controller.chooseUse(Outcome.Neutral, "Cast the copy of "
                    + cardOne.getName() + " first?", source, game)) {
                Card copyOne = game.copyCard(cardOne, source, controller.getId());
                game.getState().setValue("PlayFromNotOwnHandZone" + copyOne.getId(), Boolean.TRUE);
                controller.cast(controller.chooseAbilityForCast(copyOne, game, true),
                        game, true, approvingObject);
                game.getState().setValue("PlayFromNotOwnHandZone" + copyOne.getId(), null);
                castCardOne = false;
            }
            if (cardTwo != null) {
                Card copyTwo = game.copyCard(cardTwo, source, controller.getId());
                game.getState().setValue("PlayFromNotOwnHandZone" + copyTwo.getId(), Boolean.TRUE);
                controller.cast(controller.chooseAbilityForCast(copyTwo, game, true),
                        game, true, approvingObject);
                game.getState().setValue("PlayFromNotOwnHandZone" + copyTwo.getId(), null);
            }
            if (cardOne != null 
                    && castCardOne) {
                Card copyOne = game.copyCard(cardOne, source, controller.getId());
                game.getState().setValue("PlayFromNotOwnHandZone" + copyOne.getId(), Boolean.TRUE);
                controller.cast(controller.chooseAbilityForCast(copyOne, game, true),
                        game, true, approvingObject);
                game.getState().setValue("PlayFromNotOwnHandZone" + copyOne.getId(), null);
            }
            return true;
        }
        return false;
    }

    @Override
    public SpelltwineEffect copy() {
        return new SpelltwineEffect(this);
    }
}
