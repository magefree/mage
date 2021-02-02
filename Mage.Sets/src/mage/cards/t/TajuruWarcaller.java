
package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.AllyEntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;

/**
 *
 * @author fireshoes
 */
public final class TajuruWarcaller extends CardImpl {

    public TajuruWarcaller(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{G}{G}");
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.WARRIOR);
        this.subtype.add(SubType.ALLY);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // <i>Rally</i>-Whenever Tajuru Warcaller or another Ally enters the battlefield under your control, creatures you control get +2/+2 until end of turn.
        this.addAbility(new AllyEntersBattlefieldTriggeredAbility(new BoostControlledEffect(2, 2, Duration.EndOfTurn), false));
    }

    private TajuruWarcaller(final TajuruWarcaller card) {
        super(card);
    }

    @Override
    public TajuruWarcaller copy() {
        return new TajuruWarcaller(this);
    }
}
