package mage.cards.g;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalRequirementEffect;
import mage.abilities.effects.RequirementEffect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.hint.ConditionHint;
import mage.abilities.hint.Hint;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.IndestructibleAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author DarioBarrabinoDev
 */
public final class GalactusDevourerOfWorlds extends CardImpl {

    private static final FilterControlledCreaturePermanent filter
            = new FilterControlledCreaturePermanent("a creature named Silver Surfer, Galactus's Herald");

    static {
        filter.add(new NamePredicate("Silver Surfer, Galactus's Herald"));
    }

    private static final Condition condition
            = new PermanentsOnTheBattlefieldCondition(filter, ComparisonType.FEWER_THAN, 1);
    private static final Hint hint = new ConditionHint(
            new PermanentsOnTheBattlefieldCondition(filter), "You control a creature named Silver Surfer, Galactus's Herald"
    );

    public GalactusDevourerOfWorlds(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{10}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ELDER);
        this.subtype.add(SubType.ALIEN);
        this.power = new MageInt(12);
        this.toughness = new MageInt(12);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Indestructible
        this.addAbility(IndestructibleAbility.getInstance());

        // When Galactus enters, exile target permanent.
        Ability ability = new EntersBattlefieldTriggeredAbility(new ExileTargetEffect());
        ability.addTarget(new TargetPermanent());
        this.addAbility(ability);

        // Insatiable Hunger -- Galactus attacks an opponent with the most life among your opponents each combat if able unless you control a creature named Silver Surfer, Galactus's Herald.
        this.addAbility(new SimpleStaticAbility(new ConditionalRequirementEffect(
                new GalactusDevourerOfWorldsEffect(), condition,
                "{this} attacks an opponent with the most life among your opponents each combat if able "
                        + "unless you control a creature named Silver Surfer, Galactus's Herald"
        )).withFlavorWord("Insatiable Hunger").addHint(hint));
    }

    private GalactusDevourerOfWorlds(final GalactusDevourerOfWorlds card) {
        super(card);
    }

    @Override
    public GalactusDevourerOfWorlds copy() {
        return new GalactusDevourerOfWorlds(this);
    }
}

class GalactusDevourerOfWorldsEffect extends RequirementEffect {

    GalactusDevourerOfWorldsEffect() {
        super(Duration.WhileOnBattlefield);
    }

    private GalactusDevourerOfWorldsEffect(final GalactusDevourerOfWorldsEffect effect) {
        super(effect);
    }

    @Override
    public GalactusDevourerOfWorldsEffect copy() {
        return new GalactusDevourerOfWorldsEffect(this);
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        return permanent.getId().equals(source.getSourceId());
    }

    @Override
    public boolean mustAttack(Game game) {
        return true;
    }

    @Override
    public boolean mustBlock(Game game) {
        return false;
    }

    @Override
    public Set<UUID> mustAttackDefenders(Ability source, Game game) {
        List<Player> opponents = game
                .getOpponents(source.getControllerId(), true)
                .stream()
                .map(game::getPlayer)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        int mostLife = opponents
                .stream()
                .mapToInt(Player::getLife)
                .max()
                .orElse(Integer.MIN_VALUE);
        return opponents
                .stream()
                .filter(opponent -> opponent.getLife() == mostLife)
                .map(Player::getId)
                .collect(Collectors.toSet());
    }
}
