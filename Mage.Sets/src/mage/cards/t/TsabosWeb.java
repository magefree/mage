
package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class TsabosWeb extends CardImpl {

    public TsabosWeb(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");

        // When Tsabo's Web enters the battlefield, draw a card.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new DrawCardSourceControllerEffect(1), false));
        // Each land with an activated ability that isn't a mana ability doesn't untap during its controller's untap step.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new TsabosWebPreventUntapEffect()));
    }

    private TsabosWeb(final TsabosWeb card) {
        super(card);
    }

    @Override
    public TsabosWeb copy() {
        return new TsabosWeb(this);
    }
}

class TsabosWebPreventUntapEffect extends ContinuousRuleModifyingEffectImpl {

    TsabosWebPreventUntapEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Detriment);
        staticText = "Each land with an activated ability that isn't a mana ability doesn't untap during its controller's untap step";
    }

    private TsabosWebPreventUntapEffect(final TsabosWebPreventUntapEffect effect) {
        super(effect);
    }

    @Override
    public TsabosWebPreventUntapEffect copy() {
        return new TsabosWebPreventUntapEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.UNTAP;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (game.getTurnStepType() == PhaseStep.UNTAP) {
            Permanent permanent = game.getPermanent(event.getTargetId());
            if (permanent != null && permanent.isLand(game)) {
                for (Ability ability : permanent.getAbilities()) {
                    if (ability.isNonManaActivatedAbility()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

}
