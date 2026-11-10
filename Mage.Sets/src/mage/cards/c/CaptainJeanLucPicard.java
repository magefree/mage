package mage.cards.c;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.costs.common.TapTargetCost;
import mage.abilities.effects.SearchEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.counters.Counters;
import mage.filter.FilterCard;
import mage.filter.FilterSpell;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.game.Game;
import mage.game.permanent.token.OfficerToken;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author muz
 */
public final class CaptainJeanLucPicard extends CardImpl {

    private static final FilterSpell spellFilter = new FilterSpell("a creature or Spacecraft spell");
    private static final FilterControlledPermanent officerFilter = new FilterControlledPermanent("untapped Officers you control");

    static {
        spellFilter.add(Predicates.or(CardType.CREATURE.getPredicate(), SubType.SPACECRAFT.getPredicate()));
        officerFilter.add(SubType.OFFICER.getPredicate());
        officerFilter.add(TappedPredicate.UNTAPPED);
    }

    public CaptainJeanLucPicard(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.OFFICER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Other creatures you control get +1/+1.
        this.addAbility(new SimpleStaticAbility(new BoostControlledEffect(1, 1, Duration.WhileOnBattlefield, true)));

        // Whenever you cast a creature or Spacecraft spell, create a 1/1 red Officer creature token.
        this.addAbility(new SpellCastControllerTriggeredAbility(new CreateTokenEffect(new OfficerToken()), spellFilter, false));

        // Tap seven untapped Officers you control: Search your library for a Spacecraft card, put it onto the battlefield with seven charge counters on it, then shuffle.
        this.addAbility(new SimpleActivatedAbility(new CaptainJeanLucPicardEffect(), new TapTargetCost(7, officerFilter)));
    }

    private CaptainJeanLucPicard(final CaptainJeanLucPicard card) {
        super(card);
    }

    @Override
    public CaptainJeanLucPicard copy() {
        return new CaptainJeanLucPicard(this);
    }
}

class CaptainJeanLucPicardEffect extends SearchEffect {

    private static final FilterCard filter = new FilterCard("a Spacecraft card");

    static {
        filter.add(SubType.SPACECRAFT.getPredicate());
    }

    CaptainJeanLucPicardEffect() {
        super(new TargetCardInLibrary(filter), Outcome.PutCardInPlay);
        staticText = "search your library for a Spacecraft card, put it onto the battlefield with seven charge counters on it, then shuffle";
    }

    private CaptainJeanLucPicardEffect(final CaptainJeanLucPicardEffect effect) {
        super(effect);
    }

    @Override
    public CaptainJeanLucPicardEffect copy() {
        return new CaptainJeanLucPicardEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        target.clearChosen();
        if (controller.searchLibrary(target, source, game)) {
            Card card = game.getCard(target.getFirstTarget());
            if (card != null) {
                game.setEnterWithCounters(card.getId(), new Counters(CounterType.CHARGE.createInstance(7)));
                controller.moveCards(card, Zone.BATTLEFIELD, source, game);
            }
        }
        controller.shuffleLibrary(source, game);
        return true;
    }
}
