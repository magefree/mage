package mage.cards.p;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.effects.common.continuous.GainAbilityControlledSpellsEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.StormAbility;
import mage.abilities.keyword.WardAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.common.FilterNonlandCard;
import mage.filter.predicate.Predicates;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PrismariTheInspiration extends CardImpl {

    private static final FilterNonlandCard filter = new FilterNonlandCard("instant and sorcery spells you cast");

    static {
        filter.add(Predicates.or(
                CardType.INSTANT.getPredicate(),
                CardType.SORCERY.getPredicate()
        ));
    }

    public PrismariTheInspiration(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{U}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ELDER);
        this.subtype.add(SubType.DRAGON);
        this.power = new MageInt(7);
        this.toughness = new MageInt(7);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Ward--Pay 5 life.
        this.addAbility(new WardAbility(new PayLifeCost(5), false));

        // Instant and sorcery spells you cast have storm.
        this.addAbility(new SimpleStaticAbility(new GainAbilityControlledSpellsEffect(new StormAbility(), filter))
                .addHint(StormAbility.getHint()));
    }

    private PrismariTheInspiration(final PrismariTheInspiration card) {
        super(card);
    }

    @Override
    public PrismariTheInspiration copy() {
        return new PrismariTheInspiration(this);
    }
}
