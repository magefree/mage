package mage.cards.s;

import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.combat.CantAttackYouAllEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.TargetController;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.game.permanent.token.Wurm55Token;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class SandwurmConvergence extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creatures with flying");

    static {
        filter.add(new AbilityPredicate(FlyingAbility.class));
    }

    public SandwurmConvergence(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{6}{G}{G}");

        // Creatures with flying can't attack you or planeswalkers you control.
        this.addAbility(new SimpleStaticAbility(new CantAttackYouAllEffect(Duration.WhileOnBattlefield, filter, true)));

        // At the beginning of your end step, create a 5/5 green Wurm creature token.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(new CreateTokenEffect(new Wurm55Token()), TargetController.YOU, false));
    }

    private SandwurmConvergence(final SandwurmConvergence card) {
        super(card);
    }

    @Override
    public SandwurmConvergence copy() {
        return new SandwurmConvergence(this);
    }
}
