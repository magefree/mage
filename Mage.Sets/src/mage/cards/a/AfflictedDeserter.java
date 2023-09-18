package mage.cards.a;

import mage.MageInt;
import mage.abilities.common.WerewolfFrontTriggeredAbility;
import mage.abilities.keyword.TransformAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author BetaSteward
 */
public final class AfflictedDeserter extends CardImpl {

    public AfflictedDeserter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WEREWOLF);

        this.secondSideCardClazz = mage.cards.w.WerewolfRansacker.class;

        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // At the beginning of each upkeep, if no spells were cast last turn, transform Afflicted Deserter.
        this.addAbility(new TransformAbility());
        this.addAbility(new WerewolfFrontTriggeredAbility());
    }

    private AfflictedDeserter(final AfflictedDeserter card) {
        super(card);
    }

    @Override
    public AfflictedDeserter copy() {
        return new AfflictedDeserter(this);
    }
}
