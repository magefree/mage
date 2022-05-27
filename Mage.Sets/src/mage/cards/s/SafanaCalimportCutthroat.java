package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.common.ChooseABackgroundAbility;
import mage.abilities.condition.common.CompletedDungeonCondition;
import mage.abilities.condition.common.HaveInitiativeCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.game.permanent.token.TreasureToken;
import mage.watchers.common.CompletedDungeonWatcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SafanaCalimportCutthroat extends CardImpl {

    public SafanaCalimportCutthroat(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Menace
        this.addAbility(new MenaceAbility(false));

        // At the beginning of your end step, if you have the initiative, create a Treasure token. If you've completed a dungeon, create three of those tokens instead.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(
                new ConditionalOneShotEffect(
                        new CreateTokenEffect(new TreasureToken(), 3),
                        new CreateTokenEffect(new TreasureToken()),
                        CompletedDungeonCondition.instance, "create a Treasure token. " +
                        "If you've completed a dungeon, create three of those tokens instead"
                ),
                TargetController.YOU, HaveInitiativeCondition.instance, false
        ).addHint(CompletedDungeonCondition.getHint()), new CompletedDungeonWatcher());

        // Choose a Background
        this.addAbility(ChooseABackgroundAbility.getInstance());
    }

    private SafanaCalimportCutthroat(final SafanaCalimportCutthroat card) {
        super(card);
    }

    @Override
    public SafanaCalimportCutthroat copy() {
        return new SafanaCalimportCutthroat(this);
    }
}
