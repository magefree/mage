
package mage.cards.u;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.BandingAbility;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.TrampleAbility;
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
 * @author Styxo & L_J
 */
public final class UrzasAvenger extends CardImpl {

    public UrzasAvenger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT,CardType.CREATURE}, "{6}");

        this.subtype.add(SubType.SHAPESHIFTER);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // {0}: Urza's Avenger gets -1/-1 and gains your choice of banding, flying, first strike, or trample until end of turn.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new UrzasAvengerEffect(), new ManaCostsImpl("{0}")));
    }

    private UrzasAvenger(final UrzasAvenger card) {
        super(card);
    }

    @Override
    public UrzasAvenger copy() {
        return new UrzasAvenger(this);
    }
}

class UrzasAvengerEffect extends ContinuousEffectImpl {

    private static final Set<String> choices = new HashSet<>();
    private Ability gainedAbility;

    static {
        choices.add("Banding");
        choices.add("Flying");
        choices.add("First strike");
        choices.add("Trample");
    }

    public UrzasAvengerEffect() {
        super(Duration.EndOfTurn, Layer.PTChangingEffects_7, SubLayer.ModifyPT_7c, Outcome.AddAbility);
        this.staticText = "{this} gets -1/-1 and gains your choice of banding, flying, first strike, or trample until end of turn";
    }

    private UrzasAvengerEffect(final UrzasAvengerEffect effect) {
        super(effect);
        this.gainedAbility = effect.gainedAbility;
    }

    @Override
    public UrzasAvengerEffect copy() {
        return new UrzasAvengerEffect(this);
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
                    case "Banding":
                        gainedAbility = BandingAbility.getInstance();
                        break;
                    case "Flying":
                        gainedAbility = FlyingAbility.getInstance();
                        break;
                    case "First strike":
                        gainedAbility = FirstStrikeAbility.getInstance();
                        break;
                    default:
                        gainedAbility = TrampleAbility.getInstance();
                        break;
                }
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
