package mage.cards.l;

import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.keyword.ScryEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class LothlorienLookout extends CardImpl {

    public LothlorienLookout(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");

        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.SCOUT);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Whenever Lothlorien Lookout attacks, scry 1.
        this.addAbility(new AttacksTriggeredAbility(new ScryEffect(1, false)));
    }

    private LothlorienLookout(final LothlorienLookout card) {
        super(card);
    }

    @Override
    public LothlorienLookout copy() {
        return new LothlorienLookout(this);
    }
}
