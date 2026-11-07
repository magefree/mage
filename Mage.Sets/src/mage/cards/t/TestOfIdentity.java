package mage.cards.t;

import mage.abilities.Mode;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author muz
 */
public final class TestOfIdentity extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent(SubType.KLINGON);

    public TestOfIdentity(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{R}");

        // Choose one --
        // * I Am Klingon -- Target Klingon gets +1/+1 and gains double strike until end of turn.
        this.getSpellAbility().addEffect(new BoostTargetEffect(1, 1).setText("target Klingon gets +1/+1"));
        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(DoubleStrikeAbility.getInstance())
            .setText("and gains double strike until end of turn"));
        this.getSpellAbility().addTarget(new TargetPermanent(filter));
        this.getSpellAbility().withFirstModeFlavorWord("I Am Klingon");

        // * I Am Starfleet -- Discard a card. If you do, draw two cards.
        Mode mode = new Mode(new DoIfCostPaid(
            new DrawCardSourceControllerEffect(2), null, new DiscardCardCost(), false
        ));
        mode.withFlavorWord("I Am Starfleet");
        this.getSpellAbility().addMode(mode);
    }

    private TestOfIdentity(final TestOfIdentity card) {
        super(card);
    }

    @Override
    public TestOfIdentity copy() {
        return new TestOfIdentity(this);
    }
}
