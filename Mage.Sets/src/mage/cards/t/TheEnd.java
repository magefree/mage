package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.XorLessLifeCondition;
import mage.abilities.effects.common.cost.SpellCostReductionSourceEffect;
import mage.abilities.effects.common.search.SearchTargetGraveyardHandLibraryForCardNameAndExileEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreatureOrPlaneswalker;

import java.util.UUID;

/**
 *
 * @author Susucr
 */
public final class TheEnd extends CardImpl {

    public TheEnd(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{B}{B}");

        // This spell costs {2} less to cast if your life total is 5 or less.
        this.addAbility(new SimpleStaticAbility(
                Zone.ALL,
                new SpellCostReductionSourceEffect(
                        2,
                        new XorLessLifeCondition(XorLessLifeCondition.CheckType.CONTROLLER, 5)
                ).setCanWorksOnStackOnly(true).setText("This spell costs {2} less to cast if your life total is 5 or less.")
        ).setRuleAtTheTop(true));

        // Exile target creature or planeswalker. Search its controller's graveyard, hand, and library for any number of cards with the same name as that permanent and exile them. That player shuffles, then draws card for each card exiled from their hand this way.
        this.getSpellAbility().addEffect(new TheEndEffect());
        this.getSpellAbility().addTarget(new TargetCreatureOrPlaneswalker());
    }

    private TheEnd(final TheEnd card) {
        super(card);
    }

    @Override
    public TheEnd copy() {
        return new TheEnd(this);
    }
}

class TheEndEffect extends SearchTargetGraveyardHandLibraryForCardNameAndExileEffect {

    TheEndEffect() {
        super(true, "its controller's", "any number of cards with the same name as that permanent");
        this.staticText = "Exile target creature or planeswalker. Search its controller's graveyard, hand, and library" +
                " for any number of cards with the same name as that permanent and exile them." +
                " That player shuffles, then draws a card for each card exiled from their hand this way";
    }

    private TheEndEffect(final TheEndEffect effect) {
        super(effect);
    }

    @Override
    public TheEndEffect copy() {
        return new TheEndEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        if (permanent == null) {
            return false;
        }
        String name = permanent.getName();
        Player player = game.getPlayer(permanent.getControllerId());
        if (player == null) {
            return false;
        }

        player.moveCards(permanent, Zone.EXILED, source, game);

        FilterCard filter = new FilterCard();
        filter.add(new NamePredicate(name));

        int cardsInHandBefore = player.getHand().count(filter, game);
        boolean result = super.applySearchAndExile(game, source, name, player.getId());
        int cardsExiled = cardsInHandBefore - player.getHand().count(filter, game);
        if (cardsExiled > 0) {
            player.drawCards(cardsExiled, source, game);
        }
        return result;
    }
}
