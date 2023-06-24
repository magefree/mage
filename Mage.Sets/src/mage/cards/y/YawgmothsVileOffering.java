package mage.cards.y;

import mage.abilities.Ability;
import mage.abilities.common.LegendarySpellAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ExileSpellEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.common.FilterCreatureOrPlaneswalkerPermanent;
import mage.filter.common.FilterPermanentCard;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInGraveyard;
import mage.target.common.TargetCreatureOrPlaneswalker;

import java.util.UUID;

/**
 * @author JRHerlehy Created on 4/8/18.
 */
public final class YawgmothsVileOffering extends CardImpl {

    private static final FilterPermanentCard cardFilter = new FilterPermanentCard("creature or planeswalker card");

    static {
        cardFilter.add(Predicates.or(
                CardType.CREATURE.getPredicate(),
                CardType.PLANESWALKER.getPredicate()
        ));
    }

    public YawgmothsVileOffering(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{B}");
        this.supertype.add(SuperType.LEGENDARY);

        // (You may cast a legendary sorcery only if you control a legendary creature or planeswalker.)
        this.addAbility(new LegendarySpellAbility());

        // Put up to one target creature or planeswalker from a graveyard onto the battlefield under your control.
        // Destroy up to one target creature or planeswalker. Exile Yawgmoth's Vile Offering.
        this.getSpellAbility().addEffect(new YawgmothsVileOfferingEffect());
        this.getSpellAbility().addTarget(new TargetCardInGraveyard(0, 1, cardFilter));
        this.getSpellAbility().addTarget(new TargetCreatureOrPlaneswalker(0, 1, new FilterCreatureOrPlaneswalkerPermanent(), false));
        this.getSpellAbility().addEffect(new ExileSpellEffect());
    }

    private YawgmothsVileOffering(final YawgmothsVileOffering card) {
        super(card);
    }

    @Override
    public YawgmothsVileOffering copy() {
        return new YawgmothsVileOffering(this);
    }
}

class YawgmothsVileOfferingEffect extends OneShotEffect {

    public YawgmothsVileOfferingEffect() {
        super(Outcome.Benefit);
        this.staticText = "Put up to one target creature or planeswalker card from a graveyard onto the battlefield under your control. Destroy up to one target creature or planeswalker";
    }

    public YawgmothsVileOfferingEffect(final YawgmothsVileOfferingEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }

        Card returnCard = game.getCard(source.getTargets().getFirstTarget());
        if (returnCard != null) {
            controller.moveCards(returnCard, Zone.BATTLEFIELD, source, game);
        }

        Permanent permanent = game.getPermanent(source.getTargets().get(1).getFirstTarget());

        if (permanent != null) {
            permanent.destroy(source, game, false);
        }

        return false;
    }

    @Override
    public YawgmothsVileOfferingEffect copy() {
        return new YawgmothsVileOfferingEffect(this);
    }
}
