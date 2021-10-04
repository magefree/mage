package mage.cards.n;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class NoviceOccultist extends CardImpl {

    public NoviceOccultist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // When Novice Occultist dies, you draw a card and you lose 1 life.
        Ability ability = new DiesSourceTriggeredAbility(
                new DrawCardSourceControllerEffect(1).setText("you draw a card")
        );
        ability.addEffect(new LoseLifeSourceControllerEffect(1).concatBy("and"));
        this.addAbility(ability);
    }

    private NoviceOccultist(final NoviceOccultist card) {
        super(card);
    }

    @Override
    public NoviceOccultist copy() {
        return new NoviceOccultist(this);
    }
}
