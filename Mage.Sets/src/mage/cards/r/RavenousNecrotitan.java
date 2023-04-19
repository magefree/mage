package mage.cards.r;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.InvertCondition;
import mage.abilities.condition.common.CorruptedCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.SacrificeControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RavenousNecrotitan extends CardImpl {

    private static final Condition condition = new InvertCondition(CorruptedCondition.instance);

    public RavenousNecrotitan(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}{B}");

        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.HORROR);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Corrupted -- When Ravenous Necrotitan enters the battlefield, sacrifice a creature unless an opponent has three or more poison counters.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new ConditionalOneShotEffect(
                new SacrificeControllerEffect(StaticFilters.FILTER_PERMANENT_CREATURE, 1, ""),
                condition, "sacrifice a creature unless an opponent has three or more poison counters"
        )).setAbilityWord(AbilityWord.CORRUPTED).addHint(CorruptedCondition.getHint()));
    }

    private RavenousNecrotitan(final RavenousNecrotitan card) {
        super(card);
    }

    @Override
    public RavenousNecrotitan copy() {
        return new RavenousNecrotitan(this);
    }
}
