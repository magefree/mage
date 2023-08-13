package mage.cards.l;

import mage.MageInt;
import mage.MageObject;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CopyEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreatureCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetCardInGraveyard;
import mage.util.functions.CopyApplier;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class LazotepConvert extends CardImpl {

    public LazotepConvert(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "");

        this.subtype.add(SubType.ZOMBIE);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);
        this.color.setBlue(true);
        this.color.setBlack(true);
        this.nightCard = true;

        // You may have Lazotep Convert enter the battlefield as a copy of any creature card in a graveyard, except it's a 4/4 black Zombie in addition to its other types.
        this.addAbility(new EntersBattlefieldAbility(new LazotepConvertCopyEffect(), true));
    }

    private LazotepConvert(final LazotepConvert card) {
        super(card);
    }

    @Override
    public LazotepConvert copy() {
        return new LazotepConvert(this);
    }
}

class LazotepConvertCopyEffect extends OneShotEffect {

    private static final CopyApplier applier = new CopyApplier() {

        @Override
        public boolean apply(Game game, MageObject blueprint, Ability source, UUID copyToObjectId) {
            blueprint.removePTCDA();
            blueprint.getPower().setModifiedBaseValue(4);
            blueprint.getToughness().setModifiedBaseValue(4);
            blueprint.addSubType(SubType.ZOMBIE);
            blueprint.getColor().addColor(ObjectColor.BLACK);
            return true;
        }
    };

    private static final FilterCard filter = new FilterCreatureCard("creature card in a graveyard");

    public LazotepConvertCopyEffect() {
        super(Outcome.Copy);
        this.staticText = "as a copy of any creature card in a graveyard, " +
                "except it's a 4/4 black Zombie in addition to its other types";
    }

    public LazotepConvertCopyEffect(final LazotepConvertCopyEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Target target = new TargetCardInGraveyard(0, 1, filter);
        target.setNotTarget(true);
        player.choose(outcome, target, source, game);
        Card copyFromCard = game.getCard(target.getFirstTarget());
        if (copyFromCard == null) {
            return true;
        }
        Card modifiedCopy = copyFromCard.copy();
        //Appliers must be applied before CopyEffect, its applier setting is just for copies of copies
        applier.apply(game, modifiedCopy, source, source.getSourceId());
        game.addEffect(new CopyEffect(
                Duration.Custom, modifiedCopy, source.getSourceId()
        ).setApplier(applier), source);
        return true;
    }

    @Override
    public LazotepConvertCopyEffect copy() {
        return new LazotepConvertCopyEffect(this);
    }
}
