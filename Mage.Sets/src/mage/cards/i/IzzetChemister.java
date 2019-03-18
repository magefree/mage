
package mage.cards.i;

import java.util.UUID;
import mage.MageInt;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.common.FilterOwnedCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.game.ExileZone;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInExile;

/**
 *
 * @author TheElk801
 */
public final class IzzetChemister extends CardImpl {

    private static final FilterCard filter = new FilterOwnedCard("instant or sorcery card from your graveyard");

    static {
        filter.add(Predicates.or(
                new CardTypePredicate(CardType.INSTANT),
                new CardTypePredicate(CardType.SORCERY))
        );
    }

    public IzzetChemister(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");

        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // R, T: Exile target instant or sorcery card from your graveyard.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new ExileTargetEffect(this.getId(), this.getIdName()), new ManaCostsImpl("{R}"));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetCard(Zone.GRAVEYARD, filter));
        this.addAbility(ability);

        // 1R, T: Sacrifice Izzet Chemister: Cast any number of cards exiled with Izzet Chemister without paying their mana costs.
        IzzetChemisterCastFromExileEffect returnFromExileEffect = new IzzetChemisterCastFromExileEffect(this.getId(), "Cast any number of cards exiled with {this} without paying their mana costs.");
        ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, returnFromExileEffect, new ManaCostsImpl("{1}{R}"));
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);
    }

    public IzzetChemister(final IzzetChemister card) {
        super(card);
    }

    @Override
    public IzzetChemister copy() {
        return new IzzetChemister(this);
    }
}

class IzzetChemisterCastFromExileEffect extends OneShotEffect {

    private UUID exileId;

    public IzzetChemisterCastFromExileEffect(UUID exileId, String description) {
        super(Outcome.PlayForFree);
        this.exileId = exileId;
        this.setText(description);
    }

    public IzzetChemisterCastFromExileEffect(final IzzetChemisterCastFromExileEffect effect) {
        super(effect);
        this.exileId = effect.exileId;
    }

    @Override
    public IzzetChemisterCastFromExileEffect copy() {
        return new IzzetChemisterCastFromExileEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        ExileZone exile = game.getExile().getExileZone(exileId);
        Player controller = game.getPlayer(source.getControllerId());
        FilterCard filter = new FilterCard();
        if (controller != null && exile != null) {
            Cards cardsToExile = new CardsImpl();
            cardsToExile.addAll(exile.getCards(game));
            OuterLoop:
            while (cardsToExile.count(filter, game) > 0) {
                if (!controller.canRespond()) {
                    return false;
                }
                TargetCardInExile target = new TargetCardInExile(0, 1, filter, exileId, false);
                target.setNotTarget(true);
                while (cardsToExile.count(filter, game) > 0 && controller.choose(Outcome.PlayForFree, cardsToExile, target, game)) {
                    Card card = game.getCard(target.getFirstTarget());
                    if (card != null) {
                        controller.cast(card.getSpellAbility(), game, true, new MageObjectReference(source.getSourceObject(game), game));
                        cardsToExile.remove(card);
                    } else {
                        break OuterLoop;
                    }
                    target.clearChosen();
                }
            }
            return true;
        }
        return false;
    }
}
