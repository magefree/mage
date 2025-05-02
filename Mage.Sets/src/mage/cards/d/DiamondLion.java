package mage.cards.d;

import mage.MageInt;
import mage.Mana;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.DiscardHandCost;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.mana.AddManaOfAnyColorEffect;
import mage.abilities.effects.mana.BasicManaEffect;
import mage.abilities.mana.ActivatedManaAbilityImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DiamondLion extends CardImpl {

    public DiamondLion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{2}");

        this.subtype.add(SubType.CAT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // {T}, Discard your hand, Sacrifice Diamond Lion: Add three mana of any one color. Activate only as an instant.
        this.addAbility(new DiamondLionAbility());
    }

    private DiamondLion(final DiamondLion card) {
        super(card);
    }

    @Override
    public DiamondLion copy() {
        return new DiamondLion(this);
    }
}

class DiamondLionAbility extends ActivatedManaAbilityImpl {

    public DiamondLionAbility() {
        super(Zone.BATTLEFIELD, new AddManaOfAnyColorEffect(3), new TapSourceCost());
        this.addCost(new DiscardHandCost());
        this.addCost(new SacrificeSourceCost());
        this.netMana.add(new Mana(0, 0, 0, 0, 0, 0, 3, 0));
    }

    public DiamondLionAbility(Zone zone, Mana mana, Cost cost) {
        super(zone, new BasicManaEffect(mana), cost);

    }

    private DiamondLionAbility(final DiamondLionAbility ability) {
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
    public DiamondLionAbility copy() {
        return new DiamondLionAbility(this);
    }

    @Override
    public String getRule() {
        return super.getRule() + " Activate only as an instant.";
    }
}
