package mage.cards.k;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.HateCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.SacrificeAllEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;
import mage.watchers.common.LifeLossOtherFromCombatWatcher;

import java.util.UUID;

/**
 * @author Merlingilb
 */
public class KnightsOfRen extends CardImpl {
    public KnightsOfRen(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}{R}");
        this.supertype.add(SuperType.LEGENDARY);
        this.addSubType(SubType.HUMAN);
        this.addSubType(SubType.SITH);
        this.power = new MageInt(4);
        this.toughness = new MageInt(2);

        //Menace
        this.addAbility(new MenaceAbility());

        //<i>Hate</i> &mdash; Whenever Knights of Ren enters the battlefield or attacks, if an opponent lost life from a source other
        //than combat damage this turn, you may have each player sacrifice a creature.
        Ability abilityEnterBattlefield = new ConditionalInterveningIfTriggeredAbility(
                new EntersBattlefieldTriggeredAbility(
                        new SacrificeAllEffect(1, StaticFilters.FILTER_CONTROLLED_CREATURE_SHORT_TEXT), true),
                HateCondition.instance,
                "<i>Hate</i> &mdash; When {this} enters the battlefield, if an opponent lost life from a source other than combat damage this turn, you may have each player sacrifice a creature");
        Ability abilityAttacks = new ConditionalInterveningIfTriggeredAbility(
                new AttacksTriggeredAbility(
                        new SacrificeAllEffect(1, StaticFilters.FILTER_CONTROLLED_CREATURE_SHORT_TEXT), true),
                HateCondition.instance,
                "<i>Hate</i> &mdash; When {this} attacks, if an opponent lost life from a source other than combat damage this turn, you may have each player sacrifice a creature");
        this.addAbility(abilityEnterBattlefield, new LifeLossOtherFromCombatWatcher());
        this.addAbility(abilityAttacks, new LifeLossOtherFromCombatWatcher());
    }

    public KnightsOfRen(final KnightsOfRen card) {
        super(card);
    }

    @Override
    public KnightsOfRen copy() {
        return new KnightsOfRen(this);
    }
}
