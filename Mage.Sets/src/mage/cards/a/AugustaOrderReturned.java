package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.game.Game;
import mage.target.common.TargetAttackingCreature;
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
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.players.Player;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author muz
 */
public final class AugustaOrderReturned extends CardImpl {

    public AugustaOrderReturned(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.SPIRIT);
        this.subtype.add(SubType.ADVISOR);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Whenever Augusta attacks, each player exiles a card from their graveyard. When one or more nonland cards are exiled this way, put that many +1/+1 counters on target attacking creature.
        Ability ability = new AttacksTriggeredAbility(new AugustaOrderReturnedEffect());
        this.addAbility(ability);
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

    public AugustaOrderReturnedEffect() {
        super(Outcome.Benefit);
        this.staticText = "each player exiles a card from their graveyard. "
            + "When one or more nonland cards are exiled this way, put that "
            + "many +1/+1 counters on target attacking creature";
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
        int nonLands = 0;

        // Each player exiles a card from their graveyard
        for (UUID playerId : game.getState().getPlayersInRange(source.getControllerId(), game)) {
            Player player = game.getPlayer(playerId);
            if (player != null && !player.getGraveyard().isEmpty()) {
                TargetCardInYourGraveyard target = new TargetCardInYourGraveyard();
                target.withNotTarget(true);
                if (player.chooseTarget(Outcome.Exile, target, source, game)) {
                    Card card = game.getCard(target.getFirstTarget());
                    if (card != null && player.moveCards(card, Zone.EXILED, source, game) && !card.isLand(game)) {
                        nonLands++;
                    }
                }
            }
        }

        if (nonLands == 0) {
            return true;
        }

        ReflexiveTriggeredAbility relexAbility = new ReflexiveTriggeredAbility(
            new AddCountersTargetEffect(CounterType.P1P1.createInstance(nonLands)),
            false,
            "When one or more nonland cards are exiled this way, put that many +1/+1 counters on target attacking creature."
        );
        relexAbility.addTarget(new TargetAttackingCreature());

        game.fireReflexiveTriggeredAbility(relexAbility, source);
        return true;
    }

}
