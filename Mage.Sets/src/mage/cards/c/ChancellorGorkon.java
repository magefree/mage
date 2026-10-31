package mage.cards.c;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author muz
 */
public final class ChancellorGorkon extends CardImpl {

    public ChancellorGorkon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.KLINGON);
        this.subtype.add(SubType.NOBLE);
        this.power = new MageInt(5);
        this.toughness = new MageInt(3);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // When Chancellor Gorkon dies, exile up to two target creatures.
        Ability ability = new DiesSourceTriggeredAbility(new ExileTargetEffect());
        ability.addTarget(new TargetCreaturePermanent(0, 2));
        this.addAbility(ability);
    }

    private ChancellorGorkon(final ChancellorGorkon card) {
        super(card);
    }

    @Override
    public ChancellorGorkon copy() {
        return new ChancellorGorkon(this);
    }
}
