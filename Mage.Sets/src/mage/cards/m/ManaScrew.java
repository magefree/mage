
package mage.cards.m;

import java.util.UUID;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.mana.BasicManaEffect;
import mage.abilities.mana.ActivatedManaAbilityImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author L_J
 */
public final class ManaScrew extends CardImpl {

    public ManaScrew(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}");

        // {1}: Flip a coin. If you win the flip, add {C}{C}. Activate this ability only any time you could cast an instant.
        this.addAbility(new ManaScrewAbility());
    }

    public ManaScrew(final ManaScrew card) {
        super(card);
    }

    @Override
    public ManaScrew copy() {
        return new ManaScrew(this);
    }
}

class ManaScrewAbility extends ActivatedManaAbilityImpl {

    public ManaScrewAbility() {
        super(Zone.BATTLEFIELD, new ManaScrewEffect(), new GenericManaCost(1));
        this.netMana.add(new Mana(0, 0, 0, 0, 0, 2, 0, 0));
    }

    public ManaScrewAbility(final ManaScrewAbility ability) {
        super(ability);
    }

    @Override
    public ActivationStatus canActivate(UUID playerId, Game game) {
        Player player = game.getPlayer(playerId);
        if (player != null && !player.isInPayManaMode()) {
            return super.canActivate(playerId, game);
        }
        return ActivationStatus.getFalse();
    }

    @Override
    public ManaScrewAbility copy() {
        return new ManaScrewAbility(this);
    }

    @Override
    public String getRule() {
        return super.getRule() + " Activate this ability only any time you could cast an instant.";
    }
}

class ManaScrewEffect extends BasicManaEffect {

    public ManaScrewEffect() {
        super(Mana.ColorlessMana(2));
        this.staticText = "Flip a coin. If you win the flip, add {C}{C}";
    }

    public ManaScrewEffect(final ManaScrewEffect effect) {
        super(effect);
        this.manaTemplate = effect.manaTemplate.copy();
    }

    @Override
    public ManaScrewEffect copy() {
        return new ManaScrewEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null && player.flipCoin(game)) {
            player.getManaPool().addMana(getMana(game, source), game, source);
        }
        return true;
    }
}
