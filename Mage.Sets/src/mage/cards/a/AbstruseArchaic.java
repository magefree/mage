package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.CopyTargetStackAbilityEffect;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.FilterStackObject;
import mage.filter.predicate.Predicate;
import mage.game.Game;
import mage.game.stack.StackAbility;
import mage.game.stack.StackObject;
import mage.target.common.TargetActivatedOrTriggeredAbility;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AbstruseArchaic extends CardImpl {

    private static final FilterStackObject filter
            = new FilterStackObject("activated or triggered ability you control from a colorless source");

    static {
        filter.add(AbstruseArchaicPredicate.instance);
        filter.add(TargetController.YOU.getControllerPredicate());
    }

    public AbstruseArchaic(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}");

        this.subtype.add(SubType.AVATAR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // {1}, {T}: Copy target activated or triggered ability you control from a colorless source. You may choose new targets for the copy.
        Ability ability = new SimpleActivatedAbility(new CopyTargetStackAbilityEffect(), new GenericManaCost(1));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetActivatedOrTriggeredAbility(filter));
        this.addAbility(ability);
    }

    private AbstruseArchaic(final AbstruseArchaic card) {
        super(card);
    }

    @Override
    public AbstruseArchaic copy() {
        return new AbstruseArchaic(this);
    }
}

enum AbstruseArchaicPredicate implements Predicate<StackObject> {
    instance;

    @Override
    public boolean apply(StackObject input, Game game) {
        return input instanceof StackAbility
                && ((StackAbility) input).getSourceObject(game).getColor(game).isColorless();
    }
}
