package mage.cards.s;

import java.util.UUID;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.ExileGraveyardAllTargetPlayerEffect;
import mage.abilities.effects.common.replacement.CreaturesAreExiledOnDeathReplacementEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;
import mage.target.TargetPlayer;

/**
 *
 * @author Susucr
 */
public final class StoneOfErech extends CardImpl {

    public StoneOfErech(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}");
        
        this.supertype.add(SuperType.LEGENDARY);

        // If a creature an opponent controls would die, exile it instead.
        this.addAbility(new SimpleStaticAbility(
            new CreaturesAreExiledOnDeathReplacementEffect(StaticFilters.FILTER_OPPONENTS_PERMANENT_CREATURE)
        ));

        // {2}, {T}, Sacrifice Stone of Erech: Exile target player's graveyard. Draw a card.
        SimpleActivatedAbility ability = new SimpleActivatedAbility(
            new ExileGraveyardAllTargetPlayerEffect(), new TapSourceCost()
        );
        ability.addEffect(new DrawCardSourceControllerEffect(1));
        ability.addCost(new GenericManaCost(2));
        ability.addCost(new SacrificeSourceCost());
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);
    }

    private StoneOfErech(final StoneOfErech card) {
        super(card);
    }

    @Override
    public StoneOfErech copy() {
        return new StoneOfErech(this);
    }
}
