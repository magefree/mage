package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.ColoredManaCost;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ColoredManaSymbol;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.target.common.TargetControlledPermanent;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class SkophosWarleader extends CardImpl {

    private static final FilterControlledPermanent filter
            = new FilterControlledPermanent("another creature or an enchantment");

    static {
        filter.add(Predicates.or(
                CardType.CREATURE.getPredicate(),
                CardType.ENCHANTMENT.getPredicate()
        ));
        filter.add(AnotherPredicate.instance);
    }

    public SkophosWarleader(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{R}");

        this.subtype.add(SubType.MINOTAUR);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(4);
        this.toughness = new MageInt(5);

        // {R}, Sacrifice another creature or enchantment: Skophos Warleader gets +1/+0 and gains menace until end of turn.
        Ability ability = new SimpleActivatedAbility(
                new BoostSourceEffect(1, 0, Duration.EndOfTurn)
                        .setText("{this} gets +1/+0"),
                new ColoredManaCost(ColoredManaSymbol.R));
        ability.addCost(new SacrificeTargetCost(new TargetControlledPermanent(filter)));
        ability.addEffect(new GainAbilitySourceEffect(new MenaceAbility(), Duration.EndOfTurn)
                .setText("and gains menace until end of turn. " +
                        "<i>(It can't be blocked except by two or more creatures.)</i>"));
        this.addAbility(ability);
    }

    private SkophosWarleader(final SkophosWarleader card) {
        super(card);
    }

    @Override
    public SkophosWarleader copy() {
        return new SkophosWarleader(this);
    }
}
