
package mage.cards.m;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.choices.ChoiceImpl;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.common.FilterNonlandPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.util.CardUtil;

/**
 *
 * @author L_J
 */
public final class MonkeyMonkeyMonkey extends CardImpl {

    public MonkeyMonkeyMonkey(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{G}");
        this.subtype.add(SubType.MONKEY);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // As Monkey Monkey Monkey enters the battlefield, choose a letter.
        this.addAbility(new AsEntersBattlefieldAbility(new ChooseLetterEffect()));
        
        // Monkey Monkey Monkey gets +1/+1 for each nonland permanent whose name begins with the chosen letter.
        MonkeyMonkeyMonkeyCount count = new MonkeyMonkeyMonkeyCount();
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostSourceEffect(count, count, Duration.WhileOnBattlefield)));

    }

    private MonkeyMonkeyMonkey(final MonkeyMonkeyMonkey card) {
        super(card);
    }

    @Override
    public MonkeyMonkeyMonkey copy() {
        return new MonkeyMonkeyMonkey(this);
    }
}

class ChooseLetterEffect extends OneShotEffect {

    public ChooseLetterEffect() {
        super(Outcome.Benefit);
        staticText = "choose a letter";
    }

    public ChooseLetterEffect(final ChooseLetterEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject mageObject = game.getPermanentEntering(source.getSourceId());
        if (mageObject == null) {
            mageObject = game.getObject(source);
        }

        ChoiceImpl choice = new ChoiceImpl(true);
        choice.setMessage("Choose letter");
        Set<String> choices = new LinkedHashSet<>();
        for (char letter = 'A'; letter <= 'Z'; letter++) {
            choices.add(Character.toString(letter));
        }
        choice.setChoices(choices);

        if (controller != null && mageObject != null && controller.choose(outcome, choice, game)) {
            if (!game.isSimulation()) {
                game.informPlayers(mageObject.getLogName() + ": " + controller.getLogName() + " has chosen " + choice.getChoice());
            }
            game.getState().setValue(mageObject.getId() + "_letter", choice.getChoice());
            if (mageObject instanceof Permanent) {
                ((Permanent) mageObject).addInfo("chosen letter", CardUtil.addToolTipMarkTags("Chosen letter: " + choice.getChoice()), game);
            }
            return true;
        }
        return false;
    }

    @Override
    public ChooseLetterEffect copy() {
        return new ChooseLetterEffect(this);
    }
}

class MonkeyMonkeyMonkeyCount implements DynamicValue {

    public MonkeyMonkeyMonkeyCount() {
    }

    public MonkeyMonkeyMonkeyCount(final MonkeyMonkeyMonkeyCount countersCount) {
    }

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {

        
        MageObject mageObject = game.getObject(sourceAbility.getSourceId());
        if (mageObject instanceof Permanent) {
            Permanent permanent = game.getPermanentOrLKIBattlefield(sourceAbility.getSourceId());
            if (permanent != null && game.getState().getValue(mageObject.getId() + "_letter") != null) {
                int letters = 0;
                for (Permanent p : game.getBattlefield().getActivePermanents(new FilterNonlandPermanent(), sourceAbility.getControllerId(), sourceAbility, game)) {
                    char initial = Character.toUpperCase(p.getName().charAt(0));
                    if (Character.toString(initial).equals(game.getState().getValue(mageObject.getId() + "_letter"))) {
                        letters++;
                    }
                }
                return letters;
            }
        }
        return 0;
    }

    @Override
    public MonkeyMonkeyMonkeyCount copy() {
        return new MonkeyMonkeyMonkeyCount(this);
    }

    @Override
    public String toString() {
        return "1";
    }

    @Override
    public String getMessage() {
        return "nonland permanent whose name begins with the chosen letter";
    }
}
