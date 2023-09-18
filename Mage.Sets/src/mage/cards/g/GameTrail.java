package mage.cards.g;

import java.util.UUID;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.costs.common.RevealTargetFromHandCost;
import mage.abilities.effects.common.TapSourceUnlessPaysEffect;
import mage.abilities.mana.GreenManaAbility;
import mage.abilities.mana.RedManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.target.common.TargetCardInHand;

/**
 *
 * @author fireshoes
 */
public final class GameTrail extends CardImpl {

    private static final FilterCard filter = new FilterCard("a Mountain or Forest card from your hand");

    static {
        filter.add(Predicates.or(SubType.MOUNTAIN.getPredicate(),
                SubType.FOREST.getPredicate()));
    }

    public GameTrail(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},"");

        // As Game Trail enters the battlefield, you may reveal a Mountain or Forest card from your hand. If you don't, Game Trail enters the battlefield tapped.
        this.addAbility(new AsEntersBattlefieldAbility(new TapSourceUnlessPaysEffect(new RevealTargetFromHandCost(new TargetCardInHand(filter))),
                "you may reveal a Mountain or Forest card from your hand. If you don't, {this} enters the battlefield tapped"));

        // {T}: Add {R} or {G}.
        this.addAbility(new RedManaAbility());
        this.addAbility(new GreenManaAbility());
    }

    private GameTrail(final GameTrail card) {
        super(card);
    }

    @Override
    public GameTrail copy() {
        return new GameTrail(this);
    }
}
