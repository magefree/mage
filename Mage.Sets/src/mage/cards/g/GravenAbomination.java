
package mage.cards.g;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.predicate.card.DefendingPlayerOwnsCardPredicate;
import mage.target.common.TargetCardInGraveyard;

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class GravenAbomination extends CardImpl {

    private static final String rule = "Whenever {this} attacks, exile target card from defending player's graveyard.";
    private static final FilterCard filter = new FilterCard("card from defending player's graveyard");

    static {
        filter.add(DefendingPlayerOwnsCardPredicate.instance);
    }

    public GravenAbomination(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{3}");

        this.subtype.add(SubType.HORROR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // Whenever Graven Abomination attacks, exile target card from defending player's graveyard.
        Ability ability = new AttacksTriggeredAbility(new ExileTargetEffect(), false, rule);
        ability.addTarget(new TargetCardInGraveyard(filter));
        this.addAbility(ability);
    }

    private GravenAbomination(final GravenAbomination card) {
        super(card);
    }

    @Override
    public GravenAbomination copy() {
        return new GravenAbomination(this);
    }
}
