
package mage.cards.o;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.keyword.InspiredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LevelX2
 */
public final class OreskosSunGuide extends CardImpl {

    public OreskosSunGuide(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{W}");
        this.subtype.add(SubType.CAT);
        this.subtype.add(SubType.MONK);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // <i>Inspired</i> &mdash; Whenever Oreskos Sun Guide becomes untapped, you gain 2 life.
        this.addAbility(new InspiredAbility(new GainLifeEffect(2)));
    }

    private OreskosSunGuide(final OreskosSunGuide card) {
        super(card);
    }

    @Override
    public OreskosSunGuide copy() {
        return new OreskosSunGuide(this);
    }
}
