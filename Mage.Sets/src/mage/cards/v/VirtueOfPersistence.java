package mage.cards.v;

import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.AdventureCard;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.TargetController;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreatureCard;
import mage.target.common.TargetCardInGraveyard;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class VirtueOfPersistence extends AdventureCard {

    private static final FilterCard filter = new FilterCreatureCard("creature card from a graveyard");

    public VirtueOfPersistence(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, new CardType[]{CardType.SORCERY}, "{5}{B}{B}", "Locthwain Scorn", "{1}{B}");

        // At the beginning of your upkeep, put target creature card from a graveyard onto the battlefield under your control.
        Ability ability = new BeginningOfUpkeepTriggeredAbility(
                new ReturnFromGraveyardToBattlefieldTargetEffect(), TargetController.YOU, false
        );
        ability.addTarget(new TargetCardInGraveyard(filter));
        this.addAbility(ability);

        // Locthwain Scorn
        // Target creature gets -3/-3 until end of turn. You gain 2 life.
        this.getSpellCard().getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellCard().getSpellAbility().addEffect(new BoostTargetEffect(-3, -3, Duration.EndOfTurn));
        this.getSpellCard().getSpellAbility().addEffect(new GainLifeEffect(2));

        this.finalizeAdventure();
    }

    private VirtueOfPersistence(final VirtueOfPersistence card) {
        super(card);
    }

    @Override
    public VirtueOfPersistence copy() {
        return new VirtueOfPersistence(this);
    }
}
