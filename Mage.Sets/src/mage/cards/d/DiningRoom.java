package mage.cards.d;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.keyword.InvestigateEffect;
import mage.abilities.mana.GreenManaAbility;
import mage.abilities.mana.RedManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DiningRoom extends CardImpl {

    public DiningRoom(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // Dining Room enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());

        // {T}: Add {R} or {G}.
        this.addAbility(new RedManaAbility());
        this.addAbility(new GreenManaAbility());

        // {4}, {T}: Investigate.
        Ability ability = new SimpleActivatedAbility(new InvestigateEffect(), new GenericManaCost(4));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    private DiningRoom(final DiningRoom card) {
        super(card);
    }

    @Override
    public DiningRoom copy() {
        return new DiningRoom(this);
    }
}
