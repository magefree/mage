package mage.cards.d;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.abilities.keyword.PersistAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.permanent.TokenPredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DuskLegionSergeant extends CardImpl {

    private static final FilterPermanent filter
            = new FilterControlledCreaturePermanent(SubType.VAMPIRE, "each nontoken Vampire creature you control");

    static {
        filter.add(TokenPredicate.FALSE);
    }

    public DuskLegionSergeant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");

        this.subtype.add(SubType.VAMPIRE);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Menace
        this.addAbility(new MenaceAbility());

        // {1}{B}, Sacrifice Dusk Legion Sergeant: Each nontoken Vampire creature you control gains persist until end of turn.
        Ability ability = new SimpleActivatedAbility(new GainAbilityControlledEffect(
                new PersistAbility(), Duration.EndOfTurn, filter
        ), new ManaCostsImpl<>("{1}{B}"));
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);
    }

    private DuskLegionSergeant(final DuskLegionSergeant card) {
        super(card);
    }

    @Override
    public DuskLegionSergeant copy() {
        return new DuskLegionSergeant(this);
    }
}
