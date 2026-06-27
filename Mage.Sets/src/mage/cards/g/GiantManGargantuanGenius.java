package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.Mana;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.PowerPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.ReachAbility;
import mage.abilities.triggers.BeginningOfFirstMainTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Outcome;

/**
 *
 * @author muz
 */
public final class GiantManGargantuanGenius extends CardImpl {

    public GiantManGargantuanGenius(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SCIENTIST);
        this.subtype.add(SubType.HERO);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Reach
        this.addAbility(ReachAbility.getInstance());

        // At the beginning of your first main phase, add {G} for each creature you control with power 4 or greater.
        this.addAbility(new BeginningOfFirstMainTriggeredAbility(new GiantManGargantuanGeniusEffect()));
    }

    private GiantManGargantuanGenius(final GiantManGargantuanGenius card) {
        super(card);
    }

    @Override
    public GiantManGargantuanGenius copy() {
        return new GiantManGargantuanGenius(this);
    }
}

class GiantManGargantuanGeniusEffect extends OneShotEffect {

    private static final FilterPermanent filter = new FilterCreaturePermanent("creature with power 4 or greater you control");

    static {
        filter.add(TargetController.YOU.getControllerPredicate());
        filter.add(new PowerPredicate(ComparisonType.MORE_THAN, 3));
    }

    private static final DynamicValue xValue = new PermanentsOnBattlefieldCount(filter);

    GiantManGargantuanGeniusEffect() {
        super(Outcome.PutManaInPool);
        this.staticText = "add {G} for each creature you control with power 4 or greater";
    }

    private GiantManGargantuanGeniusEffect(final GiantManGargantuanGeniusEffect effect) {
        super(effect);
    }

    @Override
    public GiantManGargantuanGeniusEffect copy() {
        return new GiantManGargantuanGeniusEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            int count = xValue.calculate(game, source, this);
            if (count > 0) {
                player.getManaPool().addMana(Mana.GreenMana(count), game, source);
            }
        }
        return true;
    }
}
