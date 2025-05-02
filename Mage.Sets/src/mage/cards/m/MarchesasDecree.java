
package mage.cards.m;

import java.util.UUID;
import mage.abilities.common.AttacksAllTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.BecomesMonarchSourceEffect;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.abilities.hint.common.MonarchHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SetTargetPointer;
import mage.filter.StaticFilters;

/**
 *
 * @author LevelX2
 */
public final class MarchesasDecree extends CardImpl {

    public MarchesasDecree(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{B}");

        // When Marchesa's Decree enters the battlefield, you become the monarch.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new BecomesMonarchSourceEffect()).addHint(MonarchHint.instance));

        // Whenever a creature attacks you or a planeswalker you control, the controller of that creature loses 1 life.
        Effect effect = new LoseLifeTargetEffect(1);
        effect.setText("that creature's controller loses 1 life");
        this.addAbility(new AttacksAllTriggeredAbility(effect, false, StaticFilters.FILTER_PERMANENT_CREATURE, SetTargetPointer.PLAYER, true, true));
    }

    private MarchesasDecree(final MarchesasDecree card) {
        super(card);
    }

    @Override
    public MarchesasDecree copy() {
        return new MarchesasDecree(this);
    }
}
