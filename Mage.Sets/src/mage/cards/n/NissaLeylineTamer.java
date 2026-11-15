package mage.cards.n;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.LandfallAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.IfAbilityHasResolvedXTimesEffect;
import mage.abilities.effects.common.RevealCardsFromLibraryUntilEffect;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.PutCards;
import mage.filter.StaticFilters;
import mage.watchers.common.AbilityResolvedWatcher;

/**
 *
 * @author muz
 */
public final class NissaLeylineTamer extends CardImpl {

    public NissaLeylineTamer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}{U}{B}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Deathtouch
        this.addAbility(DeathtouchAbility.getInstance());

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Landfall -- Whenever a land you control enters, draw a card. Then if this is the first time this ability has resolved this turn,
        // reveal cards from the top of your library until you reveal a creature card. Put that card onto the battlefield and the rest on the
        // bottom of your library in a random order.
        Ability ability = new LandfallAbility(new DrawCardSourceControllerEffect(1), false);
        ability.addEffect(new IfAbilityHasResolvedXTimesEffect(
            Outcome.PutCreatureInPlay, 1,
            new RevealCardsFromLibraryUntilEffect(
                StaticFilters.FILTER_CARD_CREATURE, PutCards.BATTLEFIELD, PutCards.BOTTOM_RANDOM
            )
        ).setText("Then if this is the first time this ability has resolved this turn, reveal cards from the top of your library until you reveal "
            + "a creature card. Put that card onto the battlefield and the rest on the bottom of your library in a random order."));
        this.addAbility(ability, new AbilityResolvedWatcher());
    }

    private NissaLeylineTamer(final NissaLeylineTamer card) {
        super(card);
    }

    @Override
    public NissaLeylineTamer copy() {
        return new NissaLeylineTamer(this);
    }
}
