package mage.cards.p;

import mage.MageInt;
import mage.abilities.effects.common.MillThenPutInHandEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.AdventureCard;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class PicklockPrankster extends AdventureCard {

    private static final FilterCard filter = new FilterCard("an instant, sorcery, or Faerie card");

    static {
        filter.add(Predicates.or(
                CardType.INSTANT.getPredicate(),
                CardType.SORCERY.getPredicate(),
                SubType.FAERIE.getPredicate()
        ));
    }

    public PicklockPrankster(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, new CardType[]{CardType.INSTANT}, "{1}{U}", "Free the Fae", "{1}{U}");

        this.subtype.add(SubType.FAERIE);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Free the Fae
        // Mill four cards. Then put an instant, sorcery, or Faerie card from among the milled cards into your hand.
        this.getSpellCard().getSpellAbility().addEffect(new MillThenPutInHandEffect(4, filter, true));

        this.finalizeAdventure();
    }

    private PicklockPrankster(final PicklockPrankster card) {
        super(card);
    }

    @Override
    public PicklockPrankster copy() {
        return new PicklockPrankster(this);
    }
}
