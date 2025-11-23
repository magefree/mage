package mage.cards.w;

import mage.MageInt;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.condition.common.LessonsInGraveCondition;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class WalltopSentries extends CardImpl {

    public WalltopSentries(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.subtype.add(SubType.ALLY);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Reach
        this.addAbility(ReachAbility.getInstance());

        // Deathtouch
        this.addAbility(DeathtouchAbility.getInstance());

        // When this creature dies, if there's a Lesson card in your graveyard, you gain 2 life.
        this.addAbility(new DiesSourceTriggeredAbility(new GainLifeEffect(2))
                .withInterveningIf(LessonsInGraveCondition.ONE)
                .addHint(LessonsInGraveCondition.getHint()));
    }

    private WalltopSentries(final WalltopSentries card) {
        super(card);
    }

    @Override
    public WalltopSentries copy() {
        return new WalltopSentries(this);
    }
}
