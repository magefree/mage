
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.ManaWasSpentCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.ColoredManaSymbol;
import mage.constants.Duration;

/**
 *
 * @author Styxo
 */
public final class GruulScrapper extends CardImpl {

    public GruulScrapper(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{G}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.BERSERKER);

        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        //When Gruul Scrapper enters the battlefield, if Red was spent to cast Gruul Scrapper, it gains haste until end of turn.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new ConditionalContinuousEffect(new GainAbilitySourceEffect(HasteAbility.getInstance(), Duration.EndOfTurn), ManaWasSpentCondition.RED, " if {R} was spent to cast this spell, it gains haste until end of turn")));

    }

    private GruulScrapper(final GruulScrapper card) {
        super(card);
    }

    @Override
    public GruulScrapper copy() {
        return new GruulScrapper(this);
    }
}
