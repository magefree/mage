package mage.cards.d;

import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.TapTargetEffect;
import mage.cards.CardSetInfo;
import mage.cards.SplitCard;
import mage.constants.CardType;
import mage.constants.SpellAbilityType;
import mage.filter.StaticFilters;
import mage.game.permanent.token.ThopterColorlessToken;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DeposeDeploy extends SplitCard {

    public DeposeDeploy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{W/U}", "{2}{W}{U}", SpellAbilityType.SPLIT);

        // Depose
        // Tap target creature.
        // Draw a card.
        this.getLeftHalfCard().getSpellAbility().addEffect(new TapTargetEffect());
        this.getLeftHalfCard().getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getLeftHalfCard().getSpellAbility().addEffect(new DrawCardSourceControllerEffect(1).concatBy("<br>"));

        // Deploy
        // Creature two 1/1 colorless Thopter artifact creature tokens with flying, then you gain 1 life for each creature you control.
        this.getRightHalfCard().getSpellAbility().addEffect(
                new CreateTokenEffect(new ThopterColorlessToken(), 2)
        );
        this.getRightHalfCard().getSpellAbility().addEffect(
                new GainLifeEffect(new PermanentsOnBattlefieldCount(
                        StaticFilters.FILTER_CONTROLLED_CREATURES
                )).setText(", then you gain 1 life for each creature you control.")
        );

    }

    private DeposeDeploy(final DeposeDeploy card) {
        super(card);
    }

    @Override
    public DeposeDeploy copy() {
        return new DeposeDeploy(this);
    }
}
