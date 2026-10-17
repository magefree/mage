package mage.cards.g;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.effects.RequirementEffect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.IndestructibleAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
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
        this.addAbility(new SimpleStaticAbility(new GalactusDevourerOfWorldsEffect()).withFlavorWord("Insatiable Hunger"));
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

    private static final FilterControlledCreaturePermanent filter
            = new FilterControlledCreaturePermanent("a creature named Silver Surfer, Galactus's Herald");

    static {
        filter.add(new NamePredicate("Silver Surfer, Galactus's Herald"));
    }

    private static final Condition condition = new PermanentsOnTheBattlefieldCondition(filter);

    GalactusDevourerOfWorldsEffect() {
        super(Duration.WhileOnBattlefield);
        staticText = "{this} attacks an opponent with the most life among your opponents each combat if able "
                + "unless you control " + filter.getMessage();
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
        return permanent.getId().equals(source.getSourceId())
                && !condition.apply(game, source);
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
        // tied opponents all have the most life, attacking any of them obeys the requirement
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
