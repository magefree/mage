package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksAllTriggeredAbility;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.IndestructibleAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.SetTargetPointer;
import mage.constants.TargetController;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;

/**
 *
 * @author spjspj
 */
public final class StonehoofChieftain extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("another creature you control");

    static {
        filter.add(TargetController.YOU.getControllerPredicate());
        filter.add(AnotherPredicate.instance);
    }

    public StonehoofChieftain(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{7}{G}");

        this.subtype.add(SubType.CENTAUR);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(8);
        this.toughness = new MageInt(8);

        // Trample
        this.addAbility(TrampleAbility.getInstance());
        // Indestructible
        this.addAbility(IndestructibleAbility.getInstance());

        // Whenever another creature you control attacks, it gains trample and indestructible until end of turn.
        GainAbilityTargetEffect effect = new GainAbilityTargetEffect(TrampleAbility.getInstance(), Duration.EndOfTurn);
        effect.setText("it gains trample");
        Ability ability = new AttacksAllTriggeredAbility(effect, false, filter, SetTargetPointer.PERMANENT, false);
        GainAbilityTargetEffect effect2 = new GainAbilityTargetEffect(IndestructibleAbility.getInstance(), Duration.EndOfTurn);
        effect2.setText("and indestructible until end of turn");
        ability.addEffect(effect2);
        this.addAbility(ability);
    }

    private StonehoofChieftain(final StonehoofChieftain card) {
        super(card);
    }

    @Override
    public StonehoofChieftain copy() {
        return new StonehoofChieftain(this);
    }
}
