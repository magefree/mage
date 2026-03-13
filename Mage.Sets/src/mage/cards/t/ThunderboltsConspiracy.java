package mage.cards.t;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.AddCardSubTypeTargetEffect;
import mage.abilities.keyword.FlashAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.counters.Counters;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;
import mage.util.CardUtil;

/**
 *
 * @author muz
 */
public final class ThunderboltsConspiracy extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent(SubType.VILLAIN, "a Villain you control");

    public ThunderboltsConspiracy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{B}");

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // Whenever a Villain you control dies, return it to the battlefield under its owner's
        // control with a finality counter on it. That creature is a Hero in addition to its other types.
        this.addAbility(new DiesCreatureTriggeredAbility(
            new ThunderboltsConspiracyEffect(), false, filter, true
        ));
    }

    private ThunderboltsConspiracy(final ThunderboltsConspiracy card) {
        super(card);
    }

    @Override
    public ThunderboltsConspiracy copy() {
        return new ThunderboltsConspiracy(this);
    }
}

class ThunderboltsConspiracyEffect extends OneShotEffect {

    ThunderboltsConspiracyEffect() {
        super(Outcome.PutCardInPlay);
        this.staticText = "return it to the battlefield under its owner's control " +
            "with a finality counter on it. That creature is a Hero in addition to its other types";
    }

    private ThunderboltsConspiracyEffect(final ThunderboltsConspiracyEffect effect) {
        super(effect);
    }

    @Override
    public ThunderboltsConspiracyEffect copy() {
        return new ThunderboltsConspiracyEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Card card = game.getCard(getTargetPointer().getFirst(game, source));
        if (controller == null || card == null || !card.isPermanent(game)) {
            return false;
        }

        Player owner = game.getPlayer(card.getOwnerId());
        if (owner == null) {
            return false;
        }

        Counters countersToAdd = new Counters();
        countersToAdd.addCounter(CounterType.FINALITY.createInstance());
        game.setEnterWithCounters(card.getId(), countersToAdd);
        owner.moveCards(card, Zone.BATTLEFIELD, source, game);
        Permanent permanent = CardUtil.getPermanentFromCardPutToBattlefield(card, game);
        game.addEffect(new AddCardSubTypeTargetEffect(
            SubType.HERO, Duration.Custom
        ).setTargetPointer(new FixedTarget(permanent, game)), source);

        return true;
    }
}
