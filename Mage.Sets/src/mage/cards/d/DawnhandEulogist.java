package mage.cards.d;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.CardsInControllerGraveyardCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.LoseLifeOpponentsEffect;
import mage.abilities.effects.common.MillCardsControllerEffect;
import mage.abilities.hint.ConditionHint;
import mage.abilities.hint.Hint;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterCard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DawnhandEulogist extends CardImpl {

    private static final Condition condition = new CardsInControllerGraveyardCondition(1, new FilterCard(SubType.ELF));
    private static final Hint hint = new ConditionHint(condition, "There is an Elf card in your graveyard");

    public DawnhandEulogist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}");

        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.WARLOCK);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Menace
        this.addAbility(new MenaceAbility());

        // When this creature enters, mill three cards. Then if there is an Elf card in your graveyard, each opponent loses 2 life and you gain 2 life.
        Ability ability = new EntersBattlefieldTriggeredAbility(new MillCardsControllerEffect(3));
        ability.addEffect(new ConditionalOneShotEffect(
                new LoseLifeOpponentsEffect(2), condition, "Then if there is an Elf " +
                "card in your graveyard, each opponent loses 2 life and you gain 2 life"
        ).addEffect(new GainLifeEffect(2)));
        this.addAbility(ability.addHint(hint));
    }

    private DawnhandEulogist(final DawnhandEulogist card) {
        super(card);
    }

    @Override
    public DawnhandEulogist copy() {
        return new DawnhandEulogist(this);
    }
}
