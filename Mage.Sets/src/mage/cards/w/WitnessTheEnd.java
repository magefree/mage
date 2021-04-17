package mage.cards.w;

import mage.abilities.effects.common.ExileFromZoneTargetEffect;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.abilities.keyword.DevoidAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class WitnessTheEnd extends CardImpl {

    public WitnessTheEnd(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{B}");

        // Devoid
        this.addAbility(new DevoidAbility(this.color));

        // Target opponent exiles two cards from their hand and loses 2 life.
        this.getSpellAbility().addEffect(new ExileFromZoneTargetEffect(
                Zone.HAND, StaticFilters.FILTER_CARD_CARDS, 2, false
        ));
        this.getSpellAbility().addEffect(new LoseLifeTargetEffect(2).setText("and loses 2 life"));
        this.getSpellAbility().addTarget(new TargetOpponent());
    }

    private WitnessTheEnd(final WitnessTheEnd card) {
        super(card);
    }

    @Override
    public WitnessTheEnd copy() {
        return new WitnessTheEnd(this);
    }
}
