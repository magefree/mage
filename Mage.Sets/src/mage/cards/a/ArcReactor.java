package mage.cards.a;

import java.util.UUID;

import mage.Mana;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.keyword.ImproviseAbility;
import mage.abilities.mana.SimpleManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;

/**
 *
 * @author muz
 */
public final class ArcReactor extends CardImpl {

    public ArcReactor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{5}");


        // Improvise
        this.addAbility(new ImproviseAbility());

        // This artifact enters tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());

        // {T}: Add {C}{C}{C}.
        this.addAbility(new SimpleManaAbility(Zone.BATTLEFIELD, Mana.ColorlessMana(3), new TapSourceCost()));
    }

    private ArcReactor(final ArcReactor card) {
        super(card);
    }

    @Override
    public ArcReactor copy() {
        return new ArcReactor(this);
    }
}
