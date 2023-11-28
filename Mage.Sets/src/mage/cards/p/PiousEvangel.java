package mage.cards.p;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldThisOrAnotherTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.keyword.TransformAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.target.common.TargetControlledPermanent;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class PiousEvangel extends CardImpl {

    private static final FilterControlledPermanent filter2 = new FilterControlledPermanent("another permanent");

    static {
        filter2.add(AnotherPredicate.instance);
    }

    public PiousEvangel(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        this.secondSideCardClazz = mage.cards.w.WaywardDisciple.class;

        // Whenever Pious Evangel or another creature enters the battlefield under your control, you gain 1 life.
        this.addAbility(new EntersBattlefieldThisOrAnotherTriggeredAbility(new GainLifeEffect(1),
                StaticFilters.FILTER_PERMANENT_CREATURE, false, true));

        // {2}, {T}, Sacrifice another permanent: Transform Pious Evangel.
        this.addAbility(new TransformAbility());
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new TransformSourceEffect(), new GenericManaCost(2));
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeTargetCost(new TargetControlledPermanent(filter2)));
        this.addAbility(ability);
    }

    private PiousEvangel(final PiousEvangel card) {
        super(card);
    }

    @Override
    public PiousEvangel copy() {
        return new PiousEvangel(this);
    }
}
