package mage.cards.m;

import mage.MageInt;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.keyword.DisturbAbility;
import mage.abilities.keyword.TransformAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MourningPatrol extends CardImpl {

    public MourningPatrol(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);
        this.transformable = true;
        this.secondSideCardClazz = mage.cards.m.MorningApparition.class;

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Disturb {3}{W}
        this.addAbility(new TransformAbility());
        this.addAbility(new DisturbAbility(new ManaCostsImpl<>("{3}{W}")));
    }

    private MourningPatrol(final MourningPatrol card) {
        super(card);
    }

    @Override
    public MourningPatrol copy() {
        return new MourningPatrol(this);
    }
}
