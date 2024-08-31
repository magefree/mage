package mage.cards.b;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.ExileSourceFromGraveCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BonebindOrator extends CardImpl {

    private static final FilterCard filter = new FilterCreatureCard("another target creature card from your graveyard");

    static {
        filter.add(AnotherPredicate.instance);
    }

    public BonebindOrator(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");

        this.subtype.add(SubType.SQUIRREL);
        this.subtype.add(SubType.WARLOCK);
        this.subtype.add(SubType.BARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // {3}{B}, Exile Bonebind Orator from your graveyard: Return another target creature card from your graveyard to your hand.
        Ability ability = new SimpleActivatedAbility(
                Zone.GRAVEYARD, new ReturnFromGraveyardToHandTargetEffect(), new ManaCostsImpl<>("{3}{B}")
        );
        ability.addCost(new ExileSourceFromGraveCost());
        ability.addTarget(new TargetCardInYourGraveyard(filter));
        this.addAbility(ability);
    }

    private BonebindOrator(final BonebindOrator card) {
        super(card);
    }

    @Override
    public BonebindOrator copy() {
        return new BonebindOrator(this);
    }
}
