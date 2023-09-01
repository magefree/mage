package mage.cards.m;

import java.util.UUID;
import mage.ApprovingObject;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
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

/**
 *
 * @author LevelX2
 */
public final class MemoryPlunder extends CardImpl {

    private static final FilterCard filter = new FilterCard("instant or sorcery card from an opponent's graveyard");

    static {
        filter.add(Predicates.or(
                CardType.INSTANT.getPredicate(),
                CardType.SORCERY.getPredicate()));
    }

    public MemoryPlunder(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{U/B}{U/B}{U/B}{U/B}");

        // You may cast target instant or sorcery card from an opponent's graveyard without paying its mana cost.
        this.getSpellAbility().addEffect(new MemoryPlunderEffect());
        this.getSpellAbility().addTarget(new TargetCardInOpponentsGraveyard(filter));

    }

    private MemoryPlunder(final MemoryPlunder card) {
        super(card);
    }

    @Override
    public MemoryPlunder copy() {
        return new MemoryPlunder(this);
    }
}

class MemoryPlunderEffect extends OneShotEffect {

    public MemoryPlunderEffect() {
        super(Outcome.PlayForFree);
        this.staticText = "You may cast target instant or sorcery card from "
                + "an opponent's graveyard without paying its mana cost";
    }

    private MemoryPlunderEffect(final MemoryPlunderEffect effect) {
        super(effect);
    }

    @Override
    public MemoryPlunderEffect copy() {
        return new MemoryPlunderEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Card card = game.getCard(getTargetPointer().getFirst(game, source));
        if (card != null) {
            Player controller = game.getPlayer(source.getControllerId());
            if (controller != null
                    && game.getState().getZone(card.getId()) == Zone.GRAVEYARD
                    && controller.chooseUse(Outcome.PlayForFree, "Cast " + card.getName() + " without paying cost?", source, game)) {
                game.getState().setValue("PlayFromNotOwnHandZone" + card.getId(), Boolean.TRUE);
                Boolean cardWasCast = controller.cast(controller.chooseAbilityForCast(card, game, true),
                        game, true, new ApprovingObject(source, game));
                game.getState().setValue("PlayFromNotOwnHandZone" + card.getId(), null);
                return cardWasCast;
            }
        }
        return false;
    }
}
