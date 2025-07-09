package mage.cards.e;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.OneOrMoreCombatDamagePlayerTriggeredAbility;
import mage.abilities.condition.common.VoidCondition;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.abilities.keyword.LifelinkAbility;
import mage.abilities.triggers.BeginningOfEndStepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.game.permanent.token.RobotToken;
import mage.watchers.common.VoidWatcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ElegyAcolyte extends CardImpl {

    public ElegyAcolyte(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}{B}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());

        // Whenever one or more creatures you control deal combat damage to a player, you draw a card and lose 1 life.
        Ability ability = new OneOrMoreCombatDamagePlayerTriggeredAbility(
                new DrawCardSourceControllerEffect(1, true), StaticFilters.FILTER_CONTROLLED_CREATURES
        );
        ability.addEffect(new LoseLifeSourceControllerEffect(1).setText("and lose 1 life"));
        this.addAbility(ability);

        // Void -- At the beginning of your end step, if a nonland permanent left the battlefield this turn or a spell was warped this turn, create a 2/2 colorless Robot artifact creature token.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(new CreateTokenEffect(new RobotToken()))
                .withInterveningIf(VoidCondition.instance).setAbilityWord(AbilityWord.VOID).addHint(VoidCondition.getHint()), new VoidWatcher());
    }

    private ElegyAcolyte(final ElegyAcolyte card) {
        super(card);
    }

    @Override
    public ElegyAcolyte copy() {
        return new ElegyAcolyte(this);
    }
}
