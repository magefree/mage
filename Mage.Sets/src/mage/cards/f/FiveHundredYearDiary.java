package mage.cards.f;

import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.mana.DynamicManaEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.abilities.mana.SimpleManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class FiveHundredYearDiary extends CardImpl {

    private static final DynamicValue xValue = new PermanentsOnBattlefieldCount(
            new FilterControlledPermanent(SubType.CLUE, "Clue you control")
    );
    private static final Hint hint = new ValueHint("Clues you control", xValue);

    public FiveHundredYearDiary(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.CLUE);

        // Five Hundred Year Diary enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());

        // {T}: Add {U} for each Clue you control.
        this.addAbility(new SimpleManaAbility(
                Zone.BATTLEFIELD,
                new DynamicManaEffect(Mana.BlueMana(1), xValue),
                new TapSourceCost()
        ).addHint(hint));

        // {2}, Sacrifice Five Hundred Year Diary: Draw a card.
        Ability ability = new SimpleActivatedAbility(
                new DrawCardSourceControllerEffect(1),
                new GenericManaCost(2)
        );
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);
    }

    private FiveHundredYearDiary(final FiveHundredYearDiary card) {
        super(card);
    }

    @Override
    public FiveHundredYearDiary copy() {
        return new FiveHundredYearDiary(this);
    }
}
