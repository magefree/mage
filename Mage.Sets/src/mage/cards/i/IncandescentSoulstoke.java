package mage.cards.i;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.PutCardIntoPlayWithHasteAndSacrificeEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreatureCard;
import mage.filter.common.FilterCreaturePermanent;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class IncandescentSoulstoke extends CardImpl {

    private static final FilterCreaturePermanent filter
            = new FilterCreaturePermanent(SubType.ELEMENTAL, "Elemental creatures");
    private static final FilterCard filter2 = new FilterCreatureCard("Elemental creature card");

    static {
        filter2.add(SubType.ELEMENTAL.getPredicate());
    }

    public IncandescentSoulstoke(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");
        this.subtype.add(SubType.ELEMENTAL);
        this.subtype.add(SubType.SHAMAN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Other Elemental creatures you control get +1/+1.
        this.addAbility(new SimpleStaticAbility(new BoostControlledEffect(
                1, 1, Duration.WhileOnBattlefield, filter, true
        )));

        // {1}{R}, {T}: You may put an Elemental creature card from your hand onto the battlefield. That creature gains haste until end of turn. Sacrifice it at the beginning of the next end step.
        Ability ability = new SimpleActivatedAbility(
                new PutCardIntoPlayWithHasteAndSacrificeEffect(filter2, Duration.EndOfTurn)
                        .setText("You may put an Elemental creature card from your hand onto the battlefield. " +
                                "That creature gains haste until end of turn. Sacrifice it at the beginning of the next end step."),
                new ManaCostsImpl<>("{1}{R}")
        );
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);

    }

    private IncandescentSoulstoke(final IncandescentSoulstoke card) {
        super(card);
    }

    @Override
    public IncandescentSoulstoke copy() {
        return new IncandescentSoulstoke(this);
    }
}
