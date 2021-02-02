
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleEvasionAbility;
import mage.abilities.effects.common.combat.CantBeBlockedByCreaturesSourceEffect;
import mage.abilities.keyword.BattleCryAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AbilityPredicate;

/**
 *
 * @author North
 */
public final class SignalPest extends CardImpl {

    private static final FilterCreaturePermanent notFlyingorReachCreatures = new FilterCreaturePermanent("except by creatures with flying or reach");

    static {
        notFlyingorReachCreatures.add(Predicates.not(
                Predicates.or(
                        new AbilityPredicate(FlyingAbility.class),
                        new AbilityPredicate(ReachAbility.class)
                )
        ));
    }

    public SignalPest(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{1}");
        this.subtype.add(SubType.PEST);

        this.power = new MageInt(0);
        this.toughness = new MageInt(1);

        // Battle cry (Whenever this creature attacks, each other attacking creature gets +1/+0 until end of turn.)
        this.addAbility(new BattleCryAbility());

        // Signal Pest can't be blocked except by creatures with flying or reach.
        this.addAbility(new SimpleEvasionAbility(new CantBeBlockedByCreaturesSourceEffect(notFlyingorReachCreatures, Duration.WhileOnBattlefield)));
    }

    private SignalPest(final SignalPest card) {
        super(card);
    }

    @Override
    public SignalPest copy() {
        return new SignalPest(this);
    }
}
