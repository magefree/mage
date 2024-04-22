package mage.cards.g;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.keyword.ReachAbility;
import mage.cards.AdventureCard;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;
import mage.game.permanent.token.TreasureToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GrabbyGiant extends AdventureCard {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("an artifact or land");

    static {
        filter.add(Predicates.or(
                CardType.ARTIFACT.getPredicate(),
                CardType.LAND.getPredicate()
        ));
    }

    public GrabbyGiant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, new CardType[]{CardType.INSTANT}, "{3}{R}", "That's Mine", "{1}{R}");

        this.subtype.add(SubType.GIANT);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // Reach
        this.addAbility(ReachAbility.getInstance());

        // {2}{R}, Sacrifice an artifact or land: Draw a card.
        Ability ability = new SimpleActivatedAbility(
                new DrawCardSourceControllerEffect(1), new ManaCostsImpl<>("{2}{R}")
        );
        ability.addCost(new SacrificeTargetCost(filter));
        this.addAbility(ability);

        // That's Mine
        // Create a Treasure token.
        this.getSpellCard().getSpellAbility().addEffect(new CreateTokenEffect(new TreasureToken()));

        this.finalizeAdventure();
    }

    private GrabbyGiant(final GrabbyGiant card) {
        super(card);
    }

    @Override
    public GrabbyGiant copy() {
        return new GrabbyGiant(this);
    }
}
