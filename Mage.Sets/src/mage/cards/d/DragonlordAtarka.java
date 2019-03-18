
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.DamageMultiEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.filter.common.FilterCreatureOrPlaneswalkerPermanent;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.target.common.TargetCreatureOrPlaneswalkerAmount;

/**
 *
 * @author fireshoes
 */
public final class DragonlordAtarka extends CardImpl {

    private static final FilterCreatureOrPlaneswalkerPermanent filter = new FilterCreatureOrPlaneswalkerPermanent("target creatures and/or planeswalkers your opponents control");

    static {
        filter.add(new ControllerPredicate(TargetController.OPPONENT));
    }

    public DragonlordAtarka(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{5}{R}{G}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.ELDER);
        this.subtype.add(SubType.DRAGON);
        this.power = new MageInt(8);
        this.toughness = new MageInt(8);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // When Dragonlord Atarka enters the battlefield, it deals 5 damage divided as you choose among any number of target creatures and/or planeswalkers your opponents control.
        Ability ability = new EntersBattlefieldTriggeredAbility(new DamageMultiEffect(5, "it"), false);
        ability.addTarget(new TargetCreatureOrPlaneswalkerAmount(5, filter));
        this.addAbility(ability);
    }

    public DragonlordAtarka(final DragonlordAtarka card) {
        super(card);
    }

    @Override
    public DragonlordAtarka copy() {
        return new DragonlordAtarka(this);
    }
}
