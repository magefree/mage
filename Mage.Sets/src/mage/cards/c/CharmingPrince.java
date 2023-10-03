package mage.cards.c;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.ExileReturnBattlefieldNextEndStepTargetEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CharmingPrince extends CardImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent("another target creature you own");

    static {
        filter.add(TargetController.YOU.getOwnerPredicate());
        filter.add(AnotherPredicate.instance);
    }

    public CharmingPrince(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.NOBLE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When Charming Prince enters the battlefield, choose one —
        // • Scry 2.
        Ability ability = new EntersBattlefieldTriggeredAbility(new ScryEffect(2));

        // • You gain 3 life.
        ability.addMode(new Mode(new GainLifeEffect(3)));

        // • Exile another target creature you own. Return it to the battlefield under your control at the beginning of the next end step.
        Mode mode = new Mode(new ExileReturnBattlefieldNextEndStepTargetEffect().underYourControl(true).withTextThatCard(false));
        mode.addTarget(new TargetPermanent(filter));
        ability.addMode(mode);
        this.addAbility(ability);
    }

    private CharmingPrince(final CharmingPrince card) {
        super(card);
    }

    @Override
    public CharmingPrince copy() {
        return new CharmingPrince(this);
    }
}
