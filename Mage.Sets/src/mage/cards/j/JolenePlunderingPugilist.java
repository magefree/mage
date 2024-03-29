package mage.cards.j;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksWithCreaturesTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.PowerPredicate;
import mage.game.permanent.token.TreasureToken;
import mage.target.common.TargetAnyTarget;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class JolenePlunderingPugilist extends CardImpl {

    private static final FilterCreaturePermanent filter =
            new FilterCreaturePermanent("creatures with power 4 or greater");
    private static final FilterControlledPermanent filterTreasure =
            new FilterControlledPermanent(SubType.TREASURE, "Treasure");

    static {
        filter.add(new PowerPredicate(ComparisonType.OR_GREATER, 4));
    }

    public JolenePlunderingPugilist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.MERCENARY);
        this.power = new MageInt(4);
        this.toughness = new MageInt(2);

        // Whenever you attack with one or more creatures with power 4 or greater, create a Treasure token.
        this.addAbility(new AttacksWithCreaturesTriggeredAbility(
                new CreateTokenEffect(new TreasureToken()), 1, filter
        ));

        // {1}{R}, Sacrifice a Treasure: Jolene, Plundering Pugilist deals 1 damage to any target.
        Ability ability = new SimpleActivatedAbility(
                new DamageTargetEffect(1),
                new ManaCostsImpl<>("{1}{R}")
        );
        ability.addCost(new SacrificeTargetCost(filterTreasure));
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);
    }

    private JolenePlunderingPugilist(final JolenePlunderingPugilist card) {
        super(card);
    }

    @Override
    public JolenePlunderingPugilist copy() {
        return new JolenePlunderingPugilist(this);
    }
}
