package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.HateCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.watchers.common.LifeLossOtherFromCombatWatcher;

/**
 *
 * @author Styxo
 */
public final class SithSorcerer extends CardImpl {

    public SithSorcerer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{U}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SITH);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When Sith Sorcerer enters the battlefield, scry 2.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new ScryEffect(2)));

        // <i>Hate</i> &mdash; When Sith Sorcerer enters the battlefield, if an opponent lost life from a source other than combat damage this turn, draw a card.
        Ability ability = new ConditionalInterveningIfTriggeredAbility(
                new DiesSourceTriggeredAbility(new DrawCardSourceControllerEffect(1)),
                HateCondition.instance,
                "<i>Hate</i> &mdash; When {this} dies, if an opponent lost life from a source other than combat damage this turn, draw a card.");
        this.addAbility(ability, new LifeLossOtherFromCombatWatcher());

    }

    private SithSorcerer(final SithSorcerer card) {
        super(card);
    }

    @Override
    public SithSorcerer copy() {
        return new SithSorcerer(this);
    }
}
