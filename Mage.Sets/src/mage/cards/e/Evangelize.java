package mage.cards.e;

import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.abilities.keyword.BuybackAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.StaticFilters;
import mage.target.common.TargetOpponentsChoicePermanent;

import java.util.UUID;

/**
 * @author spjspj
 */
public final class Evangelize extends CardImpl {

    public Evangelize(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{W}");

        // Buyback {2}{W}{W}
        this.addAbility(new BuybackAbility("{2}{W}{W}"));

        // Gain control of target creature of an opponent's choice that they control.
        GainControlTargetEffect effect = new GainControlTargetEffect(Duration.EndOfGame);
        effect.setText("Gain control of target creature of an opponent's choice they control");
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addTarget(new TargetOpponentsChoicePermanent(1, 1, StaticFilters.FILTER_CONTROLLED_CREATURE, false));
    }

    private Evangelize(final Evangelize card) {
        super(card);
    }

    @Override
    public Evangelize copy() {
        return new Evangelize(this);
    }
}
