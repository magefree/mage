package mage.cards.o;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.RevealedOrControlledDragonCondition;
import mage.abilities.costs.common.RevealDragonFromHandCost;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.keyword.DefenderAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.watchers.common.DragonOnTheBattlefieldWhileSpellWasCastWatcher;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class OratorOfOjutai extends CardImpl {

    public OratorOfOjutai(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");
        this.subtype.add(SubType.BIRD);
        this.subtype.add(SubType.MONK);
        this.power = new MageInt(0);
        this.toughness = new MageInt(4);

        // Defender, flying
        this.addAbility(DefenderAbility.getInstance());
        this.addAbility(FlyingAbility.getInstance());

        // As an additional cost to cast Orator of Ojutai, you may reveal a Dragon card from your hand.
        this.getSpellAbility().addCost(new RevealDragonFromHandCost());

        // When Orator of Ojutai enters the battlefield, if you revealed a Dragon card or controlled a Dragon as you cast Orator of Ojutai, draw a card.
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(
                new EntersBattlefieldTriggeredAbility(new DrawCardSourceControllerEffect(1)),
                RevealedOrControlledDragonCondition.instance, "When {this} enters the battlefield, " +
                "if you revealed a Dragon card or controlled a Dragon as you cast this spell, draw a card."
        ), new DragonOnTheBattlefieldWhileSpellWasCastWatcher());
    }

    private OratorOfOjutai(final OratorOfOjutai card) {
        super(card);
    }

    @Override
    public OratorOfOjutai copy() {
        return new OratorOfOjutai(this);
    }
}
