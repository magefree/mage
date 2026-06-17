package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.target.common.TargetNonlandPermanent;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.effects.common.UntapTargetEffect;
import mage.abilities.keyword.FlashAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class GiantSizedFlyingAnt extends CardImpl {

    public GiantSizedFlyingAnt(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}");

        this.subtype.add(SubType.INSECT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When this creature enters, choose one --
        // * Tap target nonland permanent.
        Ability ability = new EntersBattlefieldTriggeredAbility(new TapTargetEffect());
        ability.addTarget(new TargetNonlandPermanent());

        // * Untap target nonland permanent.
        ability.addMode(new Mode(new UntapTargetEffect()).addTarget(new TargetNonlandPermanent()));
        this.addAbility(ability);
    }

    private GiantSizedFlyingAnt(final GiantSizedFlyingAnt card) {
        super(card);
    }

    @Override
    public GiantSizedFlyingAnt copy() {
        return new GiantSizedFlyingAnt(this);
    }
}
