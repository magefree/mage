package mage.cards.j;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.AffinityEffect;
import mage.abilities.effects.common.DontUntapInControllersNextUntapStepTargetEffect;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.common.FilterNonlandPermanent;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class JunkWinder extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("tokens");
    private static final FilterPermanent filter2 = new FilterNonlandPermanent("nonland permanent an opponent controls");

    static {
        filter.add(TokenPredicate.TRUE);
        filter2.add(TargetController.OPPONENT.getControllerPredicate());
    }

    private static final Hint hint = new ValueHint("Tokens you control", new PermanentsOnBattlefieldCount(filter));

    public JunkWinder(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{U}{U}");

        this.subtype.add(SubType.SERPENT);
        this.power = new MageInt(5);
        this.toughness = new MageInt(6);

        // Affinity for tokens
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new AffinityEffect(filter)).addHint(hint));

        // Whenever a token enters the battlefield under your control, tap target nonland permanent an opponent controls. It doesn't untap during its controller's next untap step.
        Ability ability = new EntersBattlefieldControlledTriggeredAbility(
                new TapTargetEffect(), StaticFilters.FILTER_PERMANENT_TOKEN
        );
        ability.addEffect(new DontUntapInControllersNextUntapStepTargetEffect("It"));
        ability.addTarget(new TargetPermanent(StaticFilters.FILTER_OPPONENTS_PERMANENT_NON_LAND));
        this.addAbility(ability);
    }

    private JunkWinder(final JunkWinder card) {
        super(card);
    }

    @Override
    public JunkWinder copy() {
        return new JunkWinder(this);
    }
}
