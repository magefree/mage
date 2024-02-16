package mage.cards.e;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.CastAsThoughItHadFlashAllEffect;
import mage.abilities.effects.common.continuous.LookAtTopCardOfLibraryAnyTimeEffect;
import mage.abilities.effects.common.continuous.PlayTheTopCardEffect;
import mage.abilities.keyword.ProwessAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.common.FilterNonlandCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.card.CardOnTopOfLibraryPredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ElshaOfTheInfinite extends CardImpl {

    private static final FilterCard filter = new FilterNonlandCard("cast noncreature spells");

    static {
        filter.add(Predicates.not(CardType.CREATURE.getPredicate()));
        filter.add(CardOnTopOfLibraryPredicate.YOUR);
    }

    public ElshaOfTheInfinite(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}{R}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.DJINN);
        this.subtype.add(SubType.MONK);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Prowess
        this.addAbility(new ProwessAbility());

        // You may look at the top card of your library any time.
        this.addAbility(new SimpleStaticAbility(new LookAtTopCardOfLibraryAnyTimeEffect()));

        // You may cast noncreature spells from the top of your library. If you cast a spell this way, you may cast it as though it had flash.
        Ability ability = new SimpleStaticAbility(new PlayTheTopCardEffect(TargetController.YOU, filter, false));
        ability.addEffect(new CastAsThoughItHadFlashAllEffect(
                Duration.WhileOnBattlefield, filter
        ).setText("If you cast a spell this way, you may cast it as though it had flash."));
        this.addAbility(ability);
    }

    private ElshaOfTheInfinite(final ElshaOfTheInfinite card) {
        super(card);
    }

    @Override
    public ElshaOfTheInfinite copy() {
        return new ElshaOfTheInfinite(this);
    }
}

