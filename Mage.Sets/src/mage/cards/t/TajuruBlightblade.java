package mage.cards.t;

import java.util.UUID;

import mage.MageInt;
import mage.constants.SubType;
import mage.abilities.keyword.DeathtouchAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 * @author TheElk801
 */
public final class TajuruBlightblade extends CardImpl {

    public TajuruBlightblade(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{G}");

        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Deathtouch
        this.addAbility(DeathtouchAbility.getInstance());
    }

    private TajuruBlightblade(final TajuruBlightblade card) {
        super(card);
    }

    @Override
    public TajuruBlightblade copy() {
        return new TajuruBlightblade(this);
    }
}
