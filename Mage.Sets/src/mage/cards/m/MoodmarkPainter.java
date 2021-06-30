package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.CardsInControllerGraveyardCount;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.StaticFilters;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author TheElk801
 */
public final class MoodmarkPainter extends CardImpl {

    public MoodmarkPainter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}{B}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SHAMAN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Undergrowth — When Moodmark Painter enters the battlefield, target creature gains menace and gets +X/+0 until end of turn, where X is the number of creature cards in your graveyard.
        DynamicValue xValue = new CardsInControllerGraveyardCount(
                StaticFilters.FILTER_CARD_CREATURE
        );
        Ability ability = new EntersBattlefieldTriggeredAbility(
                new GainAbilityTargetEffect(
                        new MenaceAbility(),
                        Duration.EndOfTurn
                ).setText("target creature gains menace"),
                false);
        ability.addEffect(new BoostTargetEffect(
                xValue, StaticValue.get(0),
                Duration.EndOfTurn, true
        ).setText("and gets +X/+0 until end of turn, "
                + "where X is the number of creature cards in your graveyard")
        );
        ability.addTarget(new TargetCreaturePermanent());
        ability.withFlavorWord("Undergrowth");
        this.addAbility(ability);
    }

    private MoodmarkPainter(final MoodmarkPainter card) {
        super(card);
    }

    @Override
    public MoodmarkPainter copy() {
        return new MoodmarkPainter(this);
    }
}
