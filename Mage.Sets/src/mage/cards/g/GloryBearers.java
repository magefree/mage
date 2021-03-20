package mage.cards.g;

import mage.MageInt;
import mage.abilities.common.AttacksCreatureYouControlTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GloryBearers extends CardImpl {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("another creature you control");

    static {
        filter.add(AnotherPredicate.instance);
    }

    public GloryBearers(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT, CardType.CREATURE}, "{3}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Whenever another creature you control attacks, it gets +0/+1 until end of turn.
        this.addAbility(new AttacksCreatureYouControlTriggeredAbility(
                new BoostTargetEffect(0, 1)
                        .setText("it gets +0/+1 until end of turn"),
                false, filter, true
        ));
    }

    private GloryBearers(final GloryBearers card) {
        super(card);
    }

    @Override
    public GloryBearers copy() {
        return new GloryBearers(this);
    }
}
