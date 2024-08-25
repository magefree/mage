package mage.cards.a;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.ExileSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.ExileGraveyardAllPlayersEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.abilities.mana.AnyColorManaAbility;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SuperType;
import mage.filter.FilterCard;
import mage.filter.common.FilterHistoricCard;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AbstergoEntertainment extends CardImpl {

    private static final FilterCard filter = new FilterHistoricCard("historic card from your graveyard");

    public AbstergoEntertainment(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        this.supertype.add(SuperType.LEGENDARY);

        // {T}: Add {C}.
        this.addAbility(new ColorlessManaAbility());

        // {1}, {T}: Add one mana of any color.
        Ability ability = new AnyColorManaAbility(new GenericManaCost(1));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);

        // {3}, {T}, Exile Abstergo Entertainment: Return up to one target historic card from your graveyard to your hand, then exile all graveyards.
        ability = new SimpleActivatedAbility(new ReturnFromGraveyardToHandTargetEffect(), new GenericManaCost(3));
        ability.addCost(new TapSourceCost());
        ability.addCost(new ExileSourceCost());
        ability.addEffect(new ExileGraveyardAllPlayersEffect().concatBy(", then"));
        ability.addTarget(new TargetCardInYourGraveyard(0, 1, filter));
        this.addAbility(ability);
    }

    private AbstergoEntertainment(final AbstergoEntertainment card) {
        super(card);
    }

    @Override
    public AbstergoEntertainment copy() {
        return new AbstergoEntertainment(this);
    }
}
