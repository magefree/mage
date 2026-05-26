package mage.cards.c;

import mage.MageItem;
import mage.abilities.Ability;
import mage.abilities.common.delayed.AtTheBeginOfPlayersNextEndStepDelayedTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.SacrificeTargetEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.FixedTarget;
import mage.util.CardUtil;

import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author TheElk801
 */
public final class ComeBackWrong extends CardImpl {

    public ComeBackWrong(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{B}");

        // Destroy target creature. If a creature card is put into a graveyard this way, return it to the battlefield under your control. Sacrifice it at the beginning of your next end step.
        this.getSpellAbility().addEffect(new ComeBackWrongEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private ComeBackWrong(final ComeBackWrong card) {
        super(card);
    }

    @Override
    public ComeBackWrong copy() {
        return new ComeBackWrong(this);
    }
}

class ComeBackWrongEffect extends OneShotEffect {

    ComeBackWrongEffect() {
        super(Outcome.Neutral);
        staticText = "destroy target creature. If a creature card is put into a graveyard this way, " +
                "return it to the battlefield under your control. Sacrifice it at the beginning of your next end step";
    }

    private ComeBackWrongEffect(final ComeBackWrongEffect effect) {
        super(effect);
    }

    @Override
    public ComeBackWrongEffect copy() {
        return new ComeBackWrongEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (permanent == null) {
            return false;
        }
        permanent.destroy(source, game);
        game.processAction();
        Set<Card> cards = CardUtil.getAllCardsFromPermanentLeftBattlefield(permanent, game)
                .stream()
                .map(MageItem::getId)
                .filter(id -> Zone.GRAVEYARD.match(game.getState().getZone(id)))
                .map(game::getCard)
                .filter(Objects::nonNull)
                .filter(c -> c.isCreature(game))
                .collect(Collectors.toSet());
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return true;
        }
        player.moveCards(cards, Zone.BATTLEFIELD, source, game);
        for (Card card : cards) {
            Permanent creature = CardUtil.getPermanentFromCardPutToBattlefield(card, game);
            if (creature != null) {
                game.addDelayedTriggeredAbility(new AtTheBeginOfPlayersNextEndStepDelayedTriggeredAbility(
                        new SacrificeTargetEffect("sacrifice it")
                                .setTargetPointer(new FixedTarget(creature, game)),
                        player.getId()
                ).setTriggerPhrase("At the beginning of your next end step, "), source);
            }
        }
        return true;
    }
}
