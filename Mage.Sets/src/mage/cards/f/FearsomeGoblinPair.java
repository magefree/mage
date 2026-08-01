package mage.cards.f;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.keyword.AmassEffect;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class FearsomeGoblinPair extends CardImpl {

    public FearsomeGoblinPair(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B/R}");

        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // When this creature dies, amass Goblins 4.
        this.addAbility(new DiesSourceTriggeredAbility(new AmassEffect(4, SubType.GOBLIN)));
    }

    private FearsomeGoblinPair(final FearsomeGoblinPair card) {
        super(card);
    }

    @Override
    public FearsomeGoblinPair copy() {
        return new FearsomeGoblinPair(this);
    }
}
