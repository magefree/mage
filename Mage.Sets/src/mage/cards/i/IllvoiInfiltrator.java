
package mage.cards.i;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.CantBeBlockedSourceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.game.Game;
import mage.watchers.common.SpellsCastWatcher;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class IllvoiInfiltrator extends CardImpl {

    public IllvoiInfiltrator(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");

        this.subtype.add(SubType.JELLYFISH);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // This creature can't be blocked if you've cast two or more spells this turn.
        this.addAbility(new SimpleStaticAbility(
                new ConditionalContinuousEffect(
                        new GainAbilitySourceEffect(new CantBeBlockedSourceAbility(), Duration.WhileOnBattlefield),
                        IllvoiInfiltratorCondition.instance,
                        "This creature can't be blocked if you've cast two or more spells this turn"
                )
        ));

        // Whenever this creature deals combat damage to a player, draw a card.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(
                new DrawCardSourceControllerEffect(1), false
        ));
    }

    private IllvoiInfiltrator(final IllvoiInfiltrator card) {
        super(card);
    }

    @Override
    public IllvoiInfiltrator copy() {
        return new IllvoiInfiltrator(this);
    }
}

enum IllvoiInfiltratorCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        return game
                .getState()
                .getWatcher(SpellsCastWatcher.class)
                .getSpellsCastThisTurn(source.getControllerId())
                .size() >= 2;
    }
}
