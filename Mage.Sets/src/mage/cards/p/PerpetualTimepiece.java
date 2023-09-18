package mage.cards.p;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.ExileSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.MillCardsControllerEffect;
import mage.abilities.effects.common.ShuffleIntoLibraryTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author emerald000
 */
public final class PerpetualTimepiece extends CardImpl {

    public PerpetualTimepiece(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{2}");

        // {T}: Put the top two cards of your library into your graveyard.
        this.addAbility(new SimpleActivatedAbility(new MillCardsControllerEffect(2), new TapSourceCost()));

        // {2}, Exile Perpetual Timepiece: Shuffle any number of target cards from your graveyard into your library.
        Ability ability = new SimpleActivatedAbility(new ShuffleIntoLibraryTargetEffect(), new GenericManaCost(2));
        ability.addCost(new ExileSourceCost());
        ability.addTarget(new TargetCardInYourGraveyard(0, Integer.MAX_VALUE));
        this.addAbility(ability);
    }

    private PerpetualTimepiece(final PerpetualTimepiece card) {
        super(card);
    }

    @Override
    public PerpetualTimepiece copy() {
        return new PerpetualTimepiece(this);
    }
}
