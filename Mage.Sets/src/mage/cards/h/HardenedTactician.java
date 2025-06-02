package mage.cards.h;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.permanent.TokenPredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class HardenedTactician extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("a token");

    static {
        filter.add(TokenPredicate.TRUE);
    }

    public HardenedTactician(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}{B}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // {1}, Sacrifice a token: Draw a card.
        Ability ability = new SimpleActivatedAbility(new DrawCardSourceControllerEffect(1), new GenericManaCost(1));
        ability.addCost(new SacrificeTargetCost(filter));
        this.addAbility(ability);
    }

    private HardenedTactician(final HardenedTactician card) {
        super(card);
    }

    @Override
    public HardenedTactician copy() {
        return new HardenedTactician(this);
    }
}
