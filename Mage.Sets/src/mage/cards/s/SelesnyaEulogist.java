package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.effects.common.PopulateEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SelesnyaEulogist extends CardImpl {

    public SelesnyaEulogist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");

        this.subtype.add(SubType.CENTAUR);
        this.subtype.add(SubType.DRUID);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // {2}{G}: Exile target creature card from a graveyard, then populate.
        Ability ability = new SimpleActivatedAbility(
                new ExileTargetEffect().setText("exile target creature card from a graveyard,"),
                new ManaCostsImpl<>("{2}{G}")
        );
        ability.addEffect(new PopulateEffect("then"));
        ability.addTarget(new TargetCardInGraveyard(StaticFilters.FILTER_CARD_CREATURE));
        this.addAbility(ability);
    }

    private SelesnyaEulogist(final SelesnyaEulogist card) {
        super(card);
    }

    @Override
    public SelesnyaEulogist copy() {
        return new SelesnyaEulogist(this);
    }
}
