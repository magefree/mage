package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterCard;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author Plopman
 */
public final class SkullOfOrm extends CardImpl {

    private static final FilterCard filter = new FilterCard("enchantment card from your graveyard");

    static {
        filter.add(CardType.ENCHANTMENT.getPredicate());
    }

    public SkullOfOrm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        // {5}, {tap}: Return target enchantment card from your graveyard to your hand.
        Ability ability = new SimpleActivatedAbility(new ReturnFromGraveyardToHandTargetEffect(), new ManaCostsImpl<>("{5}"));
        ability.addTarget(new TargetCardInYourGraveyard(filter));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    private SkullOfOrm(final SkullOfOrm card) {
        super(card);
    }

    @Override
    public SkullOfOrm copy() {
        return new SkullOfOrm(this);
    }
}
