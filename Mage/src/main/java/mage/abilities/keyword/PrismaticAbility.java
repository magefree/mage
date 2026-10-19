package mage.abilities.keyword;

import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.StaticAbility;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.AsThoughManaEffect;
import mage.constants.AsThoughEffectType;
import mage.constants.Duration;
import mage.constants.ManaType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.ManaPoolItem;
import mage.util.CardUtil;

import java.util.Objects;
import java.util.UUID;

/**
 * Mana of any type can be spent to cast this spell.
 */
public class PrismaticAbility extends StaticAbility {

    public PrismaticAbility() {
        super(Zone.ALL, new PrismaticEffect());
    }

    private PrismaticAbility(final PrismaticAbility ability) {
        super(ability);
    }

    @Override
    public PrismaticAbility copy() {
        return new PrismaticAbility(this);
    }

    @Override
    public String getRule() {
        return "prismatic <i>(Mana of any type can be spent to cast this spell.)</i>";
    }
}

class PrismaticEffect extends AsThoughEffectImpl implements AsThoughManaEffect {

    PrismaticEffect() {
        super(AsThoughEffectType.SPEND_OTHER_MANA, Duration.EndOfGame, Outcome.Benefit);
    }

    private PrismaticEffect(final PrismaticEffect effect) {
        super(effect);
    }

    @Override
    public PrismaticEffect copy() {
        return new PrismaticEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean applies(UUID objectId, Ability source, UUID affectedControllerId, Game game) {
        return source.isControlledBy(affectedControllerId)
                && Objects.equals(CardUtil.getMainCardId(game, objectId), CardUtil.getMainCardId(game, source.getSourceId()));
    }

    @Override
    public boolean applies(UUID objectId, Ability affectedAbility, Ability source, Game game, UUID playerId) {
        return affectedAbility instanceof SpellAbility && applies(objectId, source, playerId, game);
    }

    @Override
    public ManaType getAsThoughManaType(ManaType manaType, ManaPoolItem mana, UUID affectedControllerId, Ability source, Game game) {
        return mana.getFirstAvailable();
    }
}
