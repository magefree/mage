package mage.cards.b;

import mage.MageInt;
import mage.abilities.common.LandfallAbility;
import mage.abilities.common.SimpleEvasionAbility;
import mage.abilities.effects.common.combat.CantBeBlockedByCreaturesSourceEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.PowerPredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BrushfireElemental extends CardImpl {

    private static final FilterCreaturePermanent filter
            = new FilterCreaturePermanent("creatures with power 2 or less");

    static {
        filter.add(new PowerPredicate(ComparisonType.FEWER_THAN, 3));
    }

    public BrushfireElemental(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{R}{G}");

        this.subtype.add(SubType.ELEMENTAL);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // Brushfire Elemental can't be blocked by creatures with power 2 or less.
        this.addAbility(new SimpleEvasionAbility(
                new CantBeBlockedByCreaturesSourceEffect(filter, Duration.WhileOnBattlefield)
        ));

        // Landfall — Whenever a land enters the battlefield under your control, Brushfire Elemental gets +2/+2 until end of turn.
        this.addAbility(new LandfallAbility(new BoostSourceEffect(2, 2, Duration.EndOfTurn)));
    }

    private BrushfireElemental(final BrushfireElemental card) {
        super(card);
    }

    @Override
    public BrushfireElemental copy() {
        return new BrushfireElemental(this);
    }
}
