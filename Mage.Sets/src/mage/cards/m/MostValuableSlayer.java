package mage.cards.m;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksWithCreaturesTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetAttackingCreature;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MostValuableSlayer extends CardImpl {

    public MostValuableSlayer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // Whenever you attack, target attacking creature gets +1/+0 and gains first strike until end of turn.
        Ability ability = new AttacksWithCreaturesTriggeredAbility(new BoostTargetEffect(1, 0)
                .setText("target attacking creature gets +1/+0"), 1);
        ability.addEffect(new GainAbilityTargetEffect(FirstStrikeAbility.getInstance())
                .setText("and gains first strike until end of turn"));
        ability.addTarget(new TargetAttackingCreature());
        this.addAbility(ability);
    }

    private MostValuableSlayer(final MostValuableSlayer card) {
        super(card);
    }

    @Override
    public MostValuableSlayer copy() {
        return new MostValuableSlayer(this);
    }
}
