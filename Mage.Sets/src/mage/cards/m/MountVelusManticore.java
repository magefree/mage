package mage.cards.m;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfCombatTriggeredAbility;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetAnyTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MountVelusManticore extends CardImpl {

    public MountVelusManticore(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT, CardType.CREATURE}, "{2}{R}{R}");

        this.subtype.add(SubType.MANTICORE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // At the beginning of combat on your turn, you may discard a card. When you do, Mount Velus Manticore deals X damage to any target, where X is the number of card types the discarded card has.
        this.addAbility(new BeginningOfCombatTriggeredAbility(
                new MountVelusManticoreEffect(), TargetController.YOU, false
        ));
    }

    private MountVelusManticore(final MountVelusManticore card) {
        super(card);
    }

    @Override
    public MountVelusManticore copy() {
        return new MountVelusManticore(this);
    }
}

class MountVelusManticoreEffect extends OneShotEffect {

    MountVelusManticoreEffect() {
        super(Outcome.Benefit);
        staticText = "you may discard a card. When you do, {this} deals X damage to any target, " +
                "where X is the number of card types the discarded card has";
    }

    private MountVelusManticoreEffect(final MountVelusManticoreEffect effect) {
        super(effect);
    }

    @Override
    public MountVelusManticoreEffect copy() {
        return new MountVelusManticoreEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Card card = player.discard(0, 1, false, source, game).getRandom(game);
        if (card == null) {
            return false;
        }
        ReflexiveTriggeredAbility ability = new ReflexiveTriggeredAbility(
                new DamageTargetEffect(card.getCardType(game).size()), false, "{this} deals X damage " +
                "to any target, where X is the number of card types the discarded card has"
        );
        ability.addTarget(new TargetAnyTarget());
        game.fireReflexiveTriggeredAbility(ability, source);
        return true;
    }
}
