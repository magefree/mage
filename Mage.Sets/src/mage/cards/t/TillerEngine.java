package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.effects.common.UntapTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledLandPermanent;
import mage.filter.common.FilterNonlandPermanent;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author PurpleCrowbar
 */
public final class TillerEngine extends CardImpl {

    private static final FilterControlledLandPermanent filter = new FilterControlledLandPermanent("a land");
    private static final FilterPermanent filter2 = new FilterNonlandPermanent("nonland permanent an opponent controls");

    static {
        filter.add(TappedPredicate.TAPPED);
        filter2.add(TargetController.OPPONENT.getControllerPredicate());
    }

    public TillerEngine(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{2}");
        this.subtype.add(SubType.CONSTRUCT);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Whenever a land enters the battlefield tapped and under your control, choose one —
        // • Untap that land.
        Ability ability = new EntersBattlefieldControlledTriggeredAbility(
                Zone.BATTLEFIELD, new UntapTargetEffect().setText("Untap that land"),
                filter, false, SetTargetPointer.PERMANENT, null
        ).setTriggerPhrase("Whenever a land enters the battlefield tapped and under your control, ");

        // • Tap target nonland permanent an opponent controls.
        Mode mode = new Mode(new TapTargetEffect());
        mode.addTarget(new TargetPermanent(filter2));
        ability.addMode(mode);
        this.addAbility(ability);
    }

    private TillerEngine(final TillerEngine card) {
        super(card);
    }

    @Override
    public TillerEngine copy() {
        return new TillerEngine(this);
    }
}
