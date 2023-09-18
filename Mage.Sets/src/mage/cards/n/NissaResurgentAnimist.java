package mage.cards.n;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.LandfallAbility;
import mage.abilities.effects.common.IfAbilityHasResolvedXTimesEffect;
import mage.abilities.effects.common.RevealCardsFromLibraryUntilEffect;
import mage.abilities.effects.mana.AddManaOfAnyColorEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.watchers.common.AbilityResolvedWatcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class NissaResurgentAnimist extends CardImpl {

    private static final FilterCard filter = new FilterCard("an Elf or Elemental card");

    static {
        filter.add(Predicates.or(
                SubType.ELF.getPredicate(),
                SubType.ELEMENTAL.getPredicate()
        ));
    }

    public NissaResurgentAnimist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.SCOUT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Landfall--Whenever a land enters the battlefield under your control, add one mana of any color. Then if this is the second time this ability has resolved this turn, reveal cards from the top of your library until you reveal an Elf or Elemental card. Put that card into your hand and the rest on the bottom of your library in a random order.
        Ability ability = new LandfallAbility(new AddManaOfAnyColorEffect());
        ability.addEffect(new IfAbilityHasResolvedXTimesEffect(
                Outcome.DrawCard, 2,
                new RevealCardsFromLibraryUntilEffect(filter, PutCards.HAND, PutCards.BOTTOM_RANDOM)
        ).concatBy("Then"));
        this.addAbility(ability, new AbilityResolvedWatcher());
    }

    private NissaResurgentAnimist(final NissaResurgentAnimist card) {
        super(card);
    }

    @Override
    public NissaResurgentAnimist copy() {
        return new NissaResurgentAnimist(this);
    }
}
