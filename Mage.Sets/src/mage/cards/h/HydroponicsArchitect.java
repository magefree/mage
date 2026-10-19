package mage.cards.h;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.mana.BlueManaAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;
import mage.util.CardUtil;
import mage.util.PerpetualUtil;
import mage.util.RandomUtil;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public final class HydroponicsArchitect extends CardImpl {

    public HydroponicsArchitect(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{U}");
        this.subtype.add(SubType.INSECT);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        this.addAbility(FlyingAbility.getInstance());
        this.addAbility(new AttacksTriggeredAbility(new HydroponicsArchitectEffect()));
    }

    private HydroponicsArchitect(final HydroponicsArchitect card) {
        super(card);
    }

    @Override
    public HydroponicsArchitect copy() {
        return new HydroponicsArchitect(this);
    }
}

class HydroponicsArchitectEffect extends OneShotEffect {

    HydroponicsArchitectEffect() {
        super(Outcome.Benefit);
        staticText = "a random land card in your library perpetually becomes a basic Island in addition to its other types and gains \"When this land enters, draw a card.\"";
    }

    private HydroponicsArchitectEffect(final HydroponicsArchitectEffect effect) {
        super(effect);
    }

    @Override
    public HydroponicsArchitectEffect copy() {
        return new HydroponicsArchitectEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        List<Card> lands = player.getLibrary().getCards(game).stream()
                .filter(card -> card.isLand(game)).collect(Collectors.toList());
        Card card = RandomUtil.randomFromCollection(lands);
        if (card == null) {
            return false;
        }
        PerpetualUtil.modify(card, game, changed -> {
            changed.addSuperType(SuperType.BASIC);
            changed.addSubType(SubType.ISLAND);
        });
        game.addEffect(new HydroponicsArchitectIslandEffect().setTargetPointer(
                new FixedTarget(CardUtil.getDefaultCardSideForBattlefield(game, card).getId(), 0)), source);
        if (!card.getAbilities(game).containsClass(BlueManaAbility.class)) {
            PerpetualUtil.addAbility(card, new BlueManaAbility(), source, game);
        }
        PerpetualUtil.addAbility(card, new EntersBattlefieldTriggeredAbility(new DrawCardSourceControllerEffect(1)), source, game);
        return true;
    }
}

class HydroponicsArchitectIslandEffect extends ContinuousEffectImpl {

    HydroponicsArchitectIslandEffect() {
        super(Duration.EndOfGame, Layer.TypeChangingEffects_4, SubLayer.NA, Outcome.Benefit);
    }

    private HydroponicsArchitectIslandEffect(final HydroponicsArchitectIslandEffect effect) {
        super(effect);
    }

    @Override
    public HydroponicsArchitectIslandEffect copy() {
        return new HydroponicsArchitectIslandEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (permanent != null) {
            permanent.addSuperType(game, SuperType.BASIC);
            permanent.addSubType(game, SubType.ISLAND);
        }
        return true;
    }
}
