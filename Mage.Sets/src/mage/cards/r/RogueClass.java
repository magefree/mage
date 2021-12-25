package mage.cards.r;

import mage.abilities.Ability;
import mage.abilities.common.DealsDamageToAPlayerAllTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.AsThoughManaEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.effects.common.continuous.GainClassAbilitySourceEffect;
import mage.abilities.keyword.ClassLevelAbility;
import mage.abilities.keyword.ClassReminderAbility;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.ExileZone;
import mage.game.Game;
import mage.players.ManaPoolItem;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RogueClass extends CardImpl {

    public RogueClass(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{U}{B}");

        this.subtype.add(SubType.CLASS);

        // (Gain the next level as a sorcery to add its ability.)
        this.addAbility(new ClassReminderAbility());

        // Whenever a creature you controls deals combat damage to a player, exile the top card of that player's library face down. You may look at it for as long as it remains exiled.
        this.addAbility(new DealsDamageToAPlayerAllTriggeredAbility(
                new RogueClassExileEffect(), StaticFilters.FILTER_CONTROLLED_A_CREATURE,
                false, SetTargetPointer.PLAYER, true, true
        ));

        // {1}{U}{B}: Level 2
        this.addAbility(new ClassLevelAbility(2, "{1}{U}{B}"));

        // Creatures you control have menace.
        this.addAbility(new SimpleStaticAbility(new GainClassAbilitySourceEffect(
                new GainAbilityControlledEffect(
                        new MenaceAbility(), Duration.WhileOnBattlefield,
                        StaticFilters.FILTER_PERMANENT_CREATURES
                ), 2
        )));

        // {2}{U}{B}: Level 3
        this.addAbility(new ClassLevelAbility(3, "{2}{U}{B}"));

        // You may play cards exiled with Rogue Class, and you may spend mana as through it were mana of any color to cast them.
        Ability ability = new SimpleStaticAbility(new RogueClassPlayEffect());
        ability.addEffect(new RogueClassManaEffect());
        this.addAbility(new SimpleStaticAbility(new GainClassAbilitySourceEffect(ability, 3)));
    }

    private RogueClass(final RogueClass card) {
        super(card);
    }

    @Override
    public RogueClass copy() {
        return new RogueClass(this);
    }
}

class RogueClassExileEffect extends OneShotEffect {

    RogueClassExileEffect() {
        super(Outcome.Benefit);
        staticText = "exile the top card of that player's library face down. " +
                "You may look at it for as long as it remains exiled";
    }

    private RogueClassExileEffect(final RogueClassExileEffect effect) {
        super(effect);
    }

    @Override
    public RogueClassExileEffect copy() {
        return new RogueClassExileEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Player opponent = game.getPlayer(getTargetPointer().getFirst(game, source));
        if (controller == null || opponent == null) {
            return false;
        }
        Card card = opponent.getLibrary().getFromTop(game);
        if (card == null) {
            return false;
        }
        controller.moveCardsToExile(
                card, source, game, false,
                CardUtil.getExileZoneId(game, source),
                CardUtil.getSourceName(game, source)
        );
        card.setFaceDown(true, game);
        game.addEffect(new RogueClassLookEffect().setTargetPointer(new FixedTarget(card, game)), source);
        return true;
    }
}

class RogueClassLookEffect extends AsThoughEffectImpl {

    RogueClassLookEffect() {
        super(AsThoughEffectType.LOOK_AT_FACE_DOWN, Duration.EndOfGame, Outcome.Benefit);
    }

    private RogueClassLookEffect(final RogueClassLookEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public RogueClassLookEffect copy() {
        return new RogueClassLookEffect(this);
    }

    @Override
    public boolean applies(UUID objectId, Ability source, UUID affectedControllerId, Game game) {
        UUID cardId = getTargetPointer().getFirst(game, source);
        if (cardId == null) {
            discard();
            return false;
        }
        return source.isControlledBy(affectedControllerId)
                && cardId.equals(objectId);
    }
}

class RogueClassPlayEffect extends AsThoughEffectImpl {

    RogueClassPlayEffect() {
        super(AsThoughEffectType.PLAY_FROM_NOT_OWN_HAND_ZONE, Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "you may play cards exiled with {this}";
    }

    private RogueClassPlayEffect(final RogueClassPlayEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public RogueClassPlayEffect copy() {
        return new RogueClassPlayEffect(this);
    }

    @Override
    public boolean applies(UUID objectId, Ability source, UUID affectedControllerId, Game game) {
        if (!source.isControlledBy(affectedControllerId)) {
            return false;
        }
        ExileZone exileZone = game.getState().getExile().getExileZone(CardUtil.getExileZoneId(game, source));
        return exileZone != null && exileZone.contains(CardUtil.getMainCardId(game, objectId));
    }
}

class RogueClassManaEffect extends AsThoughEffectImpl implements AsThoughManaEffect {

    RogueClassManaEffect() {
        super(AsThoughEffectType.SPEND_OTHER_MANA, Duration.WhileOnBattlefield, Outcome.Benefit);
        this.staticText = ", and you may spend mana as though it were mana of any color to cast those spells";
    }

    private RogueClassManaEffect(final RogueClassManaEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public RogueClassManaEffect copy() {
        return new RogueClassManaEffect(this);
    }

    @Override
    public boolean applies(UUID objectId, Ability source, UUID affectedControllerId, Game game) {
        ExileZone exileZone = game.getState().getExile().getExileZone(CardUtil.getExileZoneId(game, source));
        return source.isControlledBy(affectedControllerId)
                && exileZone != null
                && exileZone.contains(CardUtil.getMainCardId(game, objectId));
    }

    @Override
    public ManaType getAsThoughManaType(ManaType manaType, ManaPoolItem mana, UUID affectedControllerId, Ability source, Game game) {
        return mana.getFirstAvailable();
    }
}
