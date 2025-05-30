package mage.cards.m;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.ExileTopXMayPlayUntilEffect;
import mage.abilities.keyword.UnearthAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MishrasResearchDesk extends CardImpl {

    public MishrasResearchDesk(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}");

        // {1}, {T}, Sacrifice Mishra's Research Desk: Exile the top two cards of your library. Choose one of them. Until the end of your next turn, you may play that card.
        Ability ability = new SimpleActivatedAbility(new ExileTopXMayPlayUntilEffect(
                2, true, Duration.UntilEndOfYourNextTurn
        ), new GenericManaCost(1));
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);

        // Unearth {1}{R}
        this.addAbility(new UnearthAbility(new ManaCostsImpl<>("{1}{R}")));
    }

    private MishrasResearchDesk(final MishrasResearchDesk card) {
        super(card);
    }

    @Override
    public MishrasResearchDesk copy() {
        return new MishrasResearchDesk(this);
    }
}
