
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.combat.CantBeBlockedTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.DethroneAbility;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class MarchesasSmuggler extends CardImpl {

    public MarchesasSmuggler(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{U}{R}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ROGUE);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Dethrone
        this.addAbility(new DethroneAbility());
        // {1}{U}{R}: Target creature you control gains haste until end of turn and can't be blocked this turn.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new GainAbilityTargetEffect(HasteAbility.getInstance(), Duration.EndOfTurn), new ManaCostsImpl<>("{1}{U}{R}"));
        ability.addTarget(new TargetControlledCreaturePermanent());
        Effect effect = new CantBeBlockedTargetEffect(Duration.EndOfTurn);
        effect.setText("and can't be blocked this turn");
        ability.addEffect(effect);
        this.addAbility(ability);

    }

    private MarchesasSmuggler(final MarchesasSmuggler card) {
        super(card);
    }

    @Override
    public MarchesasSmuggler copy() {
        return new MarchesasSmuggler(this);
    }
}
