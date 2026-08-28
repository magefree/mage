package mage.cards.l;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;
import mage.target.TargetPermanent;
import mage.abilities.keyword.VigilanceAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.ExileThenReturnTargetEffect;
import mage.abilities.keyword.WardAbility;
import mage.cards.AdventureCard;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 * @author muz
 */
public final class LakeTownMariners extends AdventureCard {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("creatures and/or lands you control");

    static {
        filter.add(Predicates.or(
            CardType.CREATURE.getPredicate(),
            CardType.LAND.getPredicate()
        ));
    }

    public LakeTownMariners(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, new CardType[]{CardType.INSTANT}, "{4}{U}{U}", "Gone Fishing", "{3}{U}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CITIZEN);
        this.power = new MageInt(6);
        this.toughness = new MageInt(5);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Ward {2}
        this.addAbility(new WardAbility(new ManaCostsImpl<>("{2}")));

        // Gone Fishing
        // Exile two target creatures and/or lands you control, then return them to the battlefield under their owner's control.
        this.getSpellCard().getSpellAbility().addEffect(
            new ExileThenReturnTargetEffect(false, false)
                .setText("exile two target creatures and/or lands you control, then return them to the battlefield under their owner's control")
        );
        this.getSpellCard().getSpellAbility().addTarget(new TargetPermanent(2, filter));

        this.finalizeAdventure();
    }

    private LakeTownMariners(final LakeTownMariners card) {
        super(card);
    }

    @Override
    public LakeTownMariners copy() {
        return new LakeTownMariners(this);
    }
}
