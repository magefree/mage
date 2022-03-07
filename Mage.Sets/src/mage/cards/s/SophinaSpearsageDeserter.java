package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.keyword.InvestigateEffect;
import mage.abilities.keyword.FriendsForeverAbility;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterAttackingCreature;
import mage.filter.predicate.permanent.TokenPredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SophinaSpearsageDeserter extends CardImpl {

    private static final FilterPermanent filter = new FilterAttackingCreature();

    static {
        filter.add(TokenPredicate.FALSE);
    }

    private static final DynamicValue xValue = new PermanentsOnBattlefieldCount(filter);

    public SophinaSpearsageDeserter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}{W}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Menace
        this.addAbility(new MenaceAbility(false));

        // Whenever Chief Jim Hopper attacks, investigate once for each nontoken attacking creature.
        this.addAbility(new AttacksTriggeredAbility(new InvestigateEffect(xValue)
                .setText("investigate once for each nontoken attacking creature")));

        // Friends forever
        this.addAbility(FriendsForeverAbility.getInstance());
    }

    private SophinaSpearsageDeserter(final SophinaSpearsageDeserter card) {
        super(card);
    }

    @Override
    public SophinaSpearsageDeserter copy() {
        return new SophinaSpearsageDeserter(this);
    }
}
