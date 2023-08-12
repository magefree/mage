
package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldOrAttacksSourceTriggeredAbility;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.CrewAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.filter.common.FilterCreatureOrPlaneswalkerPermanent;
import mage.target.common.TargetCreatureOrPlaneswalker;

import java.util.UUID;

/**
 * @author emerald000
 */
public final class SkysovereignConsulFlagship extends CardImpl {

    private static final FilterCreatureOrPlaneswalkerPermanent filter = new FilterCreatureOrPlaneswalkerPermanent("creature or planeswalker an opponent controls");

    static {
        filter.add(TargetController.OPPONENT.getControllerPredicate());
    }

    public SkysovereignConsulFlagship(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{5}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.VEHICLE);
        this.power = new MageInt(6);
        this.toughness = new MageInt(5);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever Skysovereign, Consul Flagship enters the battlefield or attacks, it deals 3 damage to target creature or planeswalker an opponent controls.
        Ability ability = new EntersBattlefieldOrAttacksSourceTriggeredAbility(new DamageTargetEffect(3, "it"));
        ability.addTarget(new TargetCreatureOrPlaneswalker(1, 1, filter, false));
        this.addAbility(ability);

        // Crew 3
        this.addAbility(new CrewAbility(3));
    }

    private SkysovereignConsulFlagship(final SkysovereignConsulFlagship card) {
        super(card);
    }

    @Override
    public SkysovereignConsulFlagship copy() {
        return new SkysovereignConsulFlagship(this);
    }
}
