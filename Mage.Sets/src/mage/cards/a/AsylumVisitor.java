package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.CardsInHandCondition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.abilities.keyword.MadnessAbility;
import mage.abilities.triggers.BeginningOfUpkeepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.SubType;
import mage.constants.TargetController;

import java.util.UUID;

/**
 * @author escplan9 (Derek Monturo - dmontur1 at gmail dot com)
 */
public final class AsylumVisitor extends CardImpl {

    private static final Condition condition = new CardsInHandCondition(ComparisonType.EQUAL_TO, 0, TargetController.ACTIVE);

    public AsylumVisitor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");
        this.subtype.add(SubType.VAMPIRE);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // At the beginning of each player's upkeep, if that player has no cards in hand, you draw a card and you lose 1 life.
        Ability ability = new BeginningOfUpkeepTriggeredAbility(
                TargetController.EACH_PLAYER, new DrawCardSourceControllerEffect(1, true), false
        ).withInterveningIf(condition);
        ability.addEffect(new LoseLifeSourceControllerEffect(1).concatBy("and"));
        this.addAbility(ability);

        // Madness {1}{B}
        this.addAbility(new MadnessAbility(new ManaCostsImpl<>("{1}{B}")));
    }

    private AsylumVisitor(final AsylumVisitor card) {
        super(card);
    }

    @Override
    public AsylumVisitor copy() {
        return new AsylumVisitor(this);
    }
}
