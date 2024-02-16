
package mage.cards.j;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.BeginningOfYourEndStepTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.MaximumHandSizeControllerEffect;
import mage.abilities.effects.common.continuous.MaximumHandSizeControllerEffect.HandSizeModification;
import mage.abilities.keyword.FlashAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;

/**
 *
 * @author Loki
 */
public final class JinGitaxiasCoreAugur extends CardImpl {

    public JinGitaxiasCoreAugur(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{8}{U}{U}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.PRAETOR);

        this.power = new MageInt(5);
        this.toughness = new MageInt(4);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // At the beginning of your end step, draw seven cards.
        this.addAbility(new BeginningOfYourEndStepTriggeredAbility(new DrawCardSourceControllerEffect(7), false));

        // Each opponent's maximum hand size is reduced by seven.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD,
                new MaximumHandSizeControllerEffect(7, Duration.WhileOnBattlefield, HandSizeModification.REDUCE, TargetController.OPPONENT)));
    }

    private JinGitaxiasCoreAugur(final JinGitaxiasCoreAugur card) {
        super(card);
    }

    @Override
    public JinGitaxiasCoreAugur copy() {
        return new JinGitaxiasCoreAugur(this);
    }
}