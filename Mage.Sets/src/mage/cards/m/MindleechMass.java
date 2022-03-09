package mage.cards.m;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class MindleechMass extends CardImpl {

    public MindleechMass(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{U}{B}{B}");
        this.subtype.add(SubType.HORROR);

        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Whenever Mindleech Mass deals combat damage to a player, you may look at that 
        // player's hand. If you do, you may cast a nonland card in it without paying that card's mana cost.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(
                new MindleechMassEffect(), true, true
        ));
    }

    private MindleechMass(final MindleechMass card) {
        super(card);
    }

    @Override
    public MindleechMass copy() {
        return new MindleechMass(this);
    }
}

class MindleechMassEffect extends OneShotEffect {

    public MindleechMassEffect() {
        super(Outcome.PlayForFree);
        this.staticText = "look at that player's hand. If you do, you " +
                "may cast a spell from among those cards without paying its mana cost";
    }

    public MindleechMassEffect(final MindleechMassEffect effect) {
        super(effect);
    }

    @Override
    public MindleechMassEffect copy() {
        return new MindleechMassEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Player opponent = game.getPlayer(getTargetPointer().getFirst(game, source));
        if (controller == null || opponent == null) {
            return false;
        }
        controller.lookAtCards(opponent.getName(), opponent.getHand(), game);
        return CardUtil.castSpellWithAttributesForFree(
                controller, source, game, new CardsImpl(opponent.getHand()),
                StaticFilters.FILTER_CARD_INSTANT_OR_SORCERY
        );
    }
}
