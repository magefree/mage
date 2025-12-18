package mage.cards.m;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DrawNthCardTriggeredAbility;
import mage.abilities.common.EntersBattlefieldAllTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.effects.common.continuous.SetBasePowerToughnessSourceEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MoonGirlAndDevilDinosaur extends CardImpl {

    public MoonGirlAndDevilDinosaur(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.DINOSAUR);
        this.subtype.add(SubType.HERO);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever you draw your second card each turn, until end of turn, Moon Girl and Devil Dinosaur's base power and toughness become 6/6 and they gain trample.
        Ability ability = new DrawNthCardTriggeredAbility(
                new SetBasePowerToughnessSourceEffect(6, 6, Duration.EndOfTurn)
                        .setText("until end of turn, {this}'s base power and toughness become 6/6")
        );
        ability.addEffect(new GainAbilitySourceEffect(
                TrampleAbility.getInstance(), Duration.EndOfTurn
        ).setText("and they gain trample"));
        this.addAbility(ability);

        // Whenever an artifact you control enters, draw a card. This ability triggers only once each turn.
        this.addAbility(new EntersBattlefieldAllTriggeredAbility(
                new DrawCardSourceControllerEffect(1), StaticFilters.FILTER_CONTROLLED_PERMANENT_ARTIFACT
        ).setTriggersLimitEachTurn(1));
    }

    private MoonGirlAndDevilDinosaur(final MoonGirlAndDevilDinosaur card) {
        super(card);
    }

    @Override
    public MoonGirlAndDevilDinosaur copy() {
        return new MoonGirlAndDevilDinosaur(this);
    }
}
