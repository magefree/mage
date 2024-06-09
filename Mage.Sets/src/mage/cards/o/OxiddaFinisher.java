package mage.cards.o;

import java.util.UUID;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.AffinityEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.constants.SubType;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;

/**
 * @author TheElk801
 */
public final class OxiddaFinisher extends CardImpl {

    private static final FilterControlledPermanent filter
            = new FilterControlledPermanent(SubType.EQUIPMENT, "Equipment");
    private static final Hint hint = new ValueHint(
            "Equipment you control", new PermanentsOnBattlefieldCount(filter)
    );

    public OxiddaFinisher(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{R}{R}");

        this.subtype.add(SubType.OGRE);
        this.subtype.add(SubType.REBEL);
        this.power = new MageInt(7);
        this.toughness = new MageInt(5);

        // Affinity for Equipment
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new AffinityEffect(filter)).addHint(hint));

        // Trample
        this.addAbility(TrampleAbility.getInstance());
    }

    private OxiddaFinisher(final OxiddaFinisher card) {
        super(card);
    }

    @Override
    public OxiddaFinisher copy() {
        return new OxiddaFinisher(this);
    }
}
