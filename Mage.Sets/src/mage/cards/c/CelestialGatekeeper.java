
package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.DiesTriggeredAbility;
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
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.filter.predicate.other.OwnerPredicate;
import mage.target.common.TargetCardInGraveyard;

/**
 *
 * @author TheElk801
 */
public final class CelestialGatekeeper extends CardImpl {

    private static final FilterCard filter = new FilterCard("Bird and/or Cleric permanent cards");

    static {
        filter.add(new OwnerPredicate(TargetController.YOU));
        filter.add(Predicates.or(
                new SubtypePredicate(SubType.BIRD),
                new SubtypePredicate(SubType.CLERIC)
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
        DiesTriggeredAbility ability = new DiesTriggeredAbility(effect);
        effect = new ReturnFromGraveyardToBattlefieldTargetEffect();
        effect.setText("exile it, then return up to two target Bird and/or Cleric permanent cards from your graveyard to the battlefield");
        ability.addEffect(effect);
        ability.addTarget(new TargetCardInGraveyard(0, 2, filter));
        this.addAbility(ability);
    }

    public CelestialGatekeeper(final CelestialGatekeeper card) {
        super(card);
    }

    @Override
    public CelestialGatekeeper copy() {
        return new CelestialGatekeeper(this);
    }
}
