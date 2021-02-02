package mage.cards.r;

import mage.MageInt;
import mage.abilities.common.SimpleEvasionAbility;
import mage.abilities.effects.common.combat.CantBeBlockedByCreaturesSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.common.FilterCreaturePermanent;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class RiverDarter extends CardImpl {

    public RiverDarter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");

        this.subtype.add(SubType.MERFOLK);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // River Darter can't be blocked by Dinosaurs.
        this.addAbility(new SimpleEvasionAbility(new CantBeBlockedByCreaturesSourceEffect(new FilterCreaturePermanent(SubType.DINOSAUR, "Dinosaurs"), Duration.WhileOnBattlefield)));
    }

    private RiverDarter(final RiverDarter card) {
        super(card);
    }

    @Override
    public RiverDarter copy() {
        return new RiverDarter(this);
    }
}
