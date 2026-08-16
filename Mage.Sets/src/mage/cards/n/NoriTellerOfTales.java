package mage.cards.n;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.target.common.TargetAttackingCreature;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class NoriTellerOfTales extends CardImpl {

    public NoriTellerOfTales(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R/W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.DWARF);
        this.subtype.add(SubType.BARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever Nori attacks, target attacking creature gains first strike until end of turn.
        Ability ability = new AttacksTriggeredAbility(new GainAbilityTargetEffect(FirstStrikeAbility.getInstance()));
        ability.addTarget(new TargetAttackingCreature());
        this.addAbility(ability);
    }

    private NoriTellerOfTales(final NoriTellerOfTales card) {
        super(card);
    }

    @Override
    public NoriTellerOfTales copy() {
        return new NoriTellerOfTales(this);
    }
}
