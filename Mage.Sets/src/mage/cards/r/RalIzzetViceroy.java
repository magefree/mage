package mage.cards.r;

import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.dynamicvalue.common.InstantSorceryExileGraveyardCount;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.GetEmblemEffect;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.PutCards;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.game.command.emblems.RalIzzetViceroyEmblem;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RalIzzetViceroy extends CardImpl {

    private static final Hint hint = new ValueHint(
            "Instant and sorcery cards in your exile and graveyard", InstantSorceryExileGraveyardCount.instance
    );

    public RalIzzetViceroy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{3}{U}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.RAL);
        this.setStartingLoyalty(5);

        // +1: Look at the top two cards of your library. Put one of them into your hand and the other into your graveyard.
        this.addAbility(new LoyaltyAbility(new LookLibraryAndPickControllerEffect(2, 1, PutCards.HAND, PutCards.GRAVEYARD), 1));

        // -3: Ral, Izzet Viceroy deals damage to target creature equal to the total number of instant and sorcery cards you own in exile and in your graveyard.
        Ability ability = new LoyaltyAbility(new DamageTargetEffect(
                InstantSorceryExileGraveyardCount.instance
        ).setText("{this} deals damage to target creature equal to "
                + "the total number of instant and sorcery cards "
                + "you own in exile and in your graveyard"), -3);
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability.addHint(hint));

        // -8: You get an emblem with "Whenever you cast an instant or sorcery spell, this emblem deals 4 damage to any target and you draw two cards."
        this.addAbility(new LoyaltyAbility(
                new GetEmblemEffect(new RalIzzetViceroyEmblem()), -8
        ));
    }

    private RalIzzetViceroy(final RalIzzetViceroy card) {
        super(card);
    }

    @Override
    public RalIzzetViceroy copy() {
        return new RalIzzetViceroy(this);
    }
}
