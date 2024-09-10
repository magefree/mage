package mage.cards.m;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.EncoreAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MistDancer extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent(SubType.MERFOLK, "Merfolk");
    private static final FilterPermanent filter2 = new FilterPermanent(SubType.MERFOLK, "");

    public MistDancer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{U}");

        this.subtype.add(SubType.MERFOLK);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Other Merfolk you control get +1/+0 and have flying.
        Ability ability = new SimpleStaticAbility(new BoostControlledEffect(
                1, 0, Duration.WhileOnBattlefield, filter, true
        ));
        ability.addEffect(new GainAbilityControlledEffect(
                FlyingAbility.getInstance(), Duration.WhileOnBattlefield, filter2, true
        ).setText("and have flying"));
        this.addAbility(ability);

        // Encore {5}{U}{U}
        this.addAbility(new EncoreAbility(new ManaCostsImpl<>("{5}{U}{U}")));
    }

    private MistDancer(final MistDancer card) {
        super(card);
    }

    @Override
    public MistDancer copy() {
        return new MistDancer(this);
    }
}
