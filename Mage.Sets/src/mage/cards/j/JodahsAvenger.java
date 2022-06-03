
package mage.cards.j;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.abilities.keyword.ProtectionAbility;
import mage.abilities.keyword.ShadowAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.choices.Choice;
import mage.choices.ChoiceImpl;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author Styxo
 */
public final class JodahsAvenger extends CardImpl {

    public JodahsAvenger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{U}");

        this.subtype.add(SubType.SHAPESHIFTER);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // {0}: Until end of turn, Jodah's Avenger gets -1/-1 and gains your choice of double strike, protection from red, vigilance, or shadow.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new JodahsAvengerEffect(), new ManaCostsImpl<>("{0}")));
    }

    private JodahsAvenger(final JodahsAvenger card) {
        super(card);
    }

    @Override
    public JodahsAvenger copy() {
        return new JodahsAvenger(this);
    }
}

class JodahsAvengerEffect extends ContinuousEffectImpl {

    private static final Set<String> choices = new HashSet<>();
    private Ability gainedAbility;

    static {
        choices.add("Double strike");
        choices.add("Protection from red");
        choices.add("Vigilance");
        choices.add("Shadow");
    }

    public JodahsAvengerEffect() {
        super(Duration.EndOfTurn, Layer.PTChangingEffects_7, SubLayer.ModifyPT_7c, Outcome.AddAbility);
        this.staticText = "Until end of turn, {this} gets -1/-1 and gains your choice of double strike, protection from red, vigilance, or shadow";
    }

    public JodahsAvengerEffect(final JodahsAvengerEffect effect) {
        super(effect);
    }

    @Override
    public JodahsAvengerEffect copy() {
        return new JodahsAvengerEffect(this);
    }

    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Choice choice = new ChoiceImpl(true);
            choice.setMessage("Choose one");
            choice.setChoices(choices);
            if (controller.choose(outcome, choice, game)) {
                switch (choice.getChoice()) {
                    case "Double strike":
                        gainedAbility = DoubleStrikeAbility.getInstance();
                        break;
                    case "Vigilance":
                        gainedAbility = VigilanceAbility.getInstance();
                        break;
                    case "Shadow":
                        gainedAbility = ShadowAbility.getInstance();
                        break;
                    default:
                        gainedAbility = ProtectionAbility.from(ObjectColor.RED);
                        break;
                }
            } else {
                discard();
            }
        }
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent sourceObject = game.getPermanent(source.getSourceId());
        if (sourceObject != null) {
            sourceObject.addPower(-1);
            sourceObject.addToughness(-1);
            game.addEffect(new GainAbilitySourceEffect(gainedAbility, Duration.EndOfTurn), source);
            return true;
        }
        return false;
    }
}
