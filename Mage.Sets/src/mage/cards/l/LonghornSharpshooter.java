package mage.cards.l;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BecomesPlottedSourceTriggeredAbility;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.PlotAbility;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetAnyTarget;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class LonghornSharpshooter extends CardImpl {

    public LonghornSharpshooter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");

        this.subtype.add(SubType.MINOTAUR);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Reach
        this.addAbility(ReachAbility.getInstance());

        // When Longhorn Sharpshooter becomes plotted, it deals 2 damage to any target.
        Ability ability = new BecomesPlottedSourceTriggeredAbility(new DamageTargetEffect(2));
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);

        // Plot {3}{R}
        this.addAbility(new PlotAbility("{3}{R}"));
    }

    private LonghornSharpshooter(final LonghornSharpshooter card) {
        super(card);
    }

    @Override
    public LonghornSharpshooter copy() {
        return new LonghornSharpshooter(this);
    }
}
