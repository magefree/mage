package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.UntapTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.keyword.HexproofAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.common.FilterUntappedCreature;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author ciaccona007
 */
public final class SarythTheVipersFang extends CardImpl {

    private static final FilterPermanent filterTapped = new FilterControlledCreaturePermanent("tapped creatures you control");
    private static final FilterPermanent filterAbility = new FilterControlledPermanent("creature or land you control");

    static {
        filterTapped.add(TappedPredicate.TAPPED);
        filterAbility.add(Predicates.or(
                CardType.CREATURE.getPredicate(),
                CardType.LAND.getPredicate()
        ));
    }

    public SarythTheVipersFang(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}{G}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARLOCK);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Other tapped creatures you control have deathtouch.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD,
                new GainAbilityControlledEffect(DeathtouchAbility.getInstance(), Duration.WhileOnBattlefield, filterTapped, true)
        ));

        // Other untapped creatures you control have hexproof.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD,
                new GainAbilityControlledEffect(HexproofAbility.getInstance(), Duration.WhileOnBattlefield, new FilterUntappedCreature("untapped creatures"), true)
        ));

        // {1}, {T}: Untap another target creature or land you control.
        Ability ability = new SimpleActivatedAbility(new UntapTargetEffect(), new GenericManaCost(1));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetPermanent(filterAbility));
        this.addAbility(ability);
    }

    private SarythTheVipersFang(final SarythTheVipersFang card) {
        super(card);
    }

    @Override
    public SarythTheVipersFang copy() {
        return new SarythTheVipersFang(this);
    }
}
