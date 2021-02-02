package mage.cards.i;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.common.FilterOwnedCard;
import mage.filter.predicate.Predicates;
import mage.game.ExileZone;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInExile;

import java.util.UUID;
import mage.ApprovingObject;

/**
 * @author TheElk801
 */
public final class IzzetChemister extends CardImpl {

    private static final FilterCard filter = new FilterOwnedCard("instant or sorcery card from your graveyard");

    static {
        filter.add(Predicates.or(
                CardType.INSTANT.getPredicate(),
                CardType.SORCERY.getPredicate())
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

    private IzzetChemister(final IzzetChemister card) {
        super(card);
    }

    @Override
    public IzzetChemister copy() {
        return new IzzetChemister(this);
    }
}

class IzzetChemisterCastFromExileEffect extends OneShotEffect {

    private final UUID exileId;

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
        if (controller != null
                && exile != null) {
            Cards cardsToExile = new CardsImpl();
            cardsToExile.addAll(exile.getCards(game));
            OuterLoop:
            while (cardsToExile.count(filter, game) > 0) {
                if (!controller.canRespond()) {
                    return false;
                }
                TargetCardInExile target = new TargetCardInExile(0, 1, filter, exileId, false);
                target.setNotTarget(true);
                while (controller.canRespond()
                        && cardsToExile.count(filter, game) > 0
                        && controller.choose(Outcome.PlayForFree, cardsToExile, target, game)) {
                    Card card = game.getCard(target.getFirstTarget());
                    if (card != null) {
                        game.getState().setValue("PlayFromNotOwnHandZone" + card.getId(), Boolean.TRUE);
                        controller.cast(controller.chooseAbilityForCast(card, game, true), game, true,
                                new ApprovingObject(source, game));
                        game.getState().setValue("PlayFromNotOwnHandZone" + card.getId(), null);
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
