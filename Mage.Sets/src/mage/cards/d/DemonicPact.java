
package mage.cards.d;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.LoseGameSourceControllerEffect;
import mage.abilities.effects.common.discard.DiscardTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.target.common.TargetAnyTarget;
import mage.target.common.TargetOpponent;

/**
 *
 * @author LevelX2
 */
public final class DemonicPact extends CardImpl {

    public DemonicPact(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{2}{B}{B}");

        // At the beginning of your upkeep, choose one that hasn't been chosen
        // - Demonic Pact deals 4 damage to any target and you gain 4 life;
        Ability ability = new BeginningOfUpkeepTriggeredAbility(new DamageTargetEffect(4), TargetController.YOU, false);
        ability.getModes().setEachModeOnlyOnce(true);
        ability.addTarget(new TargetAnyTarget());
        Effect effect = new GainLifeEffect(4);
        effect.setText("and you gain 4 life");
        ability.addEffect(effect);

        // - Target opponent discards two cards
        Mode mode = new Mode(new DiscardTargetEffect(2));
        mode.addTarget(new TargetOpponent());
        ability.addMode(mode);

        // - Draw two cards
        mode = new Mode(new DrawCardSourceControllerEffect(2));
        ability.addMode(mode);

        // - You lose the game.
        mode = new Mode(new LoseGameSourceControllerEffect());
        ability.addMode(mode);

        this.addAbility(ability);

    }

    private DemonicPact(final DemonicPact card) {
        super(card);
    }

    @Override
    public DemonicPact copy() {
        return new DemonicPact(this);
    }
}
