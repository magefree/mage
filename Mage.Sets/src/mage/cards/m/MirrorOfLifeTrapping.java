package mage.cards.m;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAllTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SetTargetPointer;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicate;
import mage.game.ExileZone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author xenohedron
 */
public final class MirrorOfLifeTrapping extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("a creature");
    static {
        filter.add(MirrorOfLifeTrappingCastPredicate.instance);
    }

    public MirrorOfLifeTrapping(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{4}");

        // Whenever a creature enters, if it was cast, exile it, then return all other permanent cards exiled with Mirror of Life Trapping to the battlefield under their owners' control.
        this.addAbility(new EntersBattlefieldAllTriggeredAbility(Zone.BATTLEFIELD, new MirrorOfLifeTrappingEffect(),
                filter, false, SetTargetPointer.PERMANENT));
    }

    private MirrorOfLifeTrapping(final MirrorOfLifeTrapping card) {
        super(card);
    }

    @Override
    public MirrorOfLifeTrapping copy() {
        return new MirrorOfLifeTrapping(this);
    }
}

enum MirrorOfLifeTrappingCastPredicate implements Predicate<Permanent> {
    instance;

    @Override
    public boolean apply(Permanent input, Game game) {
        int zcc = input.getZoneChangeCounter(game);
        Spell spell = game.getStack().getSpell(input.getId());
        return (spell != null && spell.getZoneChangeCounter(game) == zcc - 1)
                || game.getLastKnownInformation(input.getId(), Zone.STACK, zcc - 1) != null;
    }
}

class MirrorOfLifeTrappingEffect extends OneShotEffect {

    MirrorOfLifeTrappingEffect() {
        super(Outcome.Benefit);
        this.staticText = "exile it, then return all other permanent cards exiled with {this} to the battlefield under their owners' control";
    }

    private MirrorOfLifeTrappingEffect(final MirrorOfLifeTrappingEffect effect) {
        super(effect);
    }

    @Override
    public MirrorOfLifeTrappingEffect copy() {
        return new MirrorOfLifeTrappingEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = source.getSourceObject(game);
        if (controller == null || sourceObject == null) {
            return false;
        }

        UUID exileZoneId = CardUtil.getExileZoneId(game, source.getSourceId(), source.getStackMomentSourceZCC());
        ExileZone exileZone = game.getExile().getExileZone(exileZoneId);

        Cards toBattlefield = null;
        if (exileZone != null && !exileZone.isEmpty()) {
            toBattlefield = new CardsImpl(exileZone);
        }

        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (permanent != null) {
            controller.moveCardsToExile(permanent, source, game, true, exileZoneId, sourceObject.getIdName());
        }

        if (toBattlefield != null) {
            game.processAction();
            controller.moveCards(toBattlefield.getCards(StaticFilters.FILTER_CARD_PERMANENT, game),
                    Zone.BATTLEFIELD, source, game, false, false, true, null);
        }

        return true;
    }
}
