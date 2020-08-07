package mage.cards.l;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.keyword.AftermathAbility;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.cards.SplitCard;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SpellAbilityType;
import mage.constants.TargetController;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.TargetPermanent;
import mage.target.common.TargetDiscard;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class LeaveChance extends SplitCard {

    private static final FilterPermanent filter = new FilterPermanent("permanents you own");

    static {
        filter.add(TargetController.YOU.getOwnerPredicate());
    }

    public LeaveChance(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, new CardType[]{CardType.SORCERY}, "{1}{W}", "{3}{R}", SpellAbilityType.SPLIT_AFTERMATH);

        // Return any number of target permanents you own to your hand.
        getLeftHalfCard().getSpellAbility().addEffect(new ReturnToHandTargetEffect());
        getLeftHalfCard().getSpellAbility().addTarget(new TargetPermanent(0, Integer.MAX_VALUE, filter, false));

        // Chance
        // Sorcery
        // Aftermath
        getRightHalfCard().addAbility(new AftermathAbility().setRuleAtTheTop(true));

        // Discard any number of cards, then draw that many cards.
        getRightHalfCard().getSpellAbility().addEffect(new ChanceEffect());
    }

    private LeaveChance(final LeaveChance card) {
        super(card);
    }

    @Override
    public LeaveChance copy() {
        return new LeaveChance(this);
    }
}

class ChanceEffect extends OneShotEffect {

    ChanceEffect() {
        super(Outcome.DrawCard);
        this.staticText = "Discard any number of cards, then draw that many cards";
    }

    private ChanceEffect(final ChanceEffect effect) {
        super(effect);
    }

    @Override
    public ChanceEffect copy() {
        return new ChanceEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        TargetCard target = new TargetDiscard(0, controller.getHand().size(), StaticFilters.FILTER_CARD_CARDS, controller.getId());
        controller.chooseTarget(outcome, controller.getHand(), target, source, game);
        int amount = controller.discard(new CardsImpl(target.getTargets()), source, game).size();
        controller.drawCards(amount, source.getSourceId(), game);
        return true;
    }
}
