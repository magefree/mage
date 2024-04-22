package mage.cards.n;

import mage.MageInt;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.abilities.effects.common.RollDieWithResultTableEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Nothic extends CardImpl {

    public Nothic(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{B}");

        this.subtype.add(SubType.HORROR);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // Weird Insight — When Nothic dies, roll a d20.
        RollDieWithResultTableEffect effect = new RollDieWithResultTableEffect(20);

        // 1-9 | You draw a card and you lose 1 life.
        effect.addTableEntry(
                1, 9,
                new DrawCardSourceControllerEffect(1, "you"),
                new LoseLifeSourceControllerEffect(1).concatBy("and")
        );

        // 10-19 | You draw two cards and you lose 2 life.
        effect.addTableEntry(
                10, 19,
                new DrawCardSourceControllerEffect(2, "you"),
                new LoseLifeSourceControllerEffect(2).concatBy("and")
        );

        // 20 | You draw seven cards and you lose 7 life.
        effect.addTableEntry(
                20, 20,
                new DrawCardSourceControllerEffect(7, "you"),
                new LoseLifeSourceControllerEffect(7).concatBy("and")
        );

        this.addAbility(new DiesSourceTriggeredAbility(effect).withFlavorWord("Weird Insight"));
    }

    private Nothic(final Nothic card) {
        super(card);
    }

    @Override
    public Nothic copy() {
        return new Nothic(this);
    }
}
