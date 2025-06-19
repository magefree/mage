package mage.cards.k;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldOrAttacksSourceTriggeredAbility;
import mage.abilities.condition.common.HateCondition;
import mage.abilities.effects.common.SacrificeAllEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
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
        this.addAbility(new EntersBattlefieldOrAttacksSourceTriggeredAbility(
                new SacrificeAllEffect(StaticFilters.FILTER_PERMANENT_CREATURE).setText("have each player sacrifice a creature")
        ).withInterveningIf(HateCondition.instance).setAbilityWord(AbilityWord.HATE), new LifeLossOtherFromCombatWatcher());
    }

    private KnightsOfRen(final KnightsOfRen card) {
        super(card);
    }

    @Override
    public KnightsOfRen copy() {
        return new KnightsOfRen(this);
    }
}
