
package mage.cards.o;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleEvasionAbility;
import mage.abilities.effects.common.combat.CantBeBlockedByCreaturesSourceEffect;
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
public final class OrchardSpirit extends CardImpl {

    private static final FilterCreaturePermanent notFlyingorReachCreatures = new FilterCreaturePermanent("except by creatures with flying or reach");

    static {
        notFlyingorReachCreatures.add(Predicates.not(
                Predicates.or(
                        new AbilityPredicate(FlyingAbility.class),
                        new AbilityPredicate(ReachAbility.class)
                )
        ));
    }

    public OrchardSpirit(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{G}");
        this.subtype.add(SubType.SPIRIT);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Orchard Spirit can't be blocked except by creatures with flying or reach.
        this.addAbility(new SimpleEvasionAbility(new CantBeBlockedByCreaturesSourceEffect(notFlyingorReachCreatures, Duration.WhileOnBattlefield)));

    }

    private OrchardSpirit(final OrchardSpirit card) {
        super(card);
    }

    @Override
    public OrchardSpirit copy() {
        return new OrchardSpirit(this);
    }
}
