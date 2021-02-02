
package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.ExileSourceEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.constants.SubType;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.target.common.TargetCardInGraveyard;

/**
 *
 * @author TheElk801
 */
public final class CelestialGatekeeper extends CardImpl {

    private static final FilterCard filter = new FilterCard("Bird and/or Cleric permanent cards");

    static {
        filter.add(TargetController.YOU.getOwnerPredicate());
        filter.add(Predicates.or(
                SubType.BIRD.getPredicate(),
                SubType.CLERIC.getPredicate()
        ));
    }

    public CelestialGatekeeper(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}{W}");

        this.subtype.add(SubType.BIRD);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Celestial Gatekeeper dies, exile it, then return up to two target Bird and/or Cleric permanent cards from your graveyard to the battlefield.
        Effect effect = new ExileSourceEffect();
        effect.setText("");
        DiesSourceTriggeredAbility ability = new DiesSourceTriggeredAbility(effect);
        effect = new ReturnFromGraveyardToBattlefieldTargetEffect();
        effect.setText("exile it, then return up to two target Bird and/or Cleric permanent cards from your graveyard to the battlefield");
        ability.addEffect(effect);
        ability.addTarget(new TargetCardInGraveyard(0, 2, filter));
        this.addAbility(ability);
    }

    private CelestialGatekeeper(final CelestialGatekeeper card) {
        super(card);
    }

    @Override
    public CelestialGatekeeper copy() {
        return new CelestialGatekeeper(this);
    }
}
