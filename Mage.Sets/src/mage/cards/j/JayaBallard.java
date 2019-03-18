
package mage.cards.j;

import java.util.UUID;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.common.PlaneswalkerEntersWithLoyaltyCountersAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.mana.AddConditionalManaEffect;
import mage.abilities.effects.common.GetEmblemEffect;
import mage.abilities.mana.builder.common.InstantOrSorcerySpellManaBuilder;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.game.command.emblems.JayaBallardEmblem;
import mage.players.Player;
import mage.target.common.TargetDiscard;
import mage.watchers.common.CastFromGraveyardWatcher;

/**
 *
 * @author LevelX2
 */
public final class JayaBallard extends CardImpl {

    public JayaBallard(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{2}{R}{R}{R}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.JAYA);
        this.addAbility(new PlaneswalkerEntersWithLoyaltyCountersAbility(5));

        // +1: Add {R}{R}{R}. Spend this mana only to cast instant or sorcery spells.
        this.addAbility(new LoyaltyAbility(new AddConditionalManaEffect(Mana.RedMana(3), new InstantOrSorcerySpellManaBuilder()), 1));

        // +1: Discard up to three cards, then draw that many cards.
        this.addAbility(new LoyaltyAbility(new JayaBallardDiscardDrawEffect(), 1));

        // −8: You get an emblem with "You may cast instant and sorcery cards from your graveyard. If a card cast this way would be put into your graveyard, exile it instead."
        this.addAbility(new LoyaltyAbility(new GetEmblemEffect(new JayaBallardEmblem()), -8), new CastFromGraveyardWatcher());
    }

    public JayaBallard(final JayaBallard card) {
        super(card);
    }

    @Override
    public JayaBallard copy() {
        return new JayaBallard(this);
    }
}

class JayaBallardDiscardDrawEffect extends OneShotEffect {

    public JayaBallardDiscardDrawEffect() {
        super(Outcome.Detriment);
        this.staticText = "Discard up to three cards, then draw that many cards";
    }

    public JayaBallardDiscardDrawEffect(final JayaBallardDiscardDrawEffect effect) {
        super(effect);
    }

    @Override
    public JayaBallardDiscardDrawEffect copy() {
        return new JayaBallardDiscardDrawEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            TargetDiscard target = new TargetDiscard(0, 3, new FilterCard(), controller.getId());
            target.choose(outcome, controller.getId(), source.getSourceId(), game);
            int count = 0;
            for (UUID cardId : target.getTargets()) {
                Card card = game.getCard(cardId);
                if (card != null) {
                    controller.discard(card, source, game);
                    count++;
                }
            }
            controller.drawCards(count, game);
            return true;
        }
        return false;
    }
}
