package mage.cards.l;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.GainLifeEffect;
import mage.constants.SubType;
import mage.abilities.keyword.ConvokeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author TheElk801
 */
public final class LoxodonRestorer extends CardImpl {

    public LoxodonRestorer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{W}{W}");

        this.subtype.add(SubType.ELEPHANT);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Convoke
        this.addAbility(new ConvokeAbility());

        // When Loxodon Restorer enters the battlefield, you gain 4 life.
        this.addAbility(new EntersBattlefieldTriggeredAbility(
                new GainLifeEffect(4), false
        ));
    }

    private LoxodonRestorer(final LoxodonRestorer card) {
        super(card);
    }

    @Override
    public LoxodonRestorer copy() {
        return new LoxodonRestorer(this);
    }
}
