
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
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class WaxingMoon extends CardImpl {

    private final static FilterCreaturePermanent filter = new FilterCreaturePermanent("Werewolf you control");

    static {
        filter.add(new SubtypePredicate(SubType.WEREWOLF));
        filter.add(new ControllerPredicate(TargetController.YOU));
    }

    public WaxingMoon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{G}");

        // Transform up to one target Werewolf you control.
        Effect effect = new TransformTargetEffect(false);
        effect.setText("Transform up to one target Werewolf you control");
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(0, 1, filter, false));

        // Creatures you control gain trample until end of turn.
        this.getSpellAbility().addEffect(new GainAbilityAllEffect(TrampleAbility.getInstance(), Duration.EndOfTurn, new FilterControlledCreaturePermanent(), "Creatures you control gain trample until end of turn"));
    }

    public WaxingMoon(final WaxingMoon card) {
        super(card);
    }

    @Override
    public WaxingMoon copy() {
        return new WaxingMoon(this);
    }
}
