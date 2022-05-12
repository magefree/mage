package mage.cards.c;

import java.util.UUID;
import mage.ApprovingObject;
import mage.MageObject;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DamagePlayersEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.GetEmblemEffect;
import mage.abilities.effects.mana.BasicManaEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.command.emblems.ChandraTorchOfDefianceEmblem;
import mage.players.Library;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

/**
 * @author fireshoes
 */
public final class ChandraTorchOfDefiance extends CardImpl {

    public ChandraTorchOfDefiance(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{2}{R}{R}");
        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.CHANDRA);

        this.setStartingLoyalty(4);

        // +1: Exile the top card of your library. You may cast that card. If you don't, Chandra, Torch of Defiance deals 2 damage to each opponent.
        LoyaltyAbility ability = new LoyaltyAbility(new ChandraTorchOfDefianceEffect(), 1);
        this.addAbility(ability);

        // +1: Add {R}{R}.
        this.addAbility(new LoyaltyAbility(new BasicManaEffect(Mana.RedMana(2)), +1));

        // -3: Chandra, Torch of Defiance deals 4 damage to target creature.
        ability = new LoyaltyAbility(new DamageTargetEffect(4), -3);
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);

        // -7: You get an emblem with "Whenever you cast a spell, this emblem deals 5 damage to any target."
        this.addAbility(new LoyaltyAbility(new GetEmblemEffect(new ChandraTorchOfDefianceEmblem()), -7));
    }

    private ChandraTorchOfDefiance(final ChandraTorchOfDefiance card) {
        super(card);
    }

    @Override
    public ChandraTorchOfDefiance copy() {
        return new ChandraTorchOfDefiance(this);
    }
}

class ChandraTorchOfDefianceEffect extends OneShotEffect {

    public ChandraTorchOfDefianceEffect() {
        super(Outcome.Detriment);
        this.staticText = "Exile the top card of your library. You may cast that card. If you don't, {this} deals 2 damage to each opponent";
    }

    public ChandraTorchOfDefianceEffect(final ChandraTorchOfDefianceEffect effect) {
        super(effect);
    }

    @Override
    public ChandraTorchOfDefianceEffect copy() {
        return new ChandraTorchOfDefianceEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = source.getSourceObject(game);
        if (controller != null && sourceObject != null && controller.getLibrary().hasCards()) {
            Library library = controller.getLibrary();
            Card card = library.getFromTop(game);
            if (card != null) {
                boolean cardWasCast = false;
                controller.moveCardsToExile(card, source, game, true, source.getSourceId(), sourceObject.getIdName());
                if (!card.getManaCost().isEmpty()
                        || !card.isLand(game)) {
                    if (controller.chooseUse(Outcome.Benefit, "Cast " + card.getName() + "? (You still pay the costs)", source, game)
                            && (game.getState().getZone(card.getId()) == Zone.EXILED)) { // card must be in the exile zone
                        game.getState().setValue("PlayFromNotOwnHandZone" + card.getId(), Boolean.TRUE);  // enable the card to be cast from the exile zone
                        cardWasCast = controller.cast(controller.chooseAbilityForCast(card, game, false),
                                game, false, new ApprovingObject(source, game));
                        game.getState().setValue("PlayFromNotOwnHandZone" + card.getId(), null);  // reset to null
                    }
                }
                if (!cardWasCast) {
                    new DamagePlayersEffect(Outcome.Damage, StaticValue.get(2), TargetController.OPPONENT).apply(game, source);
                }
            }
            return true;
        }
        return false;
    }
}
