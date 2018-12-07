package mage.cards.c;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ChooseACardNameEffect;
import mage.abilities.keyword.FlashbackAbility;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TimingRule;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author jonubuu
 */
public final class CabalTherapy extends CardImpl {

    public CabalTherapy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{B}");

        // Name a nonland card. Target player reveals their hand and discards all cards with that name.
        this.getSpellAbility().addEffect((new ChooseACardNameEffect(ChooseACardNameEffect.TypeOfName.NON_LAND_NAME)));
        this.getSpellAbility().addTarget(new TargetPlayer());
        this.getSpellAbility().addEffect(new CabalTherapyEffect());

        // Flashback-Sacrifice a creature.
        this.addAbility(new FlashbackAbility(
                new SacrificeTargetCost(new TargetControlledCreaturePermanent(1, 1, new FilterControlledCreaturePermanent("a creature"), true)),
                TimingRule.SORCERY));
    }

    public CabalTherapy(final CabalTherapy card) {
        super(card);
    }

    @Override
    public CabalTherapy copy() {
        return new CabalTherapy(this);
    }
}

class CabalTherapyEffect extends OneShotEffect {

    public CabalTherapyEffect() {
        super(Outcome.Discard);
        staticText = "Target player reveals their hand and discards all cards with that name";
    }

    public CabalTherapyEffect(final CabalTherapyEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player targetPlayer = game.getPlayer(targetPointer.getFirst(game, source));
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = game.getObject(source.getSourceId());
        if (targetPlayer != null && controller != null && sourceObject != null) {
            String cardName = (String) game.getState().getValue(source.getSourceId().toString() + ChooseACardNameEffect.INFO_KEY);
            Cards hand = targetPlayer.getHand();

            for (Card card : hand.getCards(game)) {
                if (card.isSplitCard()) {
                    SplitCard splitCard = (SplitCard) card;
                    if (CardUtil.haveSameNames(splitCard.getLeftHalfCard().getName(), cardName)) {
                        targetPlayer.discard(card, source, game);
                    } else if (CardUtil.haveSameNames(splitCard.getRightHalfCard().getName(), cardName)) {
                        targetPlayer.discard(card, source, game);
                    }
                }
                if (CardUtil.haveSameNames(card.getName(), cardName)) {
                    targetPlayer.discard(card, source, game);
                }
            }
            targetPlayer.revealCards("Cabal Therapy", hand, game);
        }
        return true;
    }

    @Override
    public CabalTherapyEffect copy() {
        return new CabalTherapyEffect(this);
    }
}
