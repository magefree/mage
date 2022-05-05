package mage.cards.h;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.dynamicvalue.common.SavedDamageValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class HuntersProwess extends CardImpl {

    public HuntersProwess(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{4}{G}");

        // Until end of turn, target creature gets +3/+3 and gains trample and "Whenever this creature deals combat damage to a player, draw that many cards."
        Effect effect = new BoostTargetEffect(3, 3, Duration.EndOfTurn);
        effect.setText("Until end of turn, target creature gets +3/+3");
        this.getSpellAbility().addEffect(effect);
        effect = new GainAbilityTargetEffect(TrampleAbility.getInstance(), Duration.EndOfTurn);
        effect.setText("and gains trample");
        this.getSpellAbility().addEffect(effect);
        Ability grantedAbility = new DealsCombatDamageToAPlayerTriggeredAbility(
                new DrawCardSourceControllerEffect(SavedDamageValue.MANY), false, true);
        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(grantedAbility, Duration.EndOfTurn,
                "and \"Whenever this creature deals combat damage to a player, draw that many cards.\""));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private HuntersProwess(final HuntersProwess card) {
        super(card);
    }

    @Override
    public HuntersProwess copy() {
        return new HuntersProwess(this);
    }
}
