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
import mage.target.common.TargetCreatureOrPlaneswalkerAmount;

/**
 *
 * @author fireshoes
 */
public final class DragonlordAtarka extends CardImpl {

    private static final FilterCreatureOrPlaneswalkerPermanent filter = new FilterCreatureOrPlaneswalkerPermanent("creatures and/or planeswalkers your opponents control");

    static {
        filter.add(TargetController.OPPONENT.getControllerPredicate());
    }

    public DragonlordAtarka(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{R}{G}");
        this.supertype.add(SuperType.LEGENDARY);
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
        TargetCreatureOrPlaneswalkerAmount target = new TargetCreatureOrPlaneswalkerAmount(5, filter);
        target.setMinNumberOfTargets(1);
        target.setMaxNumberOfTargets(5);
        ability.addTarget(target);
        this.addAbility(ability);
    }

    private DragonlordAtarka(final DragonlordAtarka card) {
        super(card);
    }

    @Override
    public DragonlordAtarka copy() {
        return new DragonlordAtarka(this);
    }
}
