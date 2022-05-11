package mage.cards.f;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SacrificeAllTriggeredAbility;
import mage.abilities.effects.common.DamagePlayersEffect;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;

/**
 *
 * @author weirddan455
 */
public final class ForgeBoss extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("one or more other creatures");

    static {
        filter.add(AnotherPredicate.instance);
    }

    public ForgeBoss(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}{R}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Whenever you sacrifice one or more other creatures, Forge Boss deals 2 damage to each opponent. This ability triggers only once each turn.
        this.addAbility(new SacrificeAllTriggeredAbility(
                new DamagePlayersEffect(2, TargetController.OPPONENT),
                filter, TargetController.YOU, false
        ).setTriggersOnce(true));
    }

    private ForgeBoss(final ForgeBoss card) {
        super(card);
    }

    @Override
    public ForgeBoss copy() {
        return new ForgeBoss(this);
    }
}
