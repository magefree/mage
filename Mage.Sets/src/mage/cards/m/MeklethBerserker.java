package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 * @author muz
 */
public final class MeklethBerserker extends CardImpl {

    public MeklethBerserker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}");
        
        this.subtype.add(SubType.KLINGON);
        this.subtype.add(SubType.BERSERKER);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // Menace
        this.addAbility(new MenaceAbility());
    }

    private MeklethBerserker(final MeklethBerserker card) {
        super(card);
    }

    @Override
    public MeklethBerserker copy() {
        return new MeklethBerserker(this);
    }
}
