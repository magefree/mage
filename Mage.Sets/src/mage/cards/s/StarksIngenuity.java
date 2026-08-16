package mage.cards.s;

import java.util.UUID;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.game.Game;
import mage.players.Player;
import mage.abilities.Ability;
import mage.abilities.common.DrawCardControllerTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.counter.AddCountersAttachedEffect;
import mage.constants.Outcome;
import mage.target.TargetPermanent;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.util.ManaUtil;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 * @author muz
 */
public final class StarksIngenuity extends CardImpl {

    public StarksIngenuity(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{U}");

        this.subtype.add(SubType.AURA);

        // Enchant creature you control
        TargetPermanent auraTarget = new TargetControlledCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        this.addAbility(new EnchantAbility(auraTarget));

        // When this Aura enters, you may pay {X}. If you do, draw X cards.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new StarksIngenuityEffect()));

        // Whenever you draw a card, put a +1/+1 counter on enchanted creature.
        this.addAbility(new DrawCardControllerTriggeredAbility(
            new AddCountersAttachedEffect(CounterType.P1P1.createInstance(), "enchanted creature"), false
        ));
    }

    private StarksIngenuity(final StarksIngenuity card) {
        super(card);
    }

    @Override
    public StarksIngenuity copy() {
        return new StarksIngenuity(this);
    }
}

class StarksIngenuityEffect extends OneShotEffect {

    StarksIngenuityEffect() {
        super(Outcome.Benefit);
        this.staticText = "you may pay {X}. If you do, draw X cards";
    }

    private StarksIngenuityEffect(final StarksIngenuityEffect effect) {
        super(effect);
    }

    @Override
    public StarksIngenuityEffect copy() {
        return new StarksIngenuityEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null || !player.chooseUse(Outcome.Benefit, "Pay {X} to draw X cards?", source, game)) {
            return false;
        }
        int payCount = ManaUtil.playerPaysXGenericMana(true, "Stark's Ingenuity", player, source, game);
        if (payCount > 0) {
            player.drawCards(payCount, source, game);
        }
        return true;
    }
}
