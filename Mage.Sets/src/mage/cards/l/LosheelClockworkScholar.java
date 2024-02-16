package mage.cards.l;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.PreventAllDamageToAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterArtifactCreaturePermanent;
import mage.filter.predicate.permanent.AttackingPredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class LosheelClockworkScholar extends CardImpl {

    private static final FilterPermanent filter
            = new FilterArtifactCreaturePermanent("attacking artifact creatures you control");
    private static final FilterPermanent filter2
            = new FilterArtifactCreaturePermanent("one or more artifact creatures");

    static {
        filter.add(TargetController.YOU.getControllerPredicate());
        filter.add(AttackingPredicate.instance);
        filter2.add(TargetController.YOU.getControllerPredicate());
    }

    public LosheelClockworkScholar(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ELEPHANT);
        this.subtype.add(SubType.ARTIFICER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // Prevent all combat damage that would be dealt to attacking artifact creatures you control.
        this.addAbility(new SimpleStaticAbility(new PreventAllDamageToAllEffect(
                Duration.WhileOnBattlefield, filter, true
        )));

        // Whenever one or more artifact creatures enter the battlefield under your control, draw a card. This ability triggers only once each turn.
        this.addAbility(new EntersBattlefieldControlledTriggeredAbility(
                new DrawCardSourceControllerEffect(1), filter2
        ).setTriggersOnceEachTurn(true));
    }

    private LosheelClockworkScholar(final LosheelClockworkScholar card) {
        super(card);
    }

    @Override
    public LosheelClockworkScholar copy() {
        return new LosheelClockworkScholar(this);
    }
}
