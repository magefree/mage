
package mage.cards.k;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.InspiredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;

/**
 *
 * @author LevelX2
 */
public final class KragmaButcher extends CardImpl {

    public KragmaButcher(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{R}");
        this.subtype.add(SubType.MINOTAUR);
        this.subtype.add(SubType.WARRIOR);

        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // <i>Inspired</i> &mdash; Whenever Kragma Butcher becomes untapped, it gets +2/+0 until end of turn.
        Effect effect = new BoostSourceEffect(2,0, Duration.EndOfTurn);
        effect.setText("it gets +2/+0 until end of turn");
        this.addAbility(new InspiredAbility(effect));
    }

    private KragmaButcher(final KragmaButcher card) {
        super(card);
    }

    @Override
    public KragmaButcher copy() {
        return new KragmaButcher(this);
    }
}
