package mage.cards.m;

import mage.MageInt;
import mage.abilities.keyword.LifelinkAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MolderingKarok extends CardImpl {

    public MolderingKarok(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}{G}");

        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.CROCODILE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());
    }

    private MolderingKarok(final MolderingKarok card) {
        super(card);
    }

    @Override
    public MolderingKarok copy() {
        return new MolderingKarok(this);
    }
}
