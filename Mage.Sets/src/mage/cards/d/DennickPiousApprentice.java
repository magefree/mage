package mage.cards.d;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CantBeTargetedCardsGraveyardsEffect;
import mage.abilities.keyword.DisturbAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.abilities.keyword.TransformAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;

import java.util.UUID;

/**
 * @author LePwnerer
 */
public final class DennickPiousApprentice extends CardImpl {

    public DennickPiousApprentice(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{W}{U}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);
        this.secondSideCardClazz = mage.cards.d.DennickPiousApparition.class;

        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());

        // Cards in graveyards can't be the targets of spells or abilities.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new CantBeTargetedCardsGraveyardsEffect()));

        // Disturb {2}{W}{U}
        this.addAbility(new TransformAbility());
        this.addAbility(new DisturbAbility(new ManaCostsImpl<>("{2}{W}{U}")));

    }

    private DennickPiousApprentice(final DennickPiousApprentice card) {
        super(card);
    }

    @Override
    public DennickPiousApprentice copy() {
        return new DennickPiousApprentice(this);
    }
}
