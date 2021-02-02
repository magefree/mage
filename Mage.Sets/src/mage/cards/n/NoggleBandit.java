
package mage.cards.n;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleEvasionAbility;
import mage.abilities.effects.common.combat.CantBeBlockedByCreaturesSourceEffect;
import mage.abilities.keyword.DefenderAbility;
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
 * @author jeffwadsworth
 *
 */
public final class NoggleBandit extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("except by creatures with defender");

    static {
        filter.add(Predicates.not(new AbilityPredicate(DefenderAbility.class)));
    }

    public NoggleBandit(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{U/R}{U/R}");
        this.subtype.add(SubType.NOGGLE);
        this.subtype.add(SubType.ROGUE);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Noggle Bandit can't be blocked except by creatures with defender.
        this.addAbility(new SimpleEvasionAbility(new CantBeBlockedByCreaturesSourceEffect(filter, Duration.WhileOnBattlefield)));

    }

    private NoggleBandit(final NoggleBandit card) {
        super(card);
    }

    @Override
    public NoggleBandit copy() {
        return new NoggleBandit(this);
    }
}
