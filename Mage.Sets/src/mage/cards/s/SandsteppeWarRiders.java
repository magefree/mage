package mage.cards.s;

import mage.MageInt;
import mage.abilities.dynamicvalue.common.DifferentlyNamedPermanentCount;
import mage.abilities.effects.keyword.BolsterEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.abilities.triggers.BeginningOfCombatTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledArtifactPermanent;
import mage.filter.predicate.permanent.TokenPredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SandsteppeWarRiders extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledArtifactPermanent("artifact tokens you control");

    static {
        filter.add(TokenPredicate.TRUE);
    }

    private static final DifferentlyNamedPermanentCount xValue = new DifferentlyNamedPermanentCount(filter);

    public SandsteppeWarRiders(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // At the beginning of combat on your turn, bolster X, where X is the number of differently named artifact tokens you control.
        this.addAbility(new BeginningOfCombatTriggeredAbility(new BolsterEffect(xValue)).addHint(xValue.getHint()));
    }

    private SandsteppeWarRiders(final SandsteppeWarRiders card) {
        super(card);
    }

    @Override
    public SandsteppeWarRiders copy() {
        return new SandsteppeWarRiders(this);
    }
}
