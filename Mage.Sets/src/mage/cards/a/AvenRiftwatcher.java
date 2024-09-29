package mage.cards.a;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldOrLeavesSourceTriggeredAbility;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.VanishingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author dustinconrad
 */
public final class AvenRiftwatcher extends CardImpl {

    public AvenRiftwatcher(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");
        this.subtype.add(SubType.BIRD);
        this.subtype.add(SubType.REBEL);
        this.subtype.add(SubType.SOLDIER);

        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Vanishing 3
        this.addAbility(new VanishingAbility(3));

        // When Aven Riftwatcher enters the battlefield or leaves the battlefield, you gain 2 life.
        this.addAbility(new EntersBattlefieldOrLeavesSourceTriggeredAbility(new GainLifeEffect(2), false));
    }

    private AvenRiftwatcher(final AvenRiftwatcher card) {
        super(card);
    }

    @Override
    public AvenRiftwatcher copy() {
        return new AvenRiftwatcher(this);
    }
}
