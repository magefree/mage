package mage.cards.n;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.dynamicvalue.common.DomainValue;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.abilities.hint.common.DomainHint;
import mage.abilities.keyword.FlyingAbility;
import mage.constants.*;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author weirddan455
 */
public final class NaelAvizoaAeronaut extends CardImpl {

    public NaelAvizoaAeronaut(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ELF, SubType.SCOUT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Domain -- Whenever Nael, Avizoa Aeronaut deals combat damage to a player, look at the top X cards of your library, where X is the number of basic land types among lands you control.
        // Put up to one of them on top of your library and the rest on the bottom in a random order. Then if there are five basic land types among lands you control, draw a card.
        Ability ability = new DealsCombatDamageToAPlayerTriggeredAbility(
                new LookLibraryAndPickControllerEffect(DomainValue.REGULAR, 1, PutCards.TOP_ANY, PutCards.BOTTOM_RANDOM, true),
                false
        );
        ability.addEffect(new NaelAvizoaAeronautEffect());
        ability.setAbilityWord(AbilityWord.DOMAIN);
        ability.addHint(DomainHint.instance);
        this.addAbility(ability);
    }

    private NaelAvizoaAeronaut(final NaelAvizoaAeronaut card) {
        super(card);
    }

    @Override
    public NaelAvizoaAeronaut copy() {
        return new NaelAvizoaAeronaut(this);
    }
}

class NaelAvizoaAeronautEffect extends OneShotEffect {

    public NaelAvizoaAeronautEffect() {
        super(Outcome.DrawCard);
        this.staticText = "Then if there are five basic land types among lands you control, draw a card.";
    }

    private NaelAvizoaAeronautEffect(final NaelAvizoaAeronautEffect effect) {
        super(effect);
    }

    @Override
    public NaelAvizoaAeronautEffect copy() {
        return new NaelAvizoaAeronautEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null && DomainValue.REGULAR.calculate(game, source, this) >= 5) {
            controller.drawCards(1, source, game);
            return true;
        }
        return false;
    }
}
