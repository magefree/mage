package mage.cards.k;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.AfflictAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

public class KhenraEternal extends CardImpl {

    public KhenraEternal(UUID ownerId, CardSetInfo cardSetInfo) {
        super(ownerId, cardSetInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");
        subtype.add("Zombie");
        subtype.add("Jackal");
        subtype.add("Warrior");
        power = new MageInt(2);
        toughness = new MageInt(2);

        // Afflict 1 (Whenever this creature becomes blocked, defending player loses 1 life.)
        addAbility(new AfflictAbility(1));

    }

    public KhenraEternal(final KhenraEternal khenraEternal) {
        super(khenraEternal);
    }

    public KhenraEternal copy() {
        return new KhenraEternal(this);
    }
}
