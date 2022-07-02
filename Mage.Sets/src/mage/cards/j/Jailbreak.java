package mage.cards.j;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.common.FilterPermanentCard;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInOpponentsGraveyard;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author weirddan455
 */
public final class Jailbreak extends CardImpl {

    private static final FilterPermanentCard filter = new FilterPermanentCard("permanent card in an opponent's graveyard");

    public Jailbreak(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{W}");

        // Return target permanent card in an opponent's graveyard to the battlefield under their control.
        // When that permanent enters the battlefield, return up to one target permanent card with equal or lesser mana value from your graveyard to the battlefield.
        this.getSpellAbility().addEffect(new JailbreakEffect());
        this.getSpellAbility().addTarget(new TargetCardInOpponentsGraveyard(filter));
    }

    private Jailbreak(final Jailbreak card) {
        super(card);
    }

    @Override
    public Jailbreak copy() {
        return new Jailbreak(this);
    }
}

class JailbreakEffect extends OneShotEffect {

    public JailbreakEffect() {
        super(Outcome.PutCardInPlay);
        this.staticText = "Return target permanent card in an opponent's graveyard to the battlefield under their control. " +
                "When that permanent enters the battlefield, return up to one target permanent card with equal or lesser mana value from your graveyard to the battlefield.";
    }

    private JailbreakEffect(final JailbreakEffect effect) {
        super(effect);
    }

    @Override
    public JailbreakEffect copy() {
        return new JailbreakEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Card card = game.getCard(source.getFirstTarget());
        if (card == null) {
            return false;
        }
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        if (!player.moveCards(card, Zone.BATTLEFIELD, source, game, false, false, true, null)) {
            return false;
        }
        FilterPermanentCard filter = new FilterPermanentCard("permanent card with equal or lesser mana value from your graveyard");
        filter.add(new ManaValuePredicate(ComparisonType.FEWER_THAN, card.getManaValue() + 1));
        ReflexiveTriggeredAbility reflexive = new ReflexiveTriggeredAbility(new ReturnFromGraveyardToBattlefieldTargetEffect(), false,
                "When that permanent enters the battlefield, return up to one target permanent card with equal or lesser mana value from your graveyard to the battlefield.");
        reflexive.addTarget(new TargetCardInYourGraveyard(0, 1, filter));
        game.fireReflexiveTriggeredAbility(reflexive, source);
        return true;
    }
}
