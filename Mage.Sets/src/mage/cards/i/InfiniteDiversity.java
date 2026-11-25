package mage.cards.i;

import java.util.UUID;

import mage.ApprovingObject;
import mage.abilities.Ability;
import mage.abilities.dynamicvalue.common.FederationCount;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ExileSpellEffect;
import mage.cards.*;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInGraveyard;
import org.apache.log4j.Logger;

/**
 *
 * @author Grath
 */
public final class InfiniteDiversity extends CardImpl {

    public InfiniteDiversity(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{7}{R}{R}");
        

        // Federation -- Exile target instant or sorcery card from your graveyard. Copy that card X times, where X is
        // the number of creature types among non-Borg creatures you control. You may cast the copies without paying
        // their mana costs. Exile Infinite Diversity.
        this.getSpellAbility().addEffect(new InfiniteDiversityEffect());
        this.getSpellAbility().addEffect(new ExileSpellEffect());
        this.getSpellAbility().addTarget(new TargetCardInGraveyard(StaticFilters.FILTER_CARD_INSTANT_OR_SORCERY));
        this.getSpellAbility().setAbilityWord(AbilityWord.FEDERATION);
        this.getSpellAbility().addHint(FederationCount.instance.getHint());
    }

    private InfiniteDiversity(final InfiniteDiversity card) {
        super(card);
    }

    @Override
    public InfiniteDiversity copy() {
        return new InfiniteDiversity(this);
    }
}

class InfiniteDiversityEffect extends OneShotEffect {

    InfiniteDiversityEffect() {
        super(Outcome.Benefit);
        staticText = "Exile target instant or sorcery card from a graveyard. " +
                "Copy that card X times, where X is the number of creature types among non-Borg creatures you control. " +
                "You may cast the copies without paying their mana costs.";
    }

    private InfiniteDiversityEffect(final InfiniteDiversityEffect effect) {
        super(effect);
    }

    @Override
    public InfiniteDiversityEffect copy() {
        return new InfiniteDiversityEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Card card = game.getCard(source.getFirstTarget());
        if (player == null || card == null) {
            return false;
        }
        player.moveCards(card, Zone.EXILED, source, game);
        Cards cards = new CardsImpl();
        int federationCount = FederationCount.instance.calculate(game, source, this);
        for (int i = 0; i < federationCount; i++) {
            Card copiedCard = game.copyCard(card, source, source.getControllerId());
            cards.add(copiedCard);
        }
        for (Card copiedCard : cards.getCards(game)) {
            if (!player.chooseUse(outcome, "Cast the copied card?", source, game)) {
                continue;
            }
            if (copiedCard.getSpellAbility() != null) {
                game.getState().setValue("PlayFromNotOwnHandZone" + copiedCard.getId(), Boolean.TRUE);
                player.cast(player.chooseAbilityForCast(copiedCard, game, true),
                        game, true, new ApprovingObject(source, game));
                game.getState().setValue("PlayFromNotOwnHandZone" + copiedCard.getId(), null);
            } else {
                Logger.getLogger(InfiniteDiversityEffect.class).error("Infinite Diversity: "
                        + "spell ability == null " + copiedCard.getName());
            }
        }
        return true;
    }
}