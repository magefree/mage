package mage.cards.k;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.CardsInControllerGraveyardCount;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.SetPowerSourceEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.filter.common.FilterInstantOrSorceryCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetDiscard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class KineticAugur extends CardImpl {

    private static final FilterCard filter = new FilterInstantOrSorceryCard("instant and sorcery cards");
    private static final DynamicValue xValue = new CardsInControllerGraveyardCount(filter);

    public KineticAugur(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SHAMAN);
        this.power = new MageInt(0);
        this.toughness = new MageInt(4);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Kinetic Augur's power is equal to the number of instant and sorcery cards in your graveyard.
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new SetPowerSourceEffect(xValue, Duration.EndOfGame)));

        // When Kinetic Augur enters the battlefield, discard up to two cards, then draw that many cards.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new KineticAugurEffect()));
    }

    private KineticAugur(final KineticAugur card) {
        super(card);
    }

    @Override
    public KineticAugur copy() {
        return new KineticAugur(this);
    }
}

class KineticAugurEffect extends OneShotEffect {

    KineticAugurEffect() {
        super(Outcome.Benefit);
        staticText = "discard up to two cards, then draw that many cards";
    }

    private KineticAugurEffect(final KineticAugurEffect effect) {
        super(effect);
    }

    @Override
    public KineticAugurEffect copy() {
        return new KineticAugurEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null || player.getHand().isEmpty()) {
            return false;
        }
        TargetDiscard target = new TargetDiscard(0, 2, StaticFilters.FILTER_CARD, player.getId());
        player.choose(Outcome.AIDontUseIt, player.getHand(), target, game);
        int discarded = player.discard(new CardsImpl(target.getTargets()), source, game).size();
        if (discarded > 0) {
            player.drawCards(discarded, source.getSourceId(), game);
        }
        return true;
    }
}