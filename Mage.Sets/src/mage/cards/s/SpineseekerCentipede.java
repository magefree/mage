package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.DeliriumCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.dynamicvalue.common.CardTypesInGraveyardCount;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SpineseekerCentipede extends CardImpl {

    public SpineseekerCentipede(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");

        this.subtype.add(SubType.INSECT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // When Spineseeker Centipede enters, search your library for a basic land card, reveal it, put it into your hand, then shuffle.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new SearchLibraryPutInHandEffect(
                new TargetCardInLibrary(StaticFilters.FILTER_CARD_BASIC_LAND), true
        )));

        // Delirium -- Spineseeker Centipede gets +1/+2 and has vigilance as long as there are four or more card types among cards in your graveyard.
        Ability ability = new SimpleStaticAbility(new ConditionalContinuousEffect(
                new BoostSourceEffect(1, 2, Duration.WhileOnBattlefield),
                DeliriumCondition.instance, "{this} gets +1/+2"
        ));
        ability.addEffect(new ConditionalContinuousEffect(
                new GainAbilitySourceEffect(VigilanceAbility.getInstance()), DeliriumCondition.instance,
                "and has vigilance as long as there are four or more card types among cards in your graveyard"
        ));
        this.addAbility(ability.addHint(CardTypesInGraveyardCount.YOU.getHint()).setAbilityWord(AbilityWord.DELIRIUM));
    }

    private SpineseekerCentipede(final SpineseekerCentipede card) {
        super(card);
    }

    @Override
    public SpineseekerCentipede copy() {
        return new SpineseekerCentipede(this);
    }
}
