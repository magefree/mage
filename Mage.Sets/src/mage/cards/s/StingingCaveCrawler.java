package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.condition.common.DescendCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class StingingCaveCrawler extends CardImpl {

    public StingingCaveCrawler(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");

        this.subtype.add(SubType.INSECT);
        this.subtype.add(SubType.HORROR);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Deathtouch
        this.addAbility(DeathtouchAbility.getInstance());

        // Descend 4 -- Whenever Stinging Cave Crawler attacks, if there are four or more permanent cards in your graveyard, you draw a card and you lose 1 life.
        Ability ability = new ConditionalInterveningIfTriggeredAbility(
                new AttacksTriggeredAbility(new DrawCardSourceControllerEffect(1)),
                DescendCondition.FOUR, "Whenever {this} attacks, if there are four " +
                "or more permanent cards in your graveyard, you draw a card and you lose 1 life."
        );
        ability.addEffect(new LoseLifeSourceControllerEffect(1));
        this.addAbility(ability.setAbilityWord(AbilityWord.DESCEND_4).addHint(DescendCondition.getHint()));
    }

    private StingingCaveCrawler(final StingingCaveCrawler card) {
        super(card);
    }

    @Override
    public StingingCaveCrawler copy() {
        return new StingingCaveCrawler(this);
    }
}
