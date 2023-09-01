package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.common.SpellCastOpponentTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterSpell;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.stack.StackObject;
import mage.players.Player;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TalionTheKindlyLord extends CardImpl {

    private static final FilterSpell filter
            = new FilterSpell("a spell with mana value, power, or toughness equal to the chosen number");

    static {
        filter.add(TalionTheKindlyLordPredicate.instance);
    }

    public TalionTheKindlyLord(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.FAERIE);
        this.subtype.add(SubType.NOBLE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // As Talion, the Kindly Lord enters the battlefield, choose a number between 1 and 10.
        this.addAbility(new AsEntersBattlefieldAbility(new TalionTheKindlyLordEffect()));

        // Whenever an opponent casts a spell with mana value, power, or toughness equal to the chosen number, that player loses 2 life and you draw a card.
        Ability ability = new SpellCastOpponentTriggeredAbility(
                Zone.BATTLEFIELD, new LoseLifeTargetEffect(2)
                .setText("that player loses 2 life"),
                filter, false, SetTargetPointer.PLAYER
        );
        ability.addEffect(new DrawCardSourceControllerEffect(1, "you").concatBy("and"));
        this.addAbility(ability);
    }

    private TalionTheKindlyLord(final TalionTheKindlyLord card) {
        super(card);
    }

    @Override
    public TalionTheKindlyLord copy() {
        return new TalionTheKindlyLord(this);
    }
}

enum TalionTheKindlyLordPredicate implements ObjectSourcePlayerPredicate<StackObject> {
    instance;

    @Override
    public boolean apply(ObjectSourcePlayer<StackObject> input, Game game) {
        Object obj = game.getState().getValue(
                "chosenNumber_" + input.getSource().getSourceId()
                        + '_' + input.getSource().getSourceObjectZoneChangeCounter()
        );
        if (obj == null) {
            return false;
        }
        int value = (Integer) obj;
        return input.getObject().getManaValue() == value
                || input.getObject().getPower().getValue() == value
                || input.getObject().getToughness().getValue() == value;
    }
}

class TalionTheKindlyLordEffect extends OneShotEffect {

    TalionTheKindlyLordEffect() {
        super(Outcome.Benefit);
        staticText = "choose a number between 1 and 10";
    }

    private TalionTheKindlyLordEffect(final TalionTheKindlyLordEffect effect) {
        super(effect);
    }

    @Override
    public TalionTheKindlyLordEffect copy() {
        return new TalionTheKindlyLordEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return true;
        }
        int numberChoice = controller.getAmount(1, 10, "Choose a number.", game);
        game.getState().setValue("chosenNumber_" + source.getSourceId()
                + '_' + source.getSourceObjectZoneChangeCounter(), numberChoice);
        Permanent permanent = game.getPermanentEntering(source.getSourceId());
        if (permanent != null) {
            permanent.addInfo("chosen players", "<font color = 'blue'>Chosen Number: " + numberChoice + "</font>", game);
            game.informPlayers(permanent.getLogName() + ", chosen number: " + numberChoice);
        }
        return true;
    }
}
