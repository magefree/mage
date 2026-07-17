package mage.cards.m;

import mage.abilities.condition.common.KickedCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.dynamicvalue.common.GreatestAmongPermanentsValue;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.KickerAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.permanent.token.UtvaraHellkiteDragonToken;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetadjustment.ForEachPlayerTargetsAdjuster;
import mage.target.targetpointer.EachTargetPointer;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MegaFlare extends CardImpl {

    public MegaFlare(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{R}");

        // Kicker {3}{R}{R}
        this.addAbility(new KickerAbility("{3}{R}{R}"));

        // If this spell was kicked, create a 6/6 red Dragon creature token with flying.
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new CreateTokenEffect(new UtvaraHellkiteDragonToken()), KickedCondition.ONCE,
                "if this spell was kicked, create a 6/6 red Dragon creature token with flying"
        ));

        // For each opponent, choose up to one target creature that player controls. Mega Flare deals damage equal to the greatest power among creatures you control to each of the chosen creatures.
        this.getSpellAbility().addEffect(new DamageTargetEffect(GreatestAmongPermanentsValue.POWER_CONTROLLED_CREATURES)
                .setText("<br>For each opponent, choose up to one target creature that player controls. " +
                        "{this} deals damage equal to the greatest power among creatures you control " +
                        "to each of the chosen creatures").setTargetPointer(new EachTargetPointer()));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(0, 1));
        this.getSpellAbility().setTargetAdjuster(new ForEachPlayerTargetsAdjuster(false, true));
    }

    private MegaFlare(final MegaFlare card) {
        super(card);
    }

    @Override
    public MegaFlare copy() {
        return new MegaFlare(this);
    }
}
