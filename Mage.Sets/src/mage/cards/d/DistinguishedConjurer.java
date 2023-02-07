package mage.cards.d;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.ExileTargetForSourceEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.ReturnToBattlefieldUnderOwnerControlTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author Grath
 */
public final class DistinguishedConjurer extends CardImpl {

    private static final FilterCreaturePermanent filter
            = new FilterCreaturePermanent("another creature");
    private static final FilterControlledCreaturePermanent filter2
            = new FilterControlledCreaturePermanent("another target creature you control");

    static {
        filter.add(AnotherPredicate.instance);
        filter2.add(AnotherPredicate.instance);
    }

    public DistinguishedConjurer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // Whenever another creature enters the battlefield under your control, you gain 1 life.
        this.addAbility(new EntersBattlefieldControlledTriggeredAbility(new GainLifeEffect(1), filter));

        // {4}{W}, {T}: Exile another target creature you control, then return it to the battlefield under its owner’s control.
        Ability ability = new SimpleActivatedAbility(new ExileTargetForSourceEffect(), new ManaCostsImpl<>("{4}{W}"));
        ability.addCost(new TapSourceCost());
        ability.addEffect(new ReturnToBattlefieldUnderOwnerControlTargetEffect(false, false).concatBy(", then"));
        ability.addTarget(new TargetControlledCreaturePermanent(filter2));
        this.addAbility(ability);
    }

    private DistinguishedConjurer(final DistinguishedConjurer card) {
        super(card);
    }

    @Override
    public DistinguishedConjurer copy() {
        return new DistinguishedConjurer(this);
    }
}
