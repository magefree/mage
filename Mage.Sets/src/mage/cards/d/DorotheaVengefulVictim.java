package mage.cards.d;

import mage.MageInt;
import mage.abilities.common.AttacksOrBlocksTriggeredAbility;
import mage.abilities.common.delayed.AtTheEndOfCombatDelayedTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.SacrificeSourceEffect;
import mage.abilities.keyword.DisturbAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DorotheaVengefulVictim extends CardImpl {

    public DorotheaVengefulVictim(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{W}{U}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.SPIRIT);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);
        this.secondSideCardClazz = mage.cards.d.DorotheasRetribution.class;

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Dorothea, Vengeful Victim attacks or blocks, sacrifice it at end of combat.
        this.addAbility(new AttacksOrBlocksTriggeredAbility(new CreateDelayedTriggeredAbilityEffect(
                new AtTheEndOfCombatDelayedTriggeredAbility(new SacrificeSourceEffect())
        ).setText("sacrifice it at end of combat"), false));

        // Disturb {1}{W}{U}
        this.addAbility(new DisturbAbility(this, "{1}{W}{U}"));
    }

    private DorotheaVengefulVictim(final DorotheaVengefulVictim card) {
        super(card);
    }

    @Override
    public DorotheaVengefulVictim copy() {
        return new DorotheaVengefulVictim(this);
    }
}
