package mage.cards.g;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.common.CardsLeaveGraveyardTriggeredAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldWithCounterTargetEffect;
import mage.constants.SuperType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.permanent.token.ZombieToken;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author Grath
 */
public final class GrimReapersScythe extends CardImpl {

    public GrimReapersScythe(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}{B}");
        
        this.supertype.add(SuperType.LEGENDARY);

        // Whenever one or more creature cards leave your graveyard, create a 2/2 black Zombie creature token.
        this.addAbility(new CardsLeaveGraveyardTriggeredAbility(
                new CreateTokenEffect(new ZombieToken()), StaticFilters.FILTER_CARD_CREATURES
        ));

        // {3}{B}, {T}, Sacrifice two creatures: Return target creature card from your graveyard to the battlefield with a finality counter on it. Activate only as a sorcery.
        Ability ability = new ActivateAsSorceryActivatedAbility(
                new ReturnFromGraveyardToBattlefieldWithCounterTargetEffect(CounterType.FINALITY.createInstance()), new ManaCostsImpl<>("{3}{B}")
        );
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeTargetCost(2, StaticFilters.FILTER_PERMANENT_CREATURES));
        ability.addTarget(new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_CREATURE_YOUR_GRAVEYARD));
        this.addAbility(ability);
    }

    private GrimReapersScythe(final GrimReapersScythe card) {
        super(card);
    }

    @Override
    public GrimReapersScythe copy() {
        return new GrimReapersScythe(this);
    }
}
