package mage.cards.f;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.MillCardsControllerEffect;
import mage.abilities.effects.common.ReturnCardChosenFromGraveyardEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.abilities.keyword.StationAbility;
import mage.abilities.keyword.StationLevelAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FellGravship extends CardImpl {

    private static final FilterCard filter = new FilterCard("creature or Spacecraft card from your graveyard");

    static {
        filter.add(Predicates.or(
                CardType.CREATURE.getPredicate(),
                SubType.SPACECRAFT.getPredicate()
        ));
    }

    public FellGravship(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}{B}");

        this.subtype.add(SubType.SPACECRAFT);

        // When this Spacecraft enters, mill three cards, then return a creature or Spacecraft card from your graveyard to your hand.
        Ability ability = new EntersBattlefieldTriggeredAbility(new MillCardsControllerEffect(3));
        ability.addEffect(new ReturnCardChosenFromGraveyardEffect(false, filter, PutCards.HAND)
                .concatBy(", then"));
        this.addAbility(ability);

        // Station
        this.addAbility(new StationAbility());

        // STATION 8+
        // Flying
        // Lifelink
        // 3/2
        this.addAbility(new StationLevelAbility(8)
                .withLevelAbility(FlyingAbility.getInstance())
                .withLevelAbility(LifelinkAbility.getInstance())
                .withPT(3, 2));
    }

    private FellGravship(final FellGravship card) {
        super(card);
    }

    @Override
    public FellGravship copy() {
        return new FellGravship(this);
    }
}
