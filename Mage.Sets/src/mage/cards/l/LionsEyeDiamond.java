
package mage.cards.l;

import java.util.UUID;
import mage.Mana;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.DiscardHandCost;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.effects.mana.AddManaOfAnyColorEffect;
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
 * @author North
 */
public final class LionsEyeDiamond extends CardImpl {

    public LionsEyeDiamond(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{0}");

        // Sacrifice Lion's Eye Diamond, Discard your hand: Add three mana of any one color. Activate this ability only any time you could cast an instant.
        this.addAbility(new LionsEyeDiamondAbility());
    }

    private LionsEyeDiamond(final LionsEyeDiamond card) {
        super(card);
    }

    @Override
    public LionsEyeDiamond copy() {
        return new LionsEyeDiamond(this);
    }
}

class LionsEyeDiamondAbility extends ActivatedManaAbilityImpl {

    public LionsEyeDiamondAbility() {
        super(Zone.BATTLEFIELD, new AddManaOfAnyColorEffect(3), new DiscardHandCost());
        this.addCost(new SacrificeSourceCost());
        this.netMana.add(new Mana(0, 0, 0, 0, 0, 0, 3, 0));
    }

    public LionsEyeDiamondAbility(Zone zone, Mana mana, Cost cost) {
        super(zone, new BasicManaEffect(mana), cost);

    }

    private LionsEyeDiamondAbility(final LionsEyeDiamondAbility ability) {
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
    public LionsEyeDiamondAbility copy() {
        return new LionsEyeDiamondAbility(this);
    }

    @Override
    public String getRule() {
        return super.getRule() + " Activate only as an instant.";
    }
}
