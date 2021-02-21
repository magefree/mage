
package mage.cards.f;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfCombatTriggeredAbility;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.MeleeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.TargetController;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author Styxo
 */
public final class FangOfThePack extends CardImpl {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("another target creature you control");

    static {
        filter.add(AnotherPredicate.instance);
    }

    public FangOfThePack(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{G}");

        this.subtype.add(SubType.WOLF);
        this.power = new MageInt(5);
        this.toughness = new MageInt(3);

        // Melee
        this.addAbility(new MeleeAbility());

        // At the beginning of combat on your turn, another target creature you control gains melee until end of turn.
        Ability ability = new BeginningOfCombatTriggeredAbility(new GainAbilityTargetEffect(new MeleeAbility(), Duration.EndOfTurn), TargetController.YOU, false);
        ability.addTarget(new TargetControlledCreaturePermanent(filter));
        this.addAbility(ability);
    }

    private FangOfThePack(final FangOfThePack card) {
        super(card);
    }

    @Override
    public FangOfThePack copy() {
        return new FangOfThePack(this);
    }
}
