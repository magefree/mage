package mage.cards.o;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.CopyEffect;
import mage.cards.*;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.ExileZone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.permanent.PermanentCard;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInExile;
import mage.util.CardUtil;
import mage.util.functions.CopyApplier;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class OlagLudevicsHubris extends CardImpl {

    public OlagLudevicsHubris(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.ZOMBIE);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);
        this.color.setBlue(true);
        this.color.setBlack(true);
        this.nightCard = true;

        // As this creature transforms in Olag, Ludevic's Hubris, it becomes a copy of a creature card exiled with it, except its name is Olag, Ludevic's Hubris, it's 4/4, and it's a legendary blue and black Zombie in addition to its other colors and types. Put a number of +1/+1 counters on Olag equal to the number of creature cards exiled with it.
        this.addAbility(new SimpleStaticAbility(new OlagLudevicsHubrisEffect()));
    }

    private OlagLudevicsHubris(final OlagLudevicsHubris card) {
        super(card);
    }

    @Override
    public OlagLudevicsHubris copy() {
        return new OlagLudevicsHubris(this);
    }
}

class OlagLudevicsHubrisEffect extends ReplacementEffectImpl {

    OlagLudevicsHubrisEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "as this creature transforms into {this}, it becomes a copy of a creature card exiled with it, " +
                "except its name is Olag, Ludevic's Hubris, it's 4/4, and it's a legendary blue and black " +
                "Zombie in addition to its other colors and types. Put a number of +1/+1 counters on {this} " +
                "equal to the number of creature cards exiled with it";
    }

    private OlagLudevicsHubrisEffect(final OlagLudevicsHubrisEffect effect) {
        super(effect);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        ExileZone exileZone = game.getExile().getExileZone(CardUtil.getExileZoneId(game, source));
        if (exileZone == null) {
            return false;
        }

        Cards cards = new CardsImpl(exileZone);
        cards.removeIf(uuid -> !game.getCard(uuid).isCreature(game));
        if (cards.isEmpty()) {
            return false;
        }

        Card copyFromCard = getCard(cards, source, game);
        if (copyFromCard == null) {
            return false;
        }
        Permanent newBluePrint = new PermanentCard(copyFromCard, source.getControllerId(), game);
        newBluePrint.assignNewId();
        CopyApplier applier = new OlagLudevicsHubrisCopyApplier();
        applier.apply(game, newBluePrint, source, source.getSourceId());
        CopyEffect copyEffect = new CopyEffect(Duration.Custom, newBluePrint, source.getSourceId());
        copyEffect.newId();
        copyEffect.setApplier(applier);
        Ability newAbility = source.copy();
        copyEffect.init(newAbility, game);
        game.addEffect(copyEffect, newAbility);
        return false;
    }

    private Card getCard(Cards cards, Ability source, Game game) {
        if (cards.size() == 1) {
            return cards.getRandom(game);
        }
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return cards.getRandom(game);
        }
        TargetCard target = new TargetCardInExile(StaticFilters.FILTER_CARD);
        player.choose(outcome, target, source, game);
        return cards.get(target.getFirstTarget(), game);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.TRANSFORMING;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return source.getSourceId().equals(event.getTargetId())
                && source.getSourcePermanentIfItStillExists(game) != null;
    }

    @Override
    public OlagLudevicsHubrisEffect copy() {
        return new OlagLudevicsHubrisEffect(this);
    }
}

class OlagLudevicsHubrisCopyApplier extends CopyApplier {

    @Override
    public boolean apply(Game game, MageObject blueprint, Ability source, UUID copyToObjectId) {
        blueprint.setName("Olag, Ludevic's Hubris");
        blueprint.addSuperType(SuperType.LEGENDARY);
        blueprint.addSubType(SubType.ZOMBIE);
        blueprint.getColor().setBlue(true);
        blueprint.getColor().setBlack(true);
        blueprint.getPower().setModifiedBaseValue(4);
        blueprint.getToughness().setModifiedBaseValue(4);
        return true;
    }
}
