package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetAttackingCreature;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author Susurrus
 */
public final class AugustaOrderReturned extends CardImpl {

    public AugustaOrderReturned(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.SPIRIT);
        this.subtype.add(SubType.ADVISOR);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Flying, vigilance
        this.addAbility(FlyingAbility.getInstance());
        this.addAbility(VigilanceAbility.getInstance());

        // Whenever Augusta attacks, each player exiles a card from their graveyard. When one or more nonland cards are exiled this way, put that many +1/+1 counters on target attacking creature.
        this.addAbility(new AttacksTriggeredAbility(new AugustaOrderReturnedEffect(), false));
    }

    private AugustaOrderReturned(final AugustaOrderReturned card) {
        super(card);
    }

    @Override
    public AugustaOrderReturned copy() {
        return new AugustaOrderReturned(this);
    }
}

class AugustaOrderReturnedEffect extends OneShotEffect {

    AugustaOrderReturnedEffect() {
        super(Outcome.Benefit);
        staticText = "each player exiles a card from their graveyard. When one or more nonland cards are exiled " +
                "this way, put that many +1/+1 counters on target attacking creature";
    }

    private AugustaOrderReturnedEffect(final AugustaOrderReturnedEffect effect) {
        super(effect);
    }

    @Override
    public AugustaOrderReturnedEffect copy() {
        return new AugustaOrderReturnedEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int nonlandCount = 0;
        for (UUID playerId : game.getState().getPlayersInRange(source.getControllerId(), game)) {
            Player player = game.getPlayer(playerId);
            if (player == null || player.getGraveyard().isEmpty()) {
                continue;
            }
            TargetCardInYourGraveyard target = new TargetCardInYourGraveyard();
            target.withNotTarget(true);
            if (!player.chooseTarget(Outcome.Exile, target, source, game)) {
                continue;
            }
            Card card = game.getCard(target.getFirstTarget());
            if (card == null) {
                continue;
            }
            boolean nonland = !card.isLand(game);
            if (player.moveCards(card, Zone.EXILED, source, game) && nonland) {
                nonlandCount++;
            }
        }
        if (nonlandCount > 0) {
            ReflexiveTriggeredAbility ability = new ReflexiveTriggeredAbility(
                    new AddCountersTargetEffect(CounterType.P1P1.createInstance(nonlandCount)), false,
                    "When one or more nonland cards are exiled this way, put that many +1/+1 counters on target attacking creature."
            );
            ability.addTarget(new TargetAttackingCreature());
            game.fireReflexiveTriggeredAbility(ability, source);
        }
        return true;
    }
}
