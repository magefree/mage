package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.common.DrawCardAllEffect;
import mage.abilities.effects.common.LoseLifeAllPlayersEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class StormfistCrusader extends CardImpl {

    public StormfistCrusader(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{B}{R}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Menace
        this.addAbility(new MenaceAbility(false));

        // At the beginning of your upkeep, each player draws a card and loses 1 life.
        Ability ability = new BeginningOfUpkeepTriggeredAbility(
                new DrawCardAllEffect(1), TargetController.YOU, false
        );
        ability.addEffect(new LoseLifeAllPlayersEffect(StaticValue.get(1), "and loses 1 life"));
        this.addAbility(ability);
    }

    private StormfistCrusader(final StormfistCrusader card) {
        super(card);
    }

    @Override
    public StormfistCrusader copy() {
        return new StormfistCrusader(this);
    }
}
