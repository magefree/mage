package mage.cards.v;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.search.SearchLibraryPutInGraveyardEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class VileEntomber extends CardImpl {

    public VileEntomber(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}{B}");

        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.WARLOCK);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Deathtouch
        this.addAbility(DeathtouchAbility.getInstance());

        // When Vile Entomber enters the battlefield, search your library for a card, put that card into your graveyard, then shuffle.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new SearchLibraryPutInGraveyardEffect()));
    }

    private VileEntomber(final VileEntomber card) {
        super(card);
    }

    @Override
    public VileEntomber copy() {
        return new VileEntomber(this);
    }
}
