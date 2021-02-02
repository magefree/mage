
package mage.cards.f;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.ExertSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.target.common.TargetCreaturePermanent;
import mage.target.common.TargetPlayerOrPlaneswalker;

/**
 *
 * @author jeffwadsworth
 */
public final class FerventPaincaster extends CardImpl {

    public FerventPaincaster(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);

        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // {T}: Fervent Paincaster deals 1 damage to target player.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DamageTargetEffect(1), new TapSourceCost());
        ability.addTarget(new TargetPlayerOrPlaneswalker());
        this.addAbility(ability);

        // {T}, Exert Fervent Paincaster: It deals 1 damage to target creature.
        Ability ability2 = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DamageTargetEffect(1, "It"), new TapSourceCost());
        ability2.addCost(new ExertSourceCost());
        ability2.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability2);

    }

    private FerventPaincaster(final FerventPaincaster card) {
        super(card);
    }

    @Override
    public FerventPaincaster copy() {
        return new FerventPaincaster(this);
    }
}
