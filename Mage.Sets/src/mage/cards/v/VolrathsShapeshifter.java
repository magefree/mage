package mage.cards.v;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.discard.DiscardControllerEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 * @author ImperatorPrime
 */
public final class VolrathsShapeshifter extends CardImpl {

    public VolrathsShapeshifter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}{U}");
        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.SHAPESHIFTER);
        this.power = new MageInt(0);
        this.toughness = new MageInt(1);

        // As long as the top card of your graveyard is a creature card, Volrath's Shapeshifter has the full text of that card and has the text "{2}: Discard a card."
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new VolrathsShapeshifterEffect()));

        // {2}: Discard a card.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new DiscardControllerEffect(1), new ManaCostsImpl("{2}")));
    }

    private VolrathsShapeshifter(final VolrathsShapeshifter card) {
        super(card);
    }

    @Override
    public VolrathsShapeshifter copy() {
        return new VolrathsShapeshifter(this);
    }
}

class VolrathsShapeshifterEffect extends ContinuousEffectImpl {

    public VolrathsShapeshifterEffect() {
        super(Duration.WhileOnBattlefield, Layer.TextChangingEffects_3, SubLayer.NA, Outcome.BecomeCreature);
        staticText = "As long as the top card of your graveyard is a creature card, "
                + "{this} has the full text of that card and has the text \"2: Discard a card.\" "
                + "({this} has that card's name, mana cost, color, types, abilities, power, and toughness.) ";
    }

    public VolrathsShapeshifterEffect(final VolrathsShapeshifterEffect effect) {
        super(effect);
    }

    @Override
    public VolrathsShapeshifterEffect copy() {
        return new VolrathsShapeshifterEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Card card = game.getPlayer(source.getControllerId()).getGraveyard().getTopCard(game);
        Permanent permanent = game.getPermanent(source.getSourceId());

        if (card == null
                || permanent == null
                || !card.isCreature(game)) {
            return false;
        }

        permanent.getPower().setValue(card.getPower().getValue());
        permanent.getToughness().setValue(card.getToughness().getValue());
        permanent.getColor(game).setColor(card.getColor(game));
        permanent.getManaCost().clear();
        permanent.getManaCost().add(card.getManaCost().copy());
        permanent.removeAllCardTypes(game);
        permanent.setName(card.getName());

        for (CardType type : card.getCardType(game)) {
            permanent.addCardType(game, type);
        }

        permanent.removeAllSubTypes(game);
        permanent.copySubTypesFrom(game, card);

        permanent.getSuperType().clear();
        for (SuperType type : card.getSuperType()) {
            permanent.addSuperType(type);

        }

        for (Ability ability : card.getAbilities(game)) {
            if (!permanent.getAbilities().contains(ability)) {
                permanent.addAbility(ability, source.getSourceId(), game);
            }
        }

        return true;
    }
}
