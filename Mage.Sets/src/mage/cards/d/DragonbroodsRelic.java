package mage.cards.d;

import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapTargetCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.mana.AnyColorManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.game.permanent.token.ReliquaryDragonToken;
import mage.target.common.TargetControlledPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DragonbroodsRelic extends CardImpl {

    public DragonbroodsRelic(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}{G}");

        // {T}, Tap an untapped creature you control: Add one mana of any color.
        Ability ability = new AnyColorManaAbility();
        ability.addCost(new TapTargetCost(new TargetControlledPermanent(StaticFilters.FILTER_CONTROLLED_UNTAPPED_CREATURE)));
        this.addAbility(ability);

        // {3}{W}{U}{B}{R}{G}, Sacrifice this artifact: Create a 4/4 Dragon creature token named Reliquary Dragon that's all colors. It has flying, lifelink, and "When this token enters, it deals 3 damage to any target." Activate only as a sorcery.
        ability = new ActivateAsSorceryActivatedAbility(
                new CreateTokenEffect(new ReliquaryDragonToken()), new ManaCostsImpl<>("{3}{W}{U}{B}{R}{G}")
        );
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);
    }

    private DragonbroodsRelic(final DragonbroodsRelic card) {
        super(card);
    }

    @Override
    public DragonbroodsRelic copy() {
        return new DragonbroodsRelic(this);
    }
}
