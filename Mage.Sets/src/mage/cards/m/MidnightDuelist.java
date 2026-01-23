package mage.cards.m;

import mage.MageInt;
import mage.abilities.keyword.ProtectionAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;

import java.util.UUID;

/**
 * @author noxx
 */
public final class MidnightDuelist extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent(SubType.VAMPIRE, "Vampires");

    public MidnightDuelist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);

        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // Protection from Vampires
        this.addAbility(new ProtectionAbility(filter));
    }

    private MidnightDuelist(final MidnightDuelist card) {
        super(card);
    }

    @Override
    public MidnightDuelist copy() {
        return new MidnightDuelist(this);
    }
}
