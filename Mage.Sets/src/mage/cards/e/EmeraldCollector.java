package mage.cards.e;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.common.DrawNthCardTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.ConjureCardEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.SetBasePowerToughnessSourceEffect;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;

/**
 *
 * @author muz
 */
public final class EmeraldCollector extends CardImpl {

    public EmeraldCollector(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.PIRATE);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // Whenever Emerald Collector deals combat damage to a player, draw a card.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(
            new DrawCardSourceControllerEffect(1)
        ));

        // When you draw your third card in a turn, conjure a card named Mox Emerald into your hand. This ability triggers only once.
        this.addAbility(
            new DrawNthCardTriggeredAbility(
                new ConjureCardEffect("Mox Emerald"),
                false, 3
            )
            .setTriggersLimitEachGame(1)
            .setTriggerPhrase("When you draw your third card in a turn, ")
        );

        // {2}{G}: Emerald Collector has base power and toughness 4/4 until end of turn.
        this.addAbility(new SimpleActivatedAbility(
            new SetBasePowerToughnessSourceEffect(4, 4, Duration.EndOfTurn),
            new ManaCostsImpl<>("{2}{G}")
        ));
    }

    private EmeraldCollector(final EmeraldCollector card) {
        super(card);
    }

    @Override
    public EmeraldCollector copy() {
        return new EmeraldCollector(this);
    }
}
