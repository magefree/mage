package mage.game.permanent.token;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.common.AttacksAttachedTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author muz
 */
public final class ContractToken extends TokenImpl {

    public ContractToken() {
        super(
            "Contract",
            "white Aura enchantment token named Contract attached to target creature an"
              + " opponent controls. The token has enchant creature and \"Whenever"
              + " enchanted creature attacks, it gets +2/+0 until end of turn if it's"
              + " attacking one of your opponents. Otherwise, its controller loses 2 life.\""
        );
        cardType.add(CardType.ENCHANTMENT);
        color.setWhite(true);
        subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        Ability ability = new EnchantAbility(auraTarget);
        ability.addTarget(auraTarget);
        ability.addEffect(new AttachEffect(Outcome.BoostCreature));
        this.addAbility(ability);

        // Whenever enchanted creature attacks, it gets +2/+0 until end of turn if it's
        // attacking one of your opponents. Otherwise, its controller loses 2 life.
        this.addAbility(new AttacksAttachedTriggeredAbility(
            new ContractTokenEffect(), AttachmentType.AURA, false, SetTargetPointer.PERMANENT
        ));
    }

    private ContractToken(final ContractToken token) {
        super(token);
    }

    public ContractToken copy() {
        return new ContractToken(this);
    }
}

class ContractTokenEffect extends OneShotEffect {

    ContractTokenEffect() {
        super(Outcome.Benefit);
        staticText = "it gets +2/+0 until end of turn if it's attacking one of your opponents. "
            + "Otherwise, its controller loses 2 life";
    }

    private ContractTokenEffect(final ContractTokenEffect effect) {
        super(effect);
    }

    @Override
    public ContractTokenEffect copy() {
        return new ContractTokenEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent enchantedCreature = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (enchantedCreature == null) {
            return false;
        }
        UUID defendingPlayerId = game.getCombat().getDefendingPlayerId(enchantedCreature.getId(), game);
        if (defendingPlayerId != null && game.getOpponents(source.getControllerId()).contains(defendingPlayerId)) {
            game.addEffect(new BoostTargetEffect(2, 0, Duration.EndOfTurn)
                .setTargetPointer(new FixedTarget(enchantedCreature, game)), source);
        } else {
            Player controller = game.getPlayer(enchantedCreature.getControllerId());
            if (controller != null) {
                controller.loseLife(2, game, source, false);
            }
        }
        return true;
    }
}
