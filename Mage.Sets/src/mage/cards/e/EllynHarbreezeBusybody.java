package mage.cards.e;

import mage.MageInt;
import mage.abilities.common.ChooseABackgroundAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.dynamicvalue.common.TokensCreatedThisTurnCount;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.abilities.effects.common.LookLibraryControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.watchers.common.CreatedTokenWatcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class EllynHarbreezeBusybody extends CardImpl {

    public EllynHarbreezeBusybody(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.PEASANT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // {T}: Look at the top X cards of your library, where X is the number of tokens you created this turn. Put one of those cards into your hand and the rest on the bottom of your library in a random order.
        this.addAbility(new SimpleActivatedAbility(new LookLibraryAndPickControllerEffect(
                TokensCreatedThisTurnCount.instance, 1,
                LookLibraryControllerEffect.PutCards.HAND,
                LookLibraryControllerEffect.PutCards.BOTTOM_RANDOM
        ), new TapSourceCost()).addHint(TokensCreatedThisTurnCount.getHint()), new CreatedTokenWatcher());

        // Choose a Background
        this.addAbility(ChooseABackgroundAbility.getInstance());
    }

    private EllynHarbreezeBusybody(final EllynHarbreezeBusybody card) {
        super(card);
    }

    @Override
    public EllynHarbreezeBusybody copy() {
        return new EllynHarbreezeBusybody(this);
    }
}
