package mage.cards.i;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.keyword.FirebendingAbility;
import mage.abilities.keyword.FlashbackAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.common.FilterInstantOrSorceryCard;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class IrohGrandLotus extends CardImpl {

    public IrohGrandLotus(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}{U}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.NOBLE);
        this.subtype.add(SubType.ALLY);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Firebending 2
        this.addAbility(new FirebendingAbility(2));

        // During your turn, each non-Lesson instant and sorcery card in your graveyard has flashback. The flashback cost is equal to that card's mana cost.
        this.addAbility(new SimpleStaticAbility(new IrohGrandLotusEffect(false)));

        // During your turn, each Lesson card in your graveyard has flashback {1}.
        this.addAbility(new SimpleStaticAbility(new IrohGrandLotusEffect(true)));
    }

    private IrohGrandLotus(final IrohGrandLotus card) {
        super(card);
    }

    @Override
    public IrohGrandLotus copy() {
        return new IrohGrandLotus(this);
    }
}

class IrohGrandLotusEffect extends ContinuousEffectImpl {

    private static final FilterCard filterNonLesson = new FilterInstantOrSorceryCard();
    private static final FilterCard filterLesson = new FilterCard(SubType.LESSON);
    private final boolean isLesson;

    IrohGrandLotusEffect(boolean isLesson) {
        super(Duration.WhileOnBattlefield, Layer.AbilityAddingRemovingEffects_6, SubLayer.NA, Outcome.AddAbility);
        this.isLesson = isLesson;
        if (isLesson) {
            staticText = "during your turn, each Lesson card in your graveyard has flashback {1}";
        } else {
            staticText = "during your turn, each non-Lesson instant and sorcery card in your graveyard has flashback. " +
                    "The flashback cost is equal to that card's mana cost";
        }
    }

    private IrohGrandLotusEffect(final IrohGrandLotusEffect effect) {
        super(effect);
        this.isLesson = effect.isLesson;
    }

    @Override
    public IrohGrandLotusEffect copy() {
        return new IrohGrandLotusEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        if (!game.isActivePlayer(source.getControllerId())) {
            return false;
        }
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        for (Card card : player.getGraveyard().getCards(isLesson ? filterLesson : filterNonLesson, game)) {
            Ability ability = new FlashbackAbility(card, isLesson ? new GenericManaCost(1) : card.getManaCost());
            ability.setSourceId(card.getId());
            ability.setControllerId(card.getOwnerId());
            game.getState().addOtherAbility(card, ability);
        }
        return true;
    }
}
