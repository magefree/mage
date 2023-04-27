package mage.cards.m;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.PreventCombatDamageBySourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MtendaLion extends CardImpl {

    public MtendaLion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{G}");

        this.subtype.add(SubType.CAT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Whenever Mtenda Lion attacks, defending player may pay {U}. If that player does, prevent all combat damage that would be dealt by Mtenda Lion this turn.
        this.addAbility(new AttacksTriggeredAbility(new MtendaLionEffect(), false));
    }

    private MtendaLion(final MtendaLion card) {
        super(card);
    }

    @Override
    public MtendaLion copy() {
        return new MtendaLion(this);
    }
}

class MtendaLionEffect extends OneShotEffect {

    MtendaLionEffect() {
        super(Outcome.Benefit);
        staticText = "defending player may pay {U}. If that player does, " +
                "prevent all combat damage that would be dealt by {this} this turn.";
    }

    private MtendaLionEffect(final MtendaLionEffect effect) {
        super(effect);
    }

    @Override
    public MtendaLionEffect copy() {
        return new MtendaLionEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(game.getCombat().getDefendingPlayerId(source.getSourceId(), game));
        if (player == null) {
            return false;
        }
        Cost cost = new ManaCostsImpl<>("{U}");
        if (!player.chooseUse(outcome, "Pay {U} to prevent damage?", source, game)
                || !cost.pay(source, game, source, player.getId(), false)) {
            return false;
        }
        game.addEffect(new PreventCombatDamageBySourceEffect(Duration.EndOfTurn), source);
        return true;
    }
}
