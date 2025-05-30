package mage.cards.g;

import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.continuous.LookAtTopCardOfLibraryAnyTimeEffect;
import mage.abilities.effects.common.continuous.PlayFromTopOfLibraryEffect;
import mage.abilities.effects.keyword.SurveilEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ManaValuePredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GlarbCalamitysAugur extends CardImpl {

    private static final FilterCard filter = new FilterCard("play lands and cast spells with mana value 4 or greater");

    static {
        filter.add(Predicates.or(
                CardType.LAND.getPredicate(),
                new ManaValuePredicate(ComparisonType.MORE_THAN, 3)
        ));
    }

    public GlarbCalamitysAugur(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{B}{G}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.FROG);
        this.subtype.add(SubType.WIZARD);
        this.subtype.add(SubType.NOBLE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // Deathtouch
        this.addAbility(DeathtouchAbility.getInstance());

        // You may look at the top card of your library any time.
        this.addAbility(new SimpleStaticAbility(new LookAtTopCardOfLibraryAnyTimeEffect()));

        // You may play lands and cast spells with mana value 4 or greater from the top of your library.
        this.addAbility(new SimpleStaticAbility(new PlayFromTopOfLibraryEffect(filter)));

        // {T}: Surveil 2.
        this.addAbility(new SimpleActivatedAbility(new SurveilEffect(2, false), new TapSourceCost()));
    }

    private GlarbCalamitysAugur(final GlarbCalamitysAugur card) {
        super(card);
    }

    @Override
    public GlarbCalamitysAugur copy() {
        return new GlarbCalamitysAugur(this);
    }
}
