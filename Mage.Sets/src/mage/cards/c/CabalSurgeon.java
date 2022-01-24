
package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.ExileFromGraveCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author markedagain
 */
public final class CabalSurgeon extends CardImpl {

    public CabalSurgeon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}{B}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.MINION);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // {2}{B}{B}, {tap}, Exile two cards from your graveyard: Return target creature card from your graveyard to your hand.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new ReturnToHandTargetEffect(), new ManaCostsImpl("{2}{B}{B}"));
        ability.addTarget(new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_CREATURE_YOUR_GRAVEYARD));
        ability.addCost(new TapSourceCost());
        ability.addCost(new ExileFromGraveCost(new TargetCardInYourGraveyard(2, StaticFilters.FILTER_CARDS_FROM_YOUR_GRAVEYARD)));
        this.addAbility(ability);
    }

    private CabalSurgeon(final CabalSurgeon card) {
        super(card);
    }

    @Override
    public CabalSurgeon copy() {
        return new CabalSurgeon(this);
    }
}
