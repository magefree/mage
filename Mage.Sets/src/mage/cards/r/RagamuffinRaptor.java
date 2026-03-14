package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.target.common.TargetCardInYourGraveyard;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class RagamuffinRaptor extends CardImpl {

    private static final FilterCard filter = new FilterCard("creature or Food card from your graveyard");

    static {
        filter.add(Predicates.or(
            CardType.CREATURE.getPredicate(),
            SubType.FOOD.getPredicate()
        ));
    }

    public RagamuffinRaptor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{G}");

        this.subtype.add(SubType.DINOSAUR);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // When this creature enters, return up to one target creature or Food card from your graveyard to your hand.
        Ability ability = new EntersBattlefieldTriggeredAbility(new ReturnFromGraveyardToHandTargetEffect());
        ability.addTarget(new TargetCardInYourGraveyard(0, 1, filter));
        this.addAbility(ability);
    }

    private RagamuffinRaptor(final RagamuffinRaptor card) {
        super(card);
    }

    @Override
    public RagamuffinRaptor copy() {
        return new RagamuffinRaptor(this);
    }
}
