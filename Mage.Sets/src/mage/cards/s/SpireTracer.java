package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.SimpleEvasionAbility;
import mage.abilities.effects.common.combat.CantBeBlockedByCreaturesSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AbilityPredicate;

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class SpireTracer extends CardImpl {

    private static final FilterCreaturePermanent notFlyingorReachCreatures = new FilterCreaturePermanent("except by creatures with flying or reach");

    static {
        notFlyingorReachCreatures.add(Predicates.not(
                Predicates.or(
                        new AbilityPredicate(FlyingAbility.class),
                        new AbilityPredicate(ReachAbility.class)
                )
        ));
    }

    public SpireTracer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{G}");
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.SCOUT);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Spire Tracer can't be blocked except by creatures with flying or reach.
        this.addAbility(new SimpleEvasionAbility(new CantBeBlockedByCreaturesSourceEffect(notFlyingorReachCreatures, Duration.WhileOnBattlefield)));

    }

    private SpireTracer(final SpireTracer card) {
        super(card);
    }

    @Override
    public SpireTracer copy() {
        return new SpireTracer(this);
    }
}
