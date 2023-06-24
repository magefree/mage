package mage.cards.a;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CopyEffect;
import mage.abilities.keyword.FlashAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.card.PutIntoGraveFromBattlefieldThisTurnPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetCardInGraveyard;
import mage.util.functions.CopyApplier;
import mage.watchers.common.CardsPutIntoGraveyardWatcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ActivatedSleeper extends CardImpl {

    public ActivatedSleeper(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");

        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.SHAPESHIFTER);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // You may have Activated Sleeper enter the battlefield as a copy of any creature card in a graveyard that was put there from the battelfield this turn, except it's a Phyrexian in addition to its other types.
        this.addAbility(new EntersBattlefieldAbility(
                new ActivatedSleeperEffect(), true
        ), new CardsPutIntoGraveyardWatcher());
    }

    private ActivatedSleeper(final ActivatedSleeper card) {
        super(card);
    }

    @Override
    public ActivatedSleeper copy() {
        return new ActivatedSleeper(this);
    }
}

class ActivatedSleeperEffect extends OneShotEffect {

    private static final FilterCard filter = new FilterCreatureCard(
            "creature card in a graveyard that was put there from the battelfield this turn"
    );

    static {
        filter.add(PutIntoGraveFromBattlefieldThisTurnPredicate.instance);
    }

    ActivatedSleeperEffect() {
        super(Outcome.Benefit);
        staticText = "as a copy of any creature card in a graveyard that was put there from " +
                "the battlefield this turn, except it's a Phyrexian in addition to its other types";
    }

    private ActivatedSleeperEffect(final ActivatedSleeperEffect effect) {
        super(effect);
    }

    @Override
    public ActivatedSleeperEffect copy() {
        return new ActivatedSleeperEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Target target = new TargetCardInGraveyard(filter);
        target.setNotTarget(true);
        if (!target.canChoose(source.getControllerId(), source, game)) {
            return true;
        }
        player.choose(outcome, target, source, game);
        Card copyFromCard = game.getCard(target.getFirstTarget());
        if (copyFromCard != null) {
            CopyEffect copyEffect = new CopyEffect(Duration.Custom, copyFromCard, source.getSourceId());
            copyEffect.setApplier(new CopyApplier() {
                @Override
                public boolean apply(Game game, MageObject blueprint, Ability source, UUID targetObjectId) {
                    blueprint.addSubType(SubType.PHYREXIAN);
                    return true;
                }
            });
            game.addEffect(copyEffect, source);
        }
        return true;
    }
}
