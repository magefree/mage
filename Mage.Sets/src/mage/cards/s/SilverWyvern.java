package mage.cards.s;

import mage.MageInt;
import mage.MageItem;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.ChooseNewTargetsTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterStackObject;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.game.Game;
import mage.game.stack.StackObject;
import mage.target.Target;
import mage.target.TargetStackObject;

import java.util.Collection;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Stream;

/**
 * @author LevelX2
 */
public final class SilverWyvern extends CardImpl {

    private static final FilterStackObject filter = new FilterStackObject();

    static {
        filter.add(SilverWyvernPredicate.instance);
    }

    public SilverWyvern(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}{U}");
        this.subtype.add(SubType.DRAKE);

        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // {U}: Change the target of target spell or ability that targets only Silver Wyvern. The new target must be a creature.
        Ability ability = new SimpleActivatedAbility(
                new ChooseNewTargetsTargetEffect(true, true)
                        .setText("Change the target of target spell or ability that targets only {this}. " +
                                "The new target must be a creature"),
                new ManaCostsImpl<>("{U}")
        );
        ability.addTarget(new TargetStackObject(filter));
        this.addAbility(ability);

    }

    private SilverWyvern(final SilverWyvern card) {
        super(card);
    }

    @Override
    public SilverWyvern copy() {
        return new SilverWyvern(this);
    }
}

enum SilverWyvernPredicate implements ObjectSourcePlayerPredicate<StackObject> {
    instance;

    @Override
    public boolean apply(ObjectSourcePlayer<StackObject> input, Game game) {
        return makeStream(input, game).anyMatch(input.getSourceId()::equals)
                && makeStream(input, game).allMatch(input.getSourceId()::equals);
    }

    private static final Stream<UUID> makeStream(ObjectSourcePlayer<StackObject> input, Game game) {
        return input.getObject()
                .getStackAbility()
                .getTargets()
                .stream()
                .map(Target::getTargets)
                .flatMap(Collection::stream)
                .map(game::getPermanent)
                .filter(Objects::nonNull)
                .map(MageItem::getId);
    }
}
