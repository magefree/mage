package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.RollDieWithResultTableEffect;
import mage.abilities.keyword.FlashAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.permanent.token.TreasureToken;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SpikedPitTrap extends CardImpl {

    public SpikedPitTrap(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}");

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // {5}, {T}, Sacrifice Spike Pit Trap: Choose target creature, then roll a d20.
        RollDieWithResultTableEffect effect = new RollDieWithResultTableEffect(
                20, "choose target creature, then roll a d20"
        );
        Ability ability = new SimpleActivatedAbility(effect, new GenericManaCost(5));
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeSourceCost());
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);

        // 1-9 | Spiked Pit Trap deals 5 damage to that creature.
        effect.addTableEntry(1, 9, new DamageTargetEffect(
                5, true, "that creature"
        ));

        // 10-20 | Spike Pit Trap deals 5 damage to that creature. Create a Treasure token.
        effect.addTableEntry(10, 20, new DamageTargetEffect(
                5, true, "that creature."
        ), new CreateTokenEffect(new TreasureToken()));
    }

    private SpikedPitTrap(final SpikedPitTrap card) {
        super(card);
    }

    @Override
    public SpikedPitTrap copy() {
        return new SpikedPitTrap(this);
    }
}
