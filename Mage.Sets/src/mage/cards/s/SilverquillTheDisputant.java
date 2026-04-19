package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.common.FilterNonlandCard;
import mage.filter.predicate.Predicates;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.GainAbilityControlledSpellsEffect;
import mage.abilities.keyword.CasualtyAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class SilverquillTheDisputant extends CardImpl {

    private static final FilterNonlandCard filter = new FilterNonlandCard("instant and sorcery spells");

    static {
        filter.add(Predicates.or(
            CardType.INSTANT.getPredicate(),
            CardType.SORCERY.getPredicate()
        ));
    }

    public SilverquillTheDisputant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ELDER);
        this.subtype.add(SubType.DRAGON);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Each instant and sorcery spell you cast has casualty 1.
        this.addAbility(
        new SimpleStaticAbility(new GainAbilityControlledSpellsEffect(new CasualtyAbility (1), filter)
                .setText("Each instant and sorcery spell you cast has casualty 1.")));
    }

    private SilverquillTheDisputant(final SilverquillTheDisputant card) {
        super(card);
    }

    @Override
    public SilverquillTheDisputant copy() {
        return new SilverquillTheDisputant(this);
    }
}
