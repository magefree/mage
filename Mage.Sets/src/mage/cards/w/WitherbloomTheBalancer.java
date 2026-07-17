package mage.cards.w;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.common.FilterNonlandCard;
import mage.filter.predicate.Predicates;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.GainAbilityControlledSpellsEffect;
import mage.abilities.keyword.AffinityAbility;
import mage.abilities.keyword.DeathtouchAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AffinityType;
import mage.constants.CardType;

/**
 * @author balazskristof
 */
public final class WitherbloomTheBalancer extends CardImpl {
    private static final FilterNonlandCard filter = new FilterNonlandCard("Instant and sorcery spells you cast");

    static {
        filter.add(Predicates.or(
                CardType.INSTANT.getPredicate(),
                CardType.SORCERY.getPredicate()
        ));
    }

    public WitherbloomTheBalancer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{6}{B}{G}");
        
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ELDER);
        this.subtype.add(SubType.DRAGON);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Affinity for creatures
        this.addAbility(new AffinityAbility(AffinityType.CREATURES));

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Deathtouch
        this.addAbility(DeathtouchAbility.getInstance());

        // Instant and sorcery spells you cast have affinity for creatures.
        this.addAbility(new SimpleStaticAbility(new GainAbilityControlledSpellsEffect(new AffinityAbility(AffinityType.CREATURES), filter)));
    }

    private WitherbloomTheBalancer(final WitherbloomTheBalancer card) {
        super(card);
    }

    @Override
    public WitherbloomTheBalancer copy() {
        return new WitherbloomTheBalancer(this);
    }
}
