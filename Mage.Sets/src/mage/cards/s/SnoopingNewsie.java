package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.DifferentManaValuesInGraveCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.MillCardsControllerEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.hint.common.DifferentManaValuesInGraveHint;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SnoopingNewsie extends CardImpl {

    public SnoopingNewsie(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{U}{B}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When Snooping Newsie enters the battlefield, mill two cards.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new MillCardsControllerEffect(2)));

        // As long as there are five or more mana values among cards in your graveyard, Snooping Newsie gets +1/+1 and has lifelink.
        Ability ability = new SimpleStaticAbility(new ConditionalContinuousEffect(
                new BoostSourceEffect(1, 1, Duration.WhileOnBattlefield),
                DifferentManaValuesInGraveCondition.FIVE, "as long as there are " +
                "five or more mana values among cards in your graveyard, {this} gets +1/+1"
        ));
        ability.addEffect(new ConditionalContinuousEffect(
                new GainAbilitySourceEffect(LifelinkAbility.getInstance()),
                DifferentManaValuesInGraveCondition.FIVE, "and has lifelink"
        ));
        this.addAbility(ability.addHint(DifferentManaValuesInGraveHint.instance));
    }

    private SnoopingNewsie(final SnoopingNewsie card) {
        super(card);
    }

    @Override
    public SnoopingNewsie copy() {
        return new SnoopingNewsie(this);
    }
}
