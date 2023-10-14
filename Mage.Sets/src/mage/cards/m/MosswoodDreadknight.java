package mage.cards.m;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.AdventureCard;
import mage.cards.Card;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class MosswoodDreadknight extends AdventureCard {


    public MosswoodDreadknight(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, new CardType[]{CardType.SORCERY}, "{1}{G}", "Dread Whispers", "{1}{B}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // When Mosswood Dreadknight dies, you may cast it from your graveyard as an Adventure until the end of your next turn.
        this.addAbility(new DiesSourceTriggeredAbility(new MosswoodDreadknightEffect()));

        // Dread Whispers
        // You draw a card and you lose 1 life.
        this.getSpellCard().getSpellAbility().addEffect(new DrawCardSourceControllerEffect(1, "you"));
        this.getSpellCard().getSpellAbility().addEffect(new LoseLifeSourceControllerEffect(1).concatBy("and"));

        this.finalizeAdventure();
    }

    private MosswoodDreadknight(final MosswoodDreadknight card) {
        super(card);
    }

    @Override
    public MosswoodDreadknight copy() {
        return new MosswoodDreadknight(this);
    }
}

class MosswoodDreadknightEffect extends AsThoughEffectImpl {

    MosswoodDreadknightEffect() {
        super(AsThoughEffectType.CAST_ADVENTURE_FROM_NOT_OWN_HAND_ZONE, Duration.UntilEndOfYourNextTurn, Outcome.Benefit);
        staticText = "you may cast it from your graveyard as an Adventure until the end of your next turn";
    }

    private MosswoodDreadknightEffect(final MosswoodDreadknightEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public MosswoodDreadknightEffect copy() {
        return new MosswoodDreadknightEffect(this);
    }

    @Override
    public boolean applies(UUID sourceId, Ability source, UUID affectedControllerId, Game game) {
        if (!source.isControlledBy(affectedControllerId)) {
            return false;
        }
        Card card = game.getCard(sourceId);
        if (card == null || card.getMainCard() == null || !card.getMainCard().getId().equals(source.getSourceId())) {
            return false;
        }

        Card sourceCard = game.getCard(source.getSourceId());

        return sourceCard != null
                && game.getState().getZone(source.getSourceId()) == Zone.GRAVEYARD
                && source.getSourceObjectZoneChangeCounter() == sourceCard.getZoneChangeCounter(game);
    }
}
