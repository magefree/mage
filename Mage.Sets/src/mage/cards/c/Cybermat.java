package mage.cards.c;

import mage.MageInt;
import mage.abilities.common.AttacksAndIsNotBlockedTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.abilities.keyword.SkulkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterArtifactCreaturePermanent;
import mage.filter.predicate.permanent.AttackingPredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Cybermat extends CardImpl {

    private static final FilterPermanent filter = new FilterArtifactCreaturePermanent("attacking artifact creatures");

    static {
        filter.add(AttackingPredicate.instance);
    }

    private static final DynamicValue xValue = new PermanentsOnBattlefieldCount(filter, null);
    private static final Hint hint = new ValueHint("Attacking artifact creatures", xValue);

    public Cybermat(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{2}");

        this.subtype.add(SubType.ROBOT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Skulk
        this.addAbility(new SkulkAbility());

        // Whenever Cybermat attacks and isn't blocked, it gets +X/+0 until end of turn, where X is the number of attacking artifact creatures.
        this.addAbility(new AttacksAndIsNotBlockedTriggeredAbility(
                new BoostSourceEffect(xValue, StaticValue.get(0), Duration.EndOfTurn, "it")
        ).addHint(hint));
    }

    private Cybermat(final Cybermat card) {
        super(card);
    }

    @Override
    public Cybermat copy() {
        return new Cybermat(this);
    }
}
