package mage.cards.f;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.dynamicvalue.common.ArtifactYouControlCount;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.abilities.hint.common.ArtifactYouControlHint;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.PutCards;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FomoriVault extends CardImpl {

    public FomoriVault(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // {T}: Add {1}.
        this.addAbility(new ColorlessManaAbility());

        // {3}, {T}, Discard a card: Look at the top X cards of your library, where X is the number of artifacts you control. Put one of those cards into your hand and the rest on the bottom of your library in a random order.
        Ability ability = new SimpleActivatedAbility(new LookLibraryAndPickControllerEffect(
                ArtifactYouControlCount.instance, 1, PutCards.HAND, PutCards.BOTTOM_RANDOM
        ).setText("Look at the top X cards of your library, where X is the number of artifacts you control. " +
                "Put one of those cards into your hand and the rest on the bottom of your library in a random order"
        ), new GenericManaCost(3));
        ability.addCost(new TapSourceCost());
        ability.addCost(new DiscardCardCost());
        this.addAbility(ability.addHint(ArtifactYouControlHint.instance));
    }

    private FomoriVault(final FomoriVault card) {
        super(card);
    }

    @Override
    public FomoriVault copy() {
        return new FomoriVault(this);
    }
}
