package mage.cards.e;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CopyEffect;
import mage.abilities.effects.common.TapSourceEffect;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.PermanentCard;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetCardInGraveyard;
import mage.util.functions.CopyApplier;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class EchoingDeeps extends CardImpl {

    public EchoingDeeps(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        this.subtype.add(SubType.CAVE);

        // You may have Echoing Deeps enter the battlefield tapped as a copy of any land card in a graveyard, except it's a Cave in addition to its other types.
        this.addAbility(new EntersBattlefieldAbility(new EchoingDeepsEffect(), true));

        // {T}: Add {C}.
        this.addAbility(new ColorlessManaAbility());
    }

    private EchoingDeeps(final EchoingDeeps card) {
        super(card);
    }

    @Override
    public EchoingDeeps copy() {
        return new EchoingDeeps(this);
    }
}

class EchoingDeepsEffect extends OneShotEffect {

    private static final FilterCard filter = new FilterCard("land card in a graveyard");

    static {
        filter.add(CardType.LAND.getPredicate());
    }

    EchoingDeepsEffect() {
        super(Outcome.Copy);
        staticText = "tapped as a copy of any land card in a graveyard, except it's a Cave in addition to its other types";
    }

    private EchoingDeepsEffect(final EchoingDeepsEffect effect) {
        super(effect);
    }

    @Override
    public EchoingDeepsEffect copy() {
        return new EchoingDeepsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourcePermanent = game.getPermanentEntering(source.getSourceId());
        if (sourcePermanent == null) {
            sourcePermanent = game.getObject(source);
        }
        if (controller == null || sourcePermanent == null) {
            return false;
        }

        Target target = new TargetCardInGraveyard(0, 1, filter, false);
        controller.choose(Outcome.Copy, target, source, game);
        Card copyTo = game.getCard(target.getFirstTarget());
        if (copyTo == null) {
            return false;
        }

        new TapSourceEffect(true).apply(game, source);

        Permanent newBluePrint = new PermanentCard(copyTo, source.getControllerId(), game);
        newBluePrint.assignNewId();
        CopyApplier applier = new EchoingDeepsApplier();
        applier.apply(game, newBluePrint, source, source.getSourceId());
        CopyEffect copyEffect = new CopyEffect(Duration.WhileOnBattlefield, newBluePrint, source.getSourceId());
        copyEffect.newId();
        copyEffect.setApplier(applier);
        copyEffect.init(source, game);
        game.addEffect(copyEffect, source);
        return true;
    }

}

class EchoingDeepsApplier extends CopyApplier {
    @Override
    public boolean apply(Game game, MageObject blueprint, Ability source, UUID targetObjectId) {
        blueprint.addSubType(SubType.CAVE);
        return true;
    }
}