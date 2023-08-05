
package mage.cards.s;

import java.util.UUID;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.keyword.AftermathAbility;
import mage.cards.CardSetInfo;
import mage.cards.SplitCard;
import mage.constants.CardType;
import mage.constants.SpellAbilityType;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.permanent.token.WarriorVigilantToken;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author fireshoes
 */
public final class StartFinish extends SplitCard {

    public StartFinish(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, new CardType[]{CardType.SORCERY}, "{2}{W}", "{2}{B}", SpellAbilityType.SPLIT_AFTERMATH);

        // Start
        // Create two 1/1 white Warrior creature tokens with vigilance.
        Effect effect = new CreateTokenEffect(new WarriorVigilantToken(), 2);
        effect.setText("Create two 1/1 white Warrior creature tokens with vigilance");
        getLeftHalfCard().getSpellAbility().addEffect(effect);

        // Finish
        // Aftermath
        // As an additional cost to cast Finish, sacrifice a creature. Destroy target creature.
        getRightHalfCard().addAbility(new AftermathAbility().setRuleAtTheTop(true));
        getRightHalfCard().getSpellAbility().addCost(new SacrificeTargetCost(StaticFilters.FILTER_CONTROLLED_CREATURE_SHORT_TEXT));
        getRightHalfCard().getSpellAbility().addTarget(new TargetCreaturePermanent(new FilterCreaturePermanent("creature (to destoy)")));
        getRightHalfCard().getSpellAbility().addEffect(new DestroyTargetEffect("Destroy target creature"));
    }

    private StartFinish(final StartFinish card) {
        super(card);
    }

    @Override
    public StartFinish copy() {
        return new StartFinish(this);
    }
}
