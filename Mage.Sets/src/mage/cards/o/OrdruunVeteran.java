
package mage.cards.o;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.BattalionAbility;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;

/**
 *
 * @author LevelX2
 */
public final class OrdruunVeteran extends CardImpl {

    public OrdruunVeteran(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{R}{W}");
        this.subtype.add(SubType.MINOTAUR);
        this.subtype.add(SubType.SOLDIER);

        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // Battalion - Whenever Ordruun Veteran and at least two other creatures attack, Ordruun Veteran gains double strike until end of turn.
        this.addAbility(new BattalionAbility(new GainAbilitySourceEffect(DoubleStrikeAbility.getInstance(), Duration.EndOfTurn)));
    }

    private OrdruunVeteran(final OrdruunVeteran card) {
        super(card);
    }

    @Override
    public OrdruunVeteran copy() {
        return new OrdruunVeteran(this);
    }
}
