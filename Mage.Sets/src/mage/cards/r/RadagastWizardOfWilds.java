package mage.cards.r;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.WardAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.FilterSpell;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.permanent.token.BeastToken;
import mage.game.permanent.token.SwanSongBirdToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RadagastWizardOfWilds extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledPermanent("Beasts and Birds");
    private static final FilterSpell filter2 = new FilterSpell("a spell with mana value 5 or greater");

    static {
        filter.add(Predicates.or(
                SubType.BEAST.getPredicate(),
                SubType.BIRD.getPredicate()
        ));
        filter2.add(new ManaValuePredicate(ComparisonType.MORE_THAN, 4));
    }

    public RadagastWizardOfWilds(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.AVATAR);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(3);
        this.toughness = new MageInt(5);

        // Ward {1}
        this.addAbility(new WardAbility(new ManaCostsImpl<>("{1}"), false));

        // Beasts and Birds you control have ward {1}.
        this.addAbility(new SimpleStaticAbility(new GainAbilityControlledEffect(
                new WardAbility(new GenericManaCost(1), false), Duration.WhileOnBattlefield, filter
        )));

        // Whenever you cast a spell with mana value 5 or greater, choose one --
        // * Create a 3/3 green Beast creature token.
        Ability ability = new SpellCastControllerTriggeredAbility(
                new CreateTokenEffect(new BeastToken()), filter2, false
        );

        // * Create a 2/2 blue Bird creature token with flying.
        ability.addMode(new Mode(new CreateTokenEffect(new SwanSongBirdToken())));
        this.addAbility(ability);
    }

    private RadagastWizardOfWilds(final RadagastWizardOfWilds card) {
        super(card);
    }

    @Override
    public RadagastWizardOfWilds copy() {
        return new RadagastWizardOfWilds(this);
    }
}
