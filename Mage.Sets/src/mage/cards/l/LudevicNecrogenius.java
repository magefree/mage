package mage.cards.l;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.common.EntersBattlefieldOrAttacksSourceTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.ExileXFromYourGraveCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.costs.mana.VariableManaCost;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.CopyEffect;
import mage.abilities.effects.common.InfoEffect;
import mage.abilities.effects.common.MillCardsControllerEffect;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.cards.*;
import mage.constants.*;
import mage.counters.CounterType;
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
public final class LudevicNecrogenius extends TransformingDoubleFacedCard {

    public LudevicNecrogenius(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new SuperType[]{SuperType.LEGENDARY}, new CardType[]{CardType.CREATURE}, new SubType[]{SubType.HUMAN, SubType.WIZARD}, "{U}{B}",
                "Olag, Ludevic's Hubris",
                new SuperType[]{SuperType.LEGENDARY}, new CardType[]{CardType.CREATURE}, new SubType[]{SubType.ZOMBIE}, "UB"
        );

        // Ludevic, Necrogenius
        this.getLeftHalfCard().setPT(2, 3);

        // Whenever Ludevic, Necrogenius enters the battlefield or attacks, mill a card.
        this.getLeftHalfCard().addAbility(new EntersBattlefieldOrAttacksSourceTriggeredAbility(new MillCardsControllerEffect(1)));

        // {X}{U}{U}{B}{B}, Exile X creature cards from your graveyard: Transform Ludevic, Necrogenius. X can't be zero. Activate only as a sorcery.
        Ability ability = new ActivateAsSorceryActivatedAbility(
                new TransformSourceEffect(), new ManaCostsImpl<>("{X}{U}{U}{B}{B}")
        );
        ability.addEffect(new InfoEffect("X can't be 0"));
        ability.addCost(new ExileXFromYourGraveCost(StaticFilters.FILTER_CARD_CREATURES_YOUR_GRAVEYARD));
        CardUtil.castStream(ability.getCosts().stream(), VariableManaCost.class).forEach(cost -> cost.setMinX(1));
        this.getLeftHalfCard().addAbility(ability);

        // Olag, Ludevic's Hubris
        this.getRightHalfCard().setPT(4, 4);

        // As this creature transforms in Olag, Ludevic's Hubris, it becomes a copy of a creature card exiled with it, except its name is Olag, Ludevic's Hubris, it's 4/4, and it's a legendary blue and black Zombie in addition to its other colors and types. Put a number of +1/+1 counters on Olag equal to the number of creature cards exiled with it.
        this.getRightHalfCard().addAbility(new SimpleStaticAbility(new OlagLudevicsHubrisEffect()));
    }

    private LudevicNecrogenius(final LudevicNecrogenius card) {
        super(card);
    }

    @Override
    public LudevicNecrogenius copy() {
        return new LudevicNecrogenius(this);
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
        Permanent permanent = source.getSourcePermanentIfItStillExists(game);
        if (permanent == null) {
            return false;
        }

        // must use object's zcc cause static and activated abilities init in diff time with diff zcc (see #13022)
        UUID exileId = CardUtil.getExileZoneId(game, source.getSourceId(), permanent.getZoneChangeCounter(game));
        ExileZone exileZone = game.getExile().getExileZone(exileId);
        if (exileZone == null) {
            return false;
        }

        Cards cards = new CardsImpl(exileZone);
        cards.removeIf(uuid -> !game.getCard(uuid).isCreature(game));
        if (cards.isEmpty()) {
            return false;
        }

        Card copyFromCard = getCard(cards, exileId, source, game);
        if (copyFromCard == null) {
            return false;
        }
        Permanent newBluePrint = new PermanentCard(CardUtil.getDefaultCardSideForBattlefield(game, copyFromCard), source.getControllerId(), game);
        newBluePrint.assignNewId();
        CopyApplier applier = new OlagLudevicsHubrisCopyApplier();
        applier.apply(game, newBluePrint, source, source.getSourceId());
        CopyEffect copyEffect = new CopyEffect(Duration.Custom, newBluePrint, source.getSourceId());
        copyEffect.setApplier(applier);
        game.addEffect(copyEffect, source);

        permanent.addCounters(CounterType.P1P1.createInstance(cards.size()), source.getControllerId(), source, game);
        return false;
    }

    private Card getCard(Cards cards, UUID exileId, Ability source, Game game) {
        if (cards.size() == 1) {
            return cards.getRandom(game);
        }
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return cards.getRandom(game);
        }
        TargetCard target = new TargetCardInExile(StaticFilters.FILTER_CARD_CREATURE, exileId);
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
