package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.DamagePlayersEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.PowerPredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ArabellaAbandonedDoll extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledCreaturePermanent("creatures you control with power 2 or less");
    static {
        filter.add(new PowerPredicate(ComparisonType.OR_LESS, 2));
    }

    private static final DynamicValue xValue = new PermanentsOnBattlefieldCount(filter, 1);
    private static final Hint hint = new ValueHint("Creatures you control with power 2 or less", xValue);

    public ArabellaAbandonedDoll(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{R}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.TOY);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Whenever Arabella, Abandoned Doll attacks, it deals X damage to each opponent and you gain X life, where X is the number of creatures you control with power 2 or less.
        Ability ability = new AttacksTriggeredAbility(
                new DamagePlayersEffect(xValue, TargetController.OPPONENT)
                        .setText("it deals X damage to each opponent")
        );
        ability.addEffect(new GainLifeEffect(xValue).concatBy("and"));
        this.addAbility(ability.addHint(hint));
    }

    private ArabellaAbandonedDoll(final ArabellaAbandonedDoll card) {
        super(card);
    }

    @Override
    public ArabellaAbandonedDoll copy() {
        return new ArabellaAbandonedDoll(this);
    }
}
