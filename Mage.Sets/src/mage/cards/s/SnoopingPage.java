package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.abilityword.ReparteeAbility;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.abilities.effects.common.combat.CantBeBlockedSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SnoopingPage extends CardImpl {

    public SnoopingPage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}{B}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Repartee --Whenever you cast an instant or sorcery spell that targets a creature, this creature can't be blocked this turn.
        this.addAbility(new ReparteeAbility(new CantBeBlockedSourceEffect(Duration.EndOfTurn)));

        // Whenever this creature deals combat damage to a player, you draw a card and lose 1 life.
        Ability ability = new DealsCombatDamageToAPlayerTriggeredAbility(new DrawCardSourceControllerEffect(1, true));
        ability.addEffect(new LoseLifeSourceControllerEffect(1, false).concatBy("and"));
        this.addAbility(ability);
    }

    private SnoopingPage(final SnoopingPage card) {
        super(card);
    }

    @Override
    public SnoopingPage copy() {
        return new SnoopingPage(this);
    }
}
