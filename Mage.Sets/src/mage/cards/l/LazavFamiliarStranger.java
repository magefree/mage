package mage.cards.l;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.CommittedCrimeTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.PermanentCard;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInGraveyard;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class LazavFamiliarStranger extends CardImpl {

    public LazavFamiliarStranger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.SHAPESHIFTER);
        this.power = new MageInt(1);
        this.toughness = new MageInt(4);

        // Whenever you commit a crime, put a +1/+1 counter on Lazav, Familiar Stranger. Then you may exile a card from a graveyard. If a creature card was exiled this way, you may have Lazav become a copy of that card until end of turn. This ability triggers only once each turn.
        Ability ability = new CommittedCrimeTriggeredAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance())
        ).setTriggersOnceEachTurn(true);
        ability.addEffect(new LazavFamiliarStrangerEffect());
        this.addAbility(ability);
    }

    private LazavFamiliarStranger(final LazavFamiliarStranger card) {
        super(card);
    }

    @Override
    public LazavFamiliarStranger copy() {
        return new LazavFamiliarStranger(this);
    }
}

class LazavFamiliarStrangerEffect extends OneShotEffect {

    LazavFamiliarStrangerEffect() {
        super(Outcome.PutCreatureInPlay);
        staticText = "Then you may exile a card from a graveyard. "
                + "If a creature card was exiled this way, "
                + "you may have {this} become a copy of that card until end of turn.";
    }

    private LazavFamiliarStrangerEffect(final LazavFamiliarStrangerEffect effect) {
        super(effect);
    }

    @Override
    public LazavFamiliarStrangerEffect copy() {
        return new LazavFamiliarStrangerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        TargetCard target = new TargetCardInGraveyard(0, 1);
        target.withNotTarget(true);
        player.choose(outcome, target, source, game);
        Card card = game.getCard(target.getFirstTarget());
        if (card == null) {
            return false;
        }
        boolean flag = card.isCreature(game);
        player.moveCards(card, Zone.EXILED, source, game);
        if (!flag) {
            return true;
        }
        Permanent lazav = source.getSourcePermanentIfItStillExists(game);
        if (lazav == null) {
            return true;
        }
        if (player.chooseUse(
                Outcome.PutCreatureInPlay,
                "Do you want " + lazav.getLogName() + " to become a copy of " + card.getLogName() + " until end of turn?",
                source, game
        )) {
            game.copyPermanent(
                    Duration.EndOfTurn,
                    new PermanentCard(card, source.getControllerId(), game),
                    source.getSourceId(), source, null
            );
        }
        return true;
    }

}