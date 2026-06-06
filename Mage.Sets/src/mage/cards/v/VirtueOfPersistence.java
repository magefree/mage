package mage.cards.v;

import mage.abilities.Ability;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.triggers.BeginningOfUpkeepTriggeredAbility;
import mage.cards.AdventureCard;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInGraveyard;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class VirtueOfPersistence extends AdventureCard {

    public VirtueOfPersistence(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.ENCHANTMENT}, "{5}{B}{B}",
                "Locthwain Scorn",
                new CardType[]{CardType.SORCERY}, "{1}{B}");

        // Virtue of Persistence
        // At the beginning of your upkeep, put target creature card from a graveyard onto the battlefield under your control.
        Ability ability = new BeginningOfUpkeepTriggeredAbility(
                new ReturnFromGraveyardToBattlefieldTargetEffect()
        );
        ability.addTarget(new TargetCardInGraveyard(StaticFilters.FILTER_CARD_CREATURE_A_GRAVEYARD));
        this.getLeftHalfCard().addAbility(ability);

        // Locthwain Scorn
        // Target creature gets -3/-3 until end of turn. You gain 2 life.
        this.getRightHalfCard().getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getRightHalfCard().getSpellAbility().addEffect(new BoostTargetEffect(-3, -3, Duration.EndOfTurn));
        this.getRightHalfCard().getSpellAbility().addEffect(new GainLifeEffect(2));

        finalizeCard();
    }

    private VirtueOfPersistence(final VirtueOfPersistence card) {
        super(card);
    }

    @Override
    public VirtueOfPersistence copy() {
        return new VirtueOfPersistence(this);
    }
}
