package mage.cards.f;

import mage.abilities.common.DealsCombatDamageToAPlayerOrBattleTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.UntapTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.game.permanent.token.TreasureToken;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FurnaceReins extends CardImpl {

    public FurnaceReins(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{R}");

        // Gain control of target creature until end of turn. Untap that creature. Until end of turn, it gains haste and "Whenever this creature deals combat damage to a player or battle, create a Treasure token."
        this.getSpellAbility().addEffect(new GainControlTargetEffect(Duration.EndOfTurn));
        this.getSpellAbility().addEffect(new UntapTargetEffect().setText("Untap that creature"));
        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(
                HasteAbility.getInstance(), Duration.EndOfTurn
        ).setText("Until end of turn, it gains haste"));
        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(
                new DealsCombatDamageToAPlayerOrBattleTriggeredAbility(
                        new CreateTokenEffect(new TreasureToken()), false
                ), Duration.EndOfTurn
        ).setText("and \"Whenever this creature deals combat damage to a player or battle, create a Treasure token.\""));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private FurnaceReins(final FurnaceReins card) {
        super(card);
    }

    @Override
    public FurnaceReins copy() {
        return new FurnaceReins(this);
    }
}
