
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.MorphAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.choices.Choice;
import mage.choices.ChoiceImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author jeffwadsworth
 */
public final class ShorecrasherElemental extends CardImpl {

    public ShorecrasherElemental(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{U}{U}{U}");
        this.subtype.add(SubType.ELEMENTAL);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // {U}: Exile Shorecrasher Elemental, then return it to the battlefield face down under its owner's control.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new ShorecrasherElementalEffect(), new ManaCostsImpl<>("{U}")));

        // {1}: Shorecrasher Elemental gets +1/-1 or -1/+1 until end of turn.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new ShorecrasherElementalBoostEffect(), new ManaCostsImpl<>("{1}")));

        // Megamorph {4}{U}
        this.addAbility(new MorphAbility(new ManaCostsImpl<>("{4}{U}"), true));

    }

    private ShorecrasherElemental(final ShorecrasherElemental card) {
        super(card);
    }

    @Override
    public ShorecrasherElemental copy() {
        return new ShorecrasherElemental(this);
    }
}

class ShorecrasherElementalEffect extends OneShotEffect {

    public ShorecrasherElementalEffect() {
        super(Outcome.Neutral);
        this.staticText = "Exile {this}, then return it to the battlefield face down under its owner's control";
    }

    public ShorecrasherElementalEffect(final ShorecrasherElementalEffect effect) {
        super(effect);
    }

    @Override
    public ShorecrasherElementalEffect copy() {
        return new ShorecrasherElementalEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent shorecrasherElemental = game.getPermanent(source.getSourceId());
        MageObject sourceObject = source.getSourceObject(game);
        if (controller != null && shorecrasherElemental != null && sourceObject != null
                && new MageObjectReference(sourceObject, game).refersTo(shorecrasherElemental, game)) {
            if (controller.moveCards(shorecrasherElemental, Zone.EXILED, source, game)) {
                Card card = game.getExile().getCard(source.getSourceId(), game);
                if (card != null) {
                    controller.moveCards(card, Zone.BATTLEFIELD, source, game, false, true, true, null);
                }
            }
            return true;
        }
        return false;
    }
}

class ShorecrasherElementalBoostEffect extends OneShotEffect {

    private static String CHOICE_1 = "+1/-1";
    private static String CHOICE_2 = "-1/+1";

    public ShorecrasherElementalBoostEffect() {
        super(Outcome.BoostCreature);
        this.staticText = "{this} gets +1/-1 or -1/+1 until end of turn";
    }

    public ShorecrasherElementalBoostEffect(final ShorecrasherElementalBoostEffect effect) {
        super(effect);
    }

    @Override
    public ShorecrasherElementalBoostEffect copy() {
        return new ShorecrasherElementalBoostEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Choice choice = new ChoiceImpl(true);
            choice.setMessage("Select how to boost");
            choice.getChoices().add(CHOICE_1);
            choice.getChoices().add(CHOICE_2);
            if (controller.choose(outcome, choice, game)) {
                if (choice.getChoice().equals(CHOICE_1)) {
                    game.addEffect(new BoostSourceEffect(+1, -1, Duration.EndOfTurn), source);
                } else {
                    game.addEffect(new BoostSourceEffect(-1, +1, Duration.EndOfTurn), source);
                }
                return true;
            }
        }
        return false;
    }
}
