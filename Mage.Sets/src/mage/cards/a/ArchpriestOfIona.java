package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.FullPartyCondition;
import mage.abilities.dynamicvalue.common.PartyCount;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.continuous.SetBasePowerSourceEffect;
import mage.abilities.hint.common.PartyCountHint;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.triggers.BeginningOfCombatTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ArchpriestOfIona extends CardImpl {

    public ArchpriestOfIona(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(0);
        this.toughness = new MageInt(2);

        // Archpriest of Iona's power is equal to the number of creatures in your party.
        this.addAbility(new SimpleStaticAbility(
                Zone.ALL,
                new SetBasePowerSourceEffect(PartyCount.instance)
                        .setText("{this}'s power is equal to the number of creatures in your party. " + PartyCount.getReminder())
        ).addHint(PartyCountHint.instance));

        // At the beginning of combat on your turn, if you have a full party, target creature gets +1/+1 and gains flying until end of turn.
        Ability ability = new BeginningOfCombatTriggeredAbility(
                new BoostTargetEffect(1, 1).setText("target creature gets +1/+1")
        ).withInterveningIf(FullPartyCondition.instance);
        ability.addEffect(new GainAbilityTargetEffect(FlyingAbility.getInstance())
                .setText("and gains flying until end of turn"));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private ArchpriestOfIona(final ArchpriestOfIona card) {
        super(card);
    }

    @Override
    public ArchpriestOfIona copy() {
        return new ArchpriestOfIona(this);
    }
}
