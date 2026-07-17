package mage.cards.i;

import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.token.FoodAbility;
import mage.constants.SubType;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.game.Game;
import mage.game.permanent.PermanentToken;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class IceCreamKitty extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("another creature or token");

    static {
        filter.add(IceCreamKittyPredicate.instance);
    }

    public IceCreamKitty(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{1}{B/G}");

        this.subtype.add(SubType.FOOD);
        this.subtype.add(SubType.CAT);
        this.subtype.add(SubType.MUTANT);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // {2}, Sacrifice another creature or token: Draw a card. Activate only as a sorcery.
        Ability ability = new ActivateAsSorceryActivatedAbility(new DrawCardSourceControllerEffect(1), new ManaCostsImpl<>("{2}"));
        ability.addCost(new SacrificeTargetCost(filter));
        this.addAbility(ability);

        // {2}, {T}, Sacrifice this creature: You gain 3 life.
        this.addAbility(new FoodAbility());
    }

    private IceCreamKitty(final IceCreamKitty card) {
        super(card);
    }

    @Override
    public IceCreamKitty copy() {
        return new IceCreamKitty(this);
    }
}

enum IceCreamKittyPredicate implements ObjectSourcePlayerPredicate<MageObject> {
    instance;

    @Override
    public boolean apply(ObjectSourcePlayer<MageObject> input, Game game) {
        MageObject object = input.getObject();
        if (object instanceof PermanentToken) {
            return true;
        }
        if (!object.getId().equals(input.getSourceId())) {
            return object.isCreature(game);
        }
        return false;
    }
}
