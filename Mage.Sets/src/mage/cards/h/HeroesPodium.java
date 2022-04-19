package mage.cards.h;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.abilities.effects.common.LookLibraryControllerEffect.PutCards;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SuperType;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterCreatureCard;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;

/**
 *
 * @author LevelX2
 */
public final class HeroesPodium extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("each legendary creature");
    private static final FilterCreatureCard filter2 = new FilterCreatureCard("a legendary creature card");

    static {
        filter.add(SuperType.LEGENDARY.getPredicate());
        filter2.add(SuperType.LEGENDARY.getPredicate());
    }

    public HeroesPodium(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{5}");
        addSuperType(SuperType.LEGENDARY);

        // Each legendary creature you control gets +1/+1 for each other legendary creature you control.
        this.addAbility(new SimpleStaticAbility(
                new BoostControlledEffect(HeroesPodiumValue.instance, HeroesPodiumValue.instance, Duration.WhileOnBattlefield, filter, false)));
        // {X}, {T}: Look at the top X cards of your library.
        // You may reveal a legendary creature card from among them and put it into your hand.
        // Put the rest on the bottom of your library in a random order.
        Ability ability = new SimpleActivatedAbility(
                new LookLibraryAndPickControllerEffect(ManacostVariableValue.REGULAR, 1, filter2, PutCards.HAND, PutCards.BOTTOM_RANDOM),
                new ManaCostsImpl("{X}"));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    private HeroesPodium(final HeroesPodium card) {
        super(card);
    }

    @Override
    public HeroesPodium copy() {
        return new HeroesPodium(this);
    }
}

enum HeroesPodiumValue implements DynamicValue {
    instance;

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("other legendary creature you control");

    static {
        filter.add(SuperType.LEGENDARY.getPredicate());
    }

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        return Math.max(game.getBattlefield().count(filter, sourceAbility.getControllerId(), sourceAbility, game) - 1, 0);
    }

    @Override
    public String toString() {
        return "1";
    }

    @Override
    public String getMessage() {
        return filter.getMessage();
    }

    @Override
    public HeroesPodiumValue copy() {
        return instance;
    }
}
