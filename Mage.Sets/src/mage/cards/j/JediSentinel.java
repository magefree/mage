package mage.cards.j;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.keyword.FlashAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.EachTargetPointer;

import java.util.UUID;

/**
 * @author Styxo
 */
public final class JediSentinel extends CardImpl {

    private static final FilterControlledCreaturePermanent filter1 = new FilterControlledCreaturePermanent("another target creature you control");

    static {
        filter1.add(AnotherPredicate.instance);
    }

    public JediSentinel(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{G}{U}{W}");
        this.subtype.add(SubType.TWILEK);
        this.subtype.add(SubType.JEDI);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // When Jedi Sentinel enters the battlefield, return another target creature you control and target creature you don't control to their owners' hands.
        Effect effect = new ReturnToHandTargetEffect().setTargetPointer(new EachTargetPointer());
        Ability ability = new EntersBattlefieldTriggeredAbility(effect);
        ability.addTarget(new TargetControlledCreaturePermanent(filter1));
        ability.addTarget(new TargetCreaturePermanent(StaticFilters.FILTER_CREATURE_YOU_DONT_CONTROL));
        this.addAbility(ability);
    }

    private JediSentinel(final JediSentinel card) {
        super(card);
    }

    @Override
    public JediSentinel copy() {
        return new JediSentinel(this);
    }
}
