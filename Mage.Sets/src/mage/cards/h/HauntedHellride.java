package mage.cards.h;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksWithCreaturesTriggeredAbility;
import mage.abilities.effects.common.UntapTargetEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.CrewAbility;
import mage.abilities.keyword.DeathtouchAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class HauntedHellride extends CardImpl {

    public HauntedHellride(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}{U}{B}");

        this.subtype.add(SubType.VEHICLE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Whenever you attack, target creature you control gets +1/+0 and gains deathtouch until end of turn. Untap it.
        Ability ability = new AttacksWithCreaturesTriggeredAbility(new BoostTargetEffect(1, 0)
                .setText("target creature you control gets +1/+0"), 1);
        ability.addEffect(new GainAbilityTargetEffect(DeathtouchAbility.getInstance())
                .setText("and gains deathtouch until end of turn"));
        ability.addEffect(new UntapTargetEffect("untap it"));
        ability.addTarget(new TargetControlledCreaturePermanent());
        this.addAbility(ability);

        // Crew 1
        this.addAbility(new CrewAbility(1));
    }

    private HauntedHellride(final HauntedHellride card) {
        super(card);
    }

    @Override
    public HauntedHellride copy() {
        return new HauntedHellride(this);
    }
}
