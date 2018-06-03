
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.dynamicvalue.common.DevotionCount;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.IntimidateAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.ColoredManaSymbol;
import mage.constants.Duration;
import mage.game.Game;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class MogissMarauder extends CardImpl {

    public MogissMarauder(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{B}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.BERSERKER);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When Mogis's Marauder enters the battlefield, up to X target creatures each gain intimidate and haste, where X is your devotion to black.
        Ability ability = new EntersBattlefieldTriggeredAbility(
                new GainAbilityTargetEffect(IntimidateAbility.getInstance(), Duration.EndOfTurn,
                "up to X target creatures each gain intimidate"), false);
        ability.addEffect(new GainAbilityTargetEffect(HasteAbility.getInstance(), Duration.EndOfTurn,
                "and haste until end of turn, where X is your devotion to black"));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    @Override
    public void adjustTargets(Ability ability, Game game) {
        if (ability instanceof EntersBattlefieldTriggeredAbility) {
            ability.getTargets().clear();
            int numbTargets = new DevotionCount(ColoredManaSymbol.B).calculate(game, ability, null);
            if (numbTargets > 0) {
                ability.addTarget(new TargetCreaturePermanent(0,numbTargets));
            }
        }
    }

    public MogissMarauder(final MogissMarauder card) {
        super(card);
    }

    @Override
    public MogissMarauder copy() {
        return new MogissMarauder(this);
    }
}
