
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.TurnedFaceUpSourceTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.abilities.keyword.MorphAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author LevelX2
 */
public final class MonasteryLoremaster extends CardImpl {

    private static final FilterCard filter = new FilterCard("noncreature, nonland card from your graveyard");

    static {
        filter.add(Predicates.not(CardType.CREATURE.getPredicate()));
        filter.add(Predicates.not(CardType.LAND.getPredicate()));
    }

    public MonasteryLoremaster(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{U}");
        this.subtype.add(SubType.DJINN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Megamorph {5}{U}
        this.addAbility(new MorphAbility(new ManaCostsImpl<>("{5}{U}"), true));

        // When Monastery Loremaster is turned face up, return target noncreature, nonland card from your graveyard to your hand.
        Ability ability = new TurnedFaceUpSourceTriggeredAbility(new ReturnFromGraveyardToHandTargetEffect());
        ability.addTarget(new TargetCardInYourGraveyard(filter));
        this.addAbility(ability);

    }

    private MonasteryLoremaster(final MonasteryLoremaster card) {
        super(card);
    }

    @Override
    public MonasteryLoremaster copy() {
        return new MonasteryLoremaster(this);
    }
}
