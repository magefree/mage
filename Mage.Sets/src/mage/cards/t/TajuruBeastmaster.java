
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
 * @author LevelX2
 */
public final class TajuruBeastmaster extends CardImpl {

    public TajuruBeastmaster(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{5}{G}");
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.WARRIOR);
        this.subtype.add(SubType.ALLY);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // <i>Rally</i> &mdash; Whenever Tajuru Beastmaster or another Ally creature enters the battlefield under your control, creatures you control get +1/+1 until end of turn.
        this.addAbility(new AllyEntersBattlefieldTriggeredAbility(new BoostControlledEffect(1, 1, Duration.EndOfTurn), false));
    }

    private TajuruBeastmaster(final TajuruBeastmaster card) {
        super(card);
    }

    @Override
    public TajuruBeastmaster copy() {
        return new TajuruBeastmaster(this);
    }
}
