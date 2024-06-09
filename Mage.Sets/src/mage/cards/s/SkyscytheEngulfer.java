package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.combat.CantBeBlockedByCreaturesSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.ReachAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AbilityPredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SkyscytheEngulfer extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creatures with flying");

    static {
        filter.add(new AbilityPredicate(FlyingAbility.class));
    }

    public SkyscytheEngulfer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{G}");

        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(6);
        this.toughness = new MageInt(5);

        // Reach
        this.addAbility(ReachAbility.getInstance());

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Skyscythe Engulfer can't be blocked by creatures with flying.
        this.addAbility(new SimpleStaticAbility(
                new CantBeBlockedByCreaturesSourceEffect(filter, Duration.WhileOnBattlefield)
        ));
    }

    private SkyscytheEngulfer(final SkyscytheEngulfer card) {
        super(card);
    }

    @Override
    public SkyscytheEngulfer copy() {
        return new SkyscytheEngulfer(this);
    }
}
