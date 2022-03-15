
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetCard;
import mage.util.CardUtil;

/**
 *
 * @author Plopman
 */
public final class SummonersEgg extends CardImpl {

    public SummonersEgg(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{4}");
        this.subtype.add(SubType.EGG);
        this.power = new MageInt(0);
        this.toughness = new MageInt(4);

        // Imprint - When Summoner's Egg enters the battlefield, you may exile a card from your hand face down.
        this.addAbility(new EntersBattlefieldTriggeredAbility(
                new SummonersEggImprintEffect(), true)
                .setAbilityWord(AbilityWord.IMPRINT)
        );

        // When Summoner's Egg dies, turn the exiled card face up. If it's a creature card, put it onto the battlefield under your control.
        this.addAbility(new DiesSourceTriggeredAbility(new SummonersEggPutOntoBattlefieldEffect()));
    }

    private SummonersEgg(final SummonersEgg card) {
        super(card);
    }

    @Override
    public SummonersEgg copy() {
        return new SummonersEgg(this);
    }
}

class SummonersEggImprintEffect extends OneShotEffect {

    public SummonersEggImprintEffect() {
        super(Outcome.Benefit);
        staticText = "exile a card from your hand face down";
    }

    public SummonersEggImprintEffect(SummonersEggImprintEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent sourcePermanent = game.getPermanentOrLKIBattlefield(source.getSourceId());
        if (controller != null && sourcePermanent != null) {
            if (!controller.getHand().isEmpty()) {
                TargetCard target = new TargetCard(Zone.HAND, StaticFilters.FILTER_CARD);
                if (target.canChoose(source.getSourceId(), source.getControllerId(), game)
                        && controller.choose(Outcome.Benefit, controller.getHand(), target, game)) {
                    Card card = controller.getHand().get(target.getFirstTarget(), game);
                    if (card != null) {
                        card.setFaceDown(true, game);
                        controller.moveCardsToExile(card, source, game, false, source.getSourceId(), sourcePermanent.getIdName() + " (Imprint)");
                        card.setFaceDown(true, game);
                        sourcePermanent.imprint(card.getId(), game);
                        sourcePermanent.addInfo("imprint", CardUtil.addToolTipMarkTags("[Imprinted card]"), game);
                    }
                }
            }
            return true;
        }
        return false;

    }

    @Override
    public SummonersEggImprintEffect copy() {
        return new SummonersEggImprintEffect(this);
    }

}

class SummonersEggPutOntoBattlefieldEffect extends OneShotEffect {

    public SummonersEggPutOntoBattlefieldEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "turn the exiled card face up. If it's a creature card, put it onto the battlefield under your control";
    }

    public SummonersEggPutOntoBattlefieldEffect(final SummonersEggPutOntoBattlefieldEffect effect) {
        super(effect);
    }

    @Override
    public SummonersEggPutOntoBattlefieldEffect copy() {
        return new SummonersEggPutOntoBattlefieldEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Permanent SummonersEgg = game.getPermanentOrLKIBattlefield(source.getSourceId());
            if (SummonersEgg != null && SummonersEgg.getImprinted() != null && !SummonersEgg.getImprinted().isEmpty()) {
                Card imprintedCard = game.getCard(SummonersEgg.getImprinted().get(0));
                if (imprintedCard != null && game.getState().getZone(imprintedCard.getId()) == Zone.EXILED) {
                    //turn the exiled card face up.
                    imprintedCard.turnFaceUp(source, game, source.getControllerId());
                    //If it's a creature card,
                    if (imprintedCard.isCreature(game)) {
                        //put it onto the battlefield under your control
                        controller.moveCards(imprintedCard, Zone.BATTLEFIELD, source, game);
                    }
                }
            }
            return true;
        }
        return false;
    }
}
