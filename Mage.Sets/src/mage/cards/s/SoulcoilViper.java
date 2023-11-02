package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldWithCounterTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SoulcoilViper extends CardImpl {

    public SoulcoilViper(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");

        this.subtype.add(SubType.SNAKE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // {B}, {T}, Sacrifice Soulcoil Viper: Return target creature card from your graveyard to the battlefield with a finality counter on it. Activate only as a sorcery.
        Ability ability = new ActivateAsSorceryActivatedAbility(
                new ReturnFromGraveyardToBattlefieldWithCounterTargetEffect(CounterType.FINALITY.createInstance()), new ManaCostsImpl<>("{B}")
        );
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeSourceCost());
        ability.addTarget(new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_CREATURE_YOUR_GRAVEYARD));
        this.addAbility(ability);
    }

    private SoulcoilViper(final SoulcoilViper card) {
        super(card);
    }

    @Override
    public SoulcoilViper copy() {
        return new SoulcoilViper(this);
    }
}
