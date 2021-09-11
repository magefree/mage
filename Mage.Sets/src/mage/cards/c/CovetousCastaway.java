package mage.cards.c;

import mage.MageInt;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.MillCardsControllerEffect;
import mage.abilities.keyword.DisturbAbility;
import mage.abilities.keyword.TransformAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CovetousCastaway extends CardImpl {

    public CovetousCastaway(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}");

        this.subtype.add(SubType.HUMAN);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);
        this.transformable = true;
        this.secondSideCardClazz = mage.cards.g.GhostlyCastigator.class;

        // When Covetous Castaway dies, mill three cards.
        this.addAbility(new DiesSourceTriggeredAbility(new MillCardsControllerEffect(3)));

        // Disturb {3}{U}{U}
        this.addAbility(new TransformAbility());
        this.addAbility(new DisturbAbility(new ManaCostsImpl<>("{3}{U}{U}")));
    }

    private CovetousCastaway(final CovetousCastaway card) {
        super(card);
    }

    @Override
    public CovetousCastaway copy() {
        return new CovetousCastaway(this);
    }
}
