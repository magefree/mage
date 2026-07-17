package mage.cards.q;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.GainAbilityControlledSpellsEffect;
import mage.abilities.keyword.CascadeAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.common.FilterNonlandCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.card.CastFromZonePredicate;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class QuandrixTheProof extends CardImpl {

    private static final FilterNonlandCard filter = new FilterNonlandCard("instant and sorcery spells you cast from your hand");

    static {
        filter.add(Predicates.or(
                CardType.INSTANT.getPredicate(),
                CardType.SORCERY.getPredicate()));
        filter.add(new CastFromZonePredicate(Zone.HAND));
    }

    public QuandrixTheProof(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{G}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ELDER);
        this.subtype.add(SubType.DRAGON);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Cascade
        this.addAbility(new CascadeAbility());

        // Instant and sorcery spells you cast from your hand have cascade.
        this.addAbility(new SimpleStaticAbility(
                new GainAbilityControlledSpellsEffect(new CascadeAbility(false), filter)
        ));
    }

    private QuandrixTheProof(final QuandrixTheProof card) {
        super(card);
    }

    @Override
    public QuandrixTheProof copy() {
        return new QuandrixTheProof(this);
    }
}
