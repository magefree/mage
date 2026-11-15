package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.ExileSourceFromGraveCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author muz
 */
public final class TheoreticalNecromancer extends CardImpl {

    private static final FilterCard filter = new FilterCreatureCard("another target creature card from your graveyard");

    static {
        filter.add(AnotherPredicate.instance);
    }

    public TheoreticalNecromancer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");

        this.subtype.add(SubType.VAMPIRE);
        this.subtype.add(SubType.WARLOCK);
        this.power = new MageInt(4);
        this.toughness = new MageInt(1);

        // {3}{B}, Exile this card from your graveyard: Return another target creature card from your graveyard to your hand.
        Ability ability = new SimpleActivatedAbility(
            Zone.GRAVEYARD, new ReturnFromGraveyardToHandTargetEffect(), new ManaCostsImpl<>("{3}{B}")
        );
        ability.addCost(new ExileSourceFromGraveCost());
        ability.addTarget(new TargetCardInYourGraveyard(filter));
        this.addAbility(ability);
    }

    private TheoreticalNecromancer(final TheoreticalNecromancer card) {
        super(card);
    }

    @Override
    public TheoreticalNecromancer copy() {
        return new TheoreticalNecromancer(this);
    }
}
