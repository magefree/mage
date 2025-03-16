package mage.cards.m;

import java.util.UUID;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CopyEffect;
import mage.cards.*;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.PermanentCard;
import mage.players.Player;
import mage.target.common.TargetCardInExile;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.targetadjustment.TargetAdjuster;
import mage.util.CardUtil;
import mage.util.functions.CopyApplier;

/**
 *
 * @author Jmlundeen
 */
public final class MimeoplasmReveredOne extends CardImpl {

    public MimeoplasmReveredOne(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{X}{B}{G}{U}");
        
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.OOZE);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // As Mimeoplasm enters, exile up to X creature cards from your graveyard. It enters with three +1/+1 counters on it for each creature card exiled this way.
        this.addAbility(new AsEntersBattlefieldAbility(new MimeoplasmReveredOneEntersEffect()));
        // {2}: Mimeoplasm becomes a copy of target creature card exiled with it, except it's 0/0 and has this ability.
        Ability ability2 = new SimpleActivatedAbility(new MimeoplasmReveredOneEffect(), new ManaCostsImpl<>("{2}"));
        ability2.setTargetAdjuster(MimeoPlasmReveredOneTargetAdjuster.instance);
        this.addAbility(ability2);
    }

    private MimeoplasmReveredOne(final MimeoplasmReveredOne card) {
        super(card);
    }

    @Override
    public MimeoplasmReveredOne copy() {
        return new MimeoplasmReveredOne(this);
    }
}

enum MimeoPlasmReveredOneTargetAdjuster implements TargetAdjuster {

    instance;

    @Override
    public void adjustTargets(Ability ability, Game game) {
        ability.getTargets().clear();
        ability.addTarget(new TargetCardInExile(StaticFilters.FILTER_CARD_CREATURES, CardUtil.getExileZoneId(game, ability, -1)));
    }
}

class MimeoplasmReveredOneEntersEffect extends OneShotEffect {

    MimeoplasmReveredOneEntersEffect() {
        super(Outcome.Benefit);
        staticText = "exile up to X creature cards from your graveyard. " +
                "It enters with three +1/+1 counters on it for each creature card exiled this way";
    }

    private MimeoplasmReveredOneEntersEffect(final MimeoplasmReveredOneEntersEffect effect) {
        super(effect);
    }

    @Override
    public MimeoplasmReveredOneEntersEffect copy() {
        return new MimeoplasmReveredOneEntersEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent mimeoplasm = game.getPermanentEntering(source.getSourceId());
        if (controller == null || controller.getGraveyard().isEmpty() || mimeoplasm == null) {
            return false;
        }
        int xValue = CardUtil.getSourceCostsTag(game, source, "X", 0);
        if (xValue == 0) {
            return false;
        }
        TargetCardInYourGraveyard target = new TargetCardInYourGraveyard(0, xValue, StaticFilters.FILTER_CARD_CREATURES);
        target.withNotTarget(true);
        if (!controller.chooseTarget(Outcome.Exile, target, source, game)) {
            return false;
        }
        Cards cards = new CardsImpl();
        for (UUID targetId : target.getTargets()) {
            cards.add(controller.getGraveyard().get(targetId, game));
        }
        int zcc = CardUtil.getActualSourceObjectZoneChangeCounter(game, source);
        UUID exileId = CardUtil.getExileZoneId(game, source.getSourceId(), zcc);
        if (!controller.moveCardsToExile(cards.getCards(game), source, game, true,
                exileId, CardUtil.getSourceName(game, source))) {
            return false;
        }
        int counters = cards.count(StaticFilters.FILTER_CARD_CREATURE, game) * 3;
        mimeoplasm.addCounters(CounterType.P1P1.createInstance(counters), controller.getId(), source, game);
        return true;
    }
}

class MimeoplasmReveredOneEffect extends OneShotEffect {

    public MimeoplasmReveredOneEffect() {
        super(Outcome.Benefit);
        this.staticText = "{this} becomes a copy of target creature card exiled with it, except it's 0/0 and has this ability.";
    }

    public MimeoplasmReveredOneEffect(final MimeoplasmReveredOneEffect effect) {
        super(effect);
    }

    @Override
    public MimeoplasmReveredOneEffect copy() {
        return new MimeoplasmReveredOneEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent mimeoplasm = game.getPermanent(source.getSourceId());
        if (controller == null || mimeoplasm == null) {
            return false;
        }
        Card chosen = game.getCard(getTargetPointer().getFirst(game, source));
        if (chosen == null) {
            return false;
        }
        Permanent newBluePrint = new PermanentCard(chosen, source.getControllerId(), game);
        newBluePrint.assignNewId();
        CopyApplier applier = new MimeoPlasmReveredOneCopyApplier();
        applier.apply(game, newBluePrint, source, mimeoplasm.getId());
        CopyEffect copyEffect = new CopyEffect(Duration.Custom, newBluePrint, mimeoplasm.getId());
        copyEffect.setApplier(applier);
        copyEffect.init(source.copy(), game);
        game.addEffect(copyEffect, source);
        return true;
    }
}

class MimeoPlasmReveredOneCopyApplier extends CopyApplier {

    @Override
    public boolean apply(Game game, MageObject blueprint, Ability source, UUID targetObjectId) {
        Ability ability = new SimpleActivatedAbility(new MimeoplasmReveredOneEffect(), new ManaCostsImpl<>("{2}"));
        ability.setTargetAdjuster(MimeoPlasmReveredOneTargetAdjuster.instance);
        blueprint.getPower().setModifiedBaseValue(0);
        blueprint.getToughness().setModifiedBaseValue(0);
        blueprint.getAbilities().add(ability);
        return true;
    }
}
