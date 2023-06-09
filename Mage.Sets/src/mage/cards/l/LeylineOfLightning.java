package mage.cards.l;

import mage.abilities.Ability;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.costs.Cost;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.LeylineAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetPlayerOrPlaneswalker;
import mage.util.ManaUtil;

import java.util.UUID;

/**
 * @author emerald000
 */
public final class LeylineOfLightning extends CardImpl {

    public LeylineOfLightning(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{R}{R}");

        // If Leyline of Lightning is in your opening hand, you may begin the game with it on the battlefield.
        this.addAbility(LeylineAbility.getInstance());

        // Whenever you cast a spell, you may pay {1}. If you do, Leyline of Lightning deals 1 damage to target player.
        Ability ability = new SpellCastControllerTriggeredAbility(new LeylineOfLightningEffect(), true);
        ability.addTarget(new TargetPlayerOrPlaneswalker());
        this.addAbility(ability);
    }

    private LeylineOfLightning(final LeylineOfLightning card) {
        super(card);
    }

    @Override
    public LeylineOfLightning copy() {
        return new LeylineOfLightning(this);
    }
}

class LeylineOfLightningEffect extends DamageTargetEffect {

    LeylineOfLightningEffect() {
        super(1);
        this.staticText = "you may pay {1}. If you do, {this} deals 1 damage to target player or planeswalker.";
    }

    LeylineOfLightningEffect(final LeylineOfLightningEffect effect) {
        super(effect);
    }

    @Override
    public LeylineOfLightningEffect copy() {
        return new LeylineOfLightningEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            Cost cost = ManaUtil.createManaCost(1, false);
            if (cost.pay(source, game, source, source.getControllerId(), false, null)) {
                super.apply(game, source);
            }
            return true;
        }
        return false;
    }
}
