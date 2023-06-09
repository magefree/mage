package mage.cards.c;

import mage.MageInt;
import mage.MageObject;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterSpell;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.ExileZone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class CircuDimirLobotomist extends CardImpl {

    private static final FilterSpell filterBlue = new FilterSpell("a blue spell");
    private static final FilterSpell filterBlack = new FilterSpell("a black spell");

    static {
        filterBlue.add(new ColorPredicate(ObjectColor.BLUE));
        filterBlack.add(new ColorPredicate(ObjectColor.BLACK));
    }

    public CircuDimirLobotomist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Whenever you cast a blue spell, exile the top card of target library.
        Ability ability = new SpellCastControllerTriggeredAbility(new CircuDimirLobotomistEffect(), filterBlue, false);
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);

        // Whenever you cast a black spell, exile the top card of target library.
        ability = new SpellCastControllerTriggeredAbility(new CircuDimirLobotomistEffect(), filterBlack, false);
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);

        // Your opponents can't cast nonland cards with the same name as a card exiled with Circu, Dimir Lobotomist.
        this.addAbility(new SimpleStaticAbility(new CircuDimirLobotomistRuleModifyingEffect()));
    }

    private CircuDimirLobotomist(final CircuDimirLobotomist card) {
        super(card);
    }

    @Override
    public CircuDimirLobotomist copy() {
        return new CircuDimirLobotomist(this);
    }
}

class CircuDimirLobotomistEffect extends OneShotEffect {

    public CircuDimirLobotomistEffect() {
        super(Outcome.Detriment);
        this.staticText = "exile the top card of target player's library";
    }

    public CircuDimirLobotomistEffect(final CircuDimirLobotomistEffect effect) {
        super(effect);
    }

    @Override
    public CircuDimirLobotomistEffect copy() {
        return new CircuDimirLobotomistEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Player playerTargetLibrary = game.getPlayer(getTargetPointer().getFirst(game, source));
        if (controller == null || playerTargetLibrary == null) {
            return false;
        }
        controller.moveCardsToExile(
                playerTargetLibrary.getLibrary().getFromTop(game), source, game, true,
                CardUtil.getCardExileZoneId(game, source), CardUtil.getSourceName(game, source)
        );
        return true;
    }
}

class CircuDimirLobotomistRuleModifyingEffect extends ContinuousRuleModifyingEffectImpl {

    public CircuDimirLobotomistRuleModifyingEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Detriment);
        staticText = "Your opponents can't cast spells with the same name as a card exiled with {this}";
    }

    public CircuDimirLobotomistRuleModifyingEffect(final CircuDimirLobotomistRuleModifyingEffect effect) {
        super(effect);
    }

    @Override
    public CircuDimirLobotomistRuleModifyingEffect copy() {
        return new CircuDimirLobotomistRuleModifyingEffect(this);
    }

    @Override
    public String getInfoMessage(Ability source, GameEvent event, Game game) {
        return "You can't cast this spell because a card with the same name is exiled by " + CardUtil.getSourceName(game, source) + '.';
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (event.getType() != GameEvent.EventType.CAST_SPELL
                || !game.getOpponents(source.getControllerId()).contains(event.getPlayerId())) {
            return false;
        }
        MageObject object = game.getObject(event.getSourceId());
        ExileZone exileZone = game.getExile().getExileZone(CardUtil.getCardExileZoneId(game, source));
        return object != null
                && exileZone != null
                && !exileZone.isEmpty()
                && exileZone
                .getCards(game)
                .stream()
                .anyMatch(card -> CardUtil.haveSameNames(card, object));
    }
}
