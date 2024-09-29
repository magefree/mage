package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.common.CardsLeaveGraveyardTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.LoseLifeOpponentsEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.keyword.FlashAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SoulEnervation extends CardImpl {

    public SoulEnervation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{B}");

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // When Soul Enervation enters the battlefield, target creature gets -4/-4 until end of turn.
        Ability ability = new EntersBattlefieldTriggeredAbility(new BoostTargetEffect(-4, -4));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);

        // Whenever one or more creature cards leave your graveyard, each opponent loses 1 life and you gain 1 life.
        ability = new CardsLeaveGraveyardTriggeredAbility(
                new LoseLifeOpponentsEffect(1), StaticFilters.FILTER_CARD_CREATURES
        );
        ability.addEffect(new GainLifeEffect(1).concatBy("and"));
        this.addAbility(ability);
    }

    private SoulEnervation(final SoulEnervation card) {
        super(card);
    }

    @Override
    public SoulEnervation copy() {
        return new SoulEnervation(this);
    }
}
