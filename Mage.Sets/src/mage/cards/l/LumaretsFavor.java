package mage.cards.l;

import mage.abilities.condition.common.YouGainedLifeCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.dynamicvalue.common.ControllerGainedLifeCount;
import mage.abilities.effects.common.CastSourceTriggeredAbility;
import mage.abilities.effects.common.CopySourceSpellEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.target.common.TargetCreaturePermanent;
import mage.watchers.common.PlayerGainedLifeWatcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class LumaretsFavor extends CardImpl {

    public LumaretsFavor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{G}");

        // Infusion --When you cast this spell, copy it if you gained life this turn. You may choose new targets for the copy.
        this.addAbility(new CastSourceTriggeredAbility(new ConditionalOneShotEffect(
                new CopySourceSpellEffect(), YouGainedLifeCondition.getZero(),
                "copy it if you gained life this turn. You may choose new targets for the copy"
        )).setAbilityWord(AbilityWord.INFUSION).addHint(ControllerGainedLifeCount.getHint()), new PlayerGainedLifeWatcher());

        // Target creature gets +2/+4 until end of turn.
        this.getSpellAbility().addEffect(new BoostTargetEffect(2, 4));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private LumaretsFavor(final LumaretsFavor card) {
        super(card);
    }

    @Override
    public LumaretsFavor copy() {
        return new LumaretsFavor(this);
    }
}
