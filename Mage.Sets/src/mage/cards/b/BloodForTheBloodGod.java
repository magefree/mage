package mage.cards.b;

import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.CreaturesDiedThisTurnCount;
import mage.abilities.effects.common.DamagePlayersEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.ExileSpellEffect;
import mage.abilities.effects.common.cost.SpellCostReductionSourceEffect;
import mage.abilities.effects.common.discard.DiscardHandControllerEffect;
import mage.abilities.hint.common.CreaturesDiedThisTurnHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.watchers.common.CreaturesDiedWatcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BloodForTheBloodGod extends CardImpl {

    public BloodForTheBloodGod(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{8}{B}{B}{R}");

        // This spell costs {1} less to cast for each creature that died this turn.
        this.addAbility(new SimpleStaticAbility(
                Zone.ALL, new SpellCostReductionSourceEffect(CreaturesDiedThisTurnCount.instance)
                .setText("this spell costs {1} less to cast for each creature that died this turn")
        ).addHint(CreaturesDiedThisTurnHint.instance).setRuleAtTheTop(true));

        // Discard your hand, then draw eight cards. Blood for the Blood God! deals 8 damage to each opponent. Exile Blood for the Blood God!.
        this.getSpellAbility().addEffect(new DiscardHandControllerEffect());
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(8).concatBy(", then"));
        this.getSpellAbility().addEffect(new DamagePlayersEffect(8, TargetController.OPPONENT));
        this.getSpellAbility().addEffect(new ExileSpellEffect());
    }

    private BloodForTheBloodGod(final BloodForTheBloodGod card) {
        super(card);
    }

    @Override
    public BloodForTheBloodGod copy() {
        return new BloodForTheBloodGod(this);
    }
}
