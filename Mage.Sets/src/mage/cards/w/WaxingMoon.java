
package mage.cards.w;

import java.util.UUID;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.TransformTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class WaxingMoon extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Werewolf you control");

    static {
        filter.add(SubType.WEREWOLF.getPredicate());
        filter.add(TargetController.YOU.getControllerPredicate());
    }

    public WaxingMoon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{G}");

        // Transform up to one target Werewolf you control.
        Effect effect = new TransformTargetEffect();
        effect.setText("Transform up to one target Werewolf you control");
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(0, 1, filter, false));

        // Creatures you control gain trample until end of turn.
        this.getSpellAbility().addEffect(new GainAbilityAllEffect(TrampleAbility.getInstance(), Duration.EndOfTurn, new FilterControlledCreaturePermanent(), "Creatures you control gain trample until end of turn"));
    }

    private WaxingMoon(final WaxingMoon card) {
        super(card);
    }

    @Override
    public WaxingMoon copy() {
        return new WaxingMoon(this);
    }
}
