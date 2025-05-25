
package mage.cards.p;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.PayEnergyCost;
import mage.abilities.dynamicvalue.common.GreatestAmongPermanentsValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.combat.BlocksIfAbleTargetEffect;
import mage.abilities.effects.common.counter.GetEnergyCountersControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class PeemaAetherSeer extends CardImpl {

    public PeemaAetherSeer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}");

        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.DRUID);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // When Peema Aether-Seer enters the battlefield, you get an amount of {E} equal to the greatest power among creatures you control.
        Effect effect = new GetEnergyCountersControllerEffect(GreatestAmongPermanentsValue.POWER_CONTROLLED_CREATURES);
        effect.setText("you get an amount of {E} equal to the greatest power among creatures you control");
        this.addAbility(new EntersBattlefieldTriggeredAbility(effect).addHint(GreatestAmongPermanentsValue.POWER_CONTROLLED_CREATURES.getHint()));

        // Pay {E}{E}{E}: Target creature blocks this turn if able.
        Ability ability = new SimpleActivatedAbility(new BlocksIfAbleTargetEffect(Duration.EndOfTurn), new PayEnergyCost(3));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private PeemaAetherSeer(final PeemaAetherSeer card) {
        super(card);
    }

    @Override
    public PeemaAetherSeer copy() {
        return new PeemaAetherSeer(this);
    }
}
