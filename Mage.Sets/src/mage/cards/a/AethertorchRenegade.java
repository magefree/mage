
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.PayEnergyCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.counter.GetEnergyCountersControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.target.common.TargetCreaturePermanent;
import mage.target.common.TargetPlayerOrPlaneswalker;

/**
 *
 * @author LevelX2
 */
public final class AethertorchRenegade extends CardImpl {

    public AethertorchRenegade(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // When Aethertorch Renegade enters the battlefield, you get {E}{E}{E}{E}
        this.addAbility(new EntersBattlefieldTriggeredAbility(new GetEnergyCountersControllerEffect(4)));

        // {t}, Pay {E}{E} Aethertorch Renegade deals 1 damage to target creature.
        Ability ability = new SimpleActivatedAbility(new DamageTargetEffect(1), new TapSourceCost());
        ability.addTarget(new TargetCreaturePermanent());
        ability.addCost(new PayEnergyCost(2));
        this.addAbility(ability);
        // {t}, Pay {E}{E}{E}{E}{E}{E}{E}{E}: Aethertorch Renegade deals 6 damage to target player.
        ability = new SimpleActivatedAbility(new DamageTargetEffect(6), new TapSourceCost());
        ability.addTarget(new TargetPlayerOrPlaneswalker());
        ability.addCost(new PayEnergyCost(8));
        this.addAbility(ability);

    }

    private AethertorchRenegade(final AethertorchRenegade card) {
        super(card);
    }

    @Override
    public AethertorchRenegade copy() {
        return new AethertorchRenegade(this);
    }
}
