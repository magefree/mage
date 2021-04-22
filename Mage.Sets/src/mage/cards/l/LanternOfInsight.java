package mage.cards.l;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.ShuffleLibraryTargetEffect;
import mage.abilities.effects.common.continuous.PlayWithTheTopCardRevealedEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.TargetPlayer;

import java.util.UUID;

/**
 * @author emerald000
 */
public final class LanternOfInsight extends CardImpl {

    public LanternOfInsight(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}");

        // Each player plays with the top card of their library revealed.
        this.addAbility(new SimpleStaticAbility(new PlayWithTheTopCardRevealedEffect(true)));

        // {tap}, Sacrifice Lantern of Insight: Target player shuffles their library.
        Ability ability = new SimpleActivatedAbility(new ShuffleLibraryTargetEffect().setText("target player shuffles"), new TapSourceCost());
        ability.addCost(new SacrificeSourceCost());
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);
    }

    private LanternOfInsight(final LanternOfInsight card) {
        super(card);
    }

    @Override
    public LanternOfInsight copy() {
        return new LanternOfInsight(this);
    }
}
