package mage.cards.k;

import mage.MageInt;
import mage.abilities.abilityword.SurvivalAbility;
import mage.abilities.effects.common.PutCardFromHandOntoBattlefieldEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class KonaRescueBeastie extends CardImpl {

    public KonaRescueBeastie(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.BEAST);
        this.subtype.add(SubType.SURVIVOR);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // Survival - At the beginning of your second main phase, if Kona, Rescue Beastie is tapped, you may put a permanent card from your hand onto the battlefield
        this.addAbility(new SurvivalAbility(new PutCardFromHandOntoBattlefieldEffect(StaticFilters.FILTER_CARD_CREATURE_A)));
    }

    private KonaRescueBeastie(final KonaRescueBeastie card) {
        super(card);
    }

    @Override
    public KonaRescueBeastie copy() {
        return new KonaRescueBeastie(this);
    }
}
