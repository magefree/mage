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
import mage.constants.AbilityWord;
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

    private static final DynamicValue xValue = new CardsInControllerGraveyardCount(StaticFilters.FILTER_CARD_CREATURES, null);

    public MoodmarkPainter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}{B}");

        this.subtype.add(SubType.HUMAN, SubType.SHAMAN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Undergrowth — When Moodmark Painter enters the battlefield,
        Ability ability = new EntersBattlefieldTriggeredAbility(
                new GainAbilityTargetEffect(
                        new MenaceAbility(false),
                        Duration.EndOfTurn
                ).setText("target creature gains menace."),
                false);
        // target creature gains menace and gets +X/+0 until end of turn,
        // where X is the number of creature cards in your graveyard.
        ability.addEffect(new BoostTargetEffect(
                xValue, StaticValue.get(0),
                Duration.EndOfTurn
        ).setText("and gets +X/+0 until end of turn, "
                + "where X is the number of creature cards in your graveyard. " +
                "<i>(It can't be blocked except by two or more creatures.)</i>")); // Must be here to match Oracle text
        ability.addTarget(new TargetCreaturePermanent());
        ability.setAbilityWord(AbilityWord.UNDERGROWTH);
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
