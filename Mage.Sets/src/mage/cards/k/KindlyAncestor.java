package mage.cards.k;

import mage.MageInt;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.keyword.DisturbAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class KindlyAncestor extends CardImpl {

    public KindlyAncestor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");

        this.subtype.add(SubType.SPIRIT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);
        this.secondSideCardClazz = mage.cards.a.AncestorsEmbrace.class;

        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());

        // Disturb {1}{W}
        this.addAbility(new DisturbAbility(new ManaCostsImpl<>("{1}{W}")));
    }

    private KindlyAncestor(final KindlyAncestor card) {
        super(card);
    }

    @Override
    public KindlyAncestor copy() {
        return new KindlyAncestor(this);
    }
}
