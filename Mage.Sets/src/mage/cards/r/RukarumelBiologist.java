package mage.cards.r;

import mage.MageInt;
import mage.abilities.ActivatedAbility;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.ChooseCreatureTypeEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.AddCreatureSubTypeAllMultiZoneEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.*;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.permanent.token.SliverToken;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class RukarumelBiologist extends CardImpl {

    public static final FilterControlledCreaturePermanent filterCreatures =
            new FilterControlledCreaturePermanent("Slivers you control and nontoken creatures you control");
    public static final FilterControlledCreatureSpell filterSpells = new FilterControlledCreatureSpell("creature spells you control");
    public static final FilterOwnedCreatureCard filterCards = new FilterOwnedCreatureCard("creature cards you own");

    static {
        filterCreatures.add(Predicates.or(
                SubType.SLIVER.getPredicate(),
                TokenPredicate.FALSE
        ));
    }

    public RukarumelBiologist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{W}{U}{B}{R}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // As Rukarumel, Biologist enters the battlefield, choose a creature type.
        this.addAbility(new AsEntersBattlefieldAbility(new ChooseCreatureTypeEffect(Outcome.Neutral)));

        // Slivers you control and nontoken creatures you control are the chosen type in addition to their other creature types. The same is true for creature spells you control and creature cards you own that aren't on the battlefield.
        this.addAbility(new SimpleStaticAbility(new AddCreatureSubTypeAllMultiZoneEffect(
                filterCreatures,
                filterSpells,
                filterCards
        )));

        // {3}, {T}: Create a 1/1 colorless Sliver creature token.
        ActivatedAbility activated = new SimpleActivatedAbility(
                Zone.BATTLEFIELD,
                new CreateTokenEffect(new SliverToken()),
                new ManaCostsImpl<>("{3}")
        );
        activated.addCost(new TapSourceCost());

        this.addAbility(activated);
    }

    private RukarumelBiologist(final RukarumelBiologist card) {
        super(card);
    }

    @Override
    public RukarumelBiologist copy() {
        return new RukarumelBiologist(this);
    }
}
