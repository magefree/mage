package mage.cards.a;

import mage.MageInt;
import mage.abilities.common.CantBeCounteredSourceAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.GainAbilityControllerEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.ProtectionFromEachOpponentAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AbsoluteVirtue extends CardImpl {

    public AbsoluteVirtue(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{6}{W}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.AVATAR);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(8);
        this.toughness = new MageInt(8);

        // This spell can't be countered.
        this.addAbility(new CantBeCounteredSourceAbility());

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // You have protection from each of your opponents.
        this.addAbility(new SimpleStaticAbility(new GainAbilityControllerEffect(new ProtectionFromEachOpponentAbility())));
    }

    private AbsoluteVirtue(final AbsoluteVirtue card) {
        super(card);
    }

    @Override
    public AbsoluteVirtue copy() {
        return new AbsoluteVirtue(this);
    }
}
