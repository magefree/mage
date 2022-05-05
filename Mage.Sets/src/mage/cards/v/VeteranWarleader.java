package mage.cards.v;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.TapTargetCost;
import mage.abilities.dynamicvalue.common.CreaturesYouControlCount;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.effects.common.continuous.SetPowerToughnessSourceEffect;
import mage.abilities.hint.common.CreaturesYouControlHint;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.choices.Choice;
import mage.choices.ChoiceImpl;
import mage.constants.*;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetControlledPermanent;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author fireshoes
 */
public final class VeteranWarleader extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("another untapped Ally you control");

    static {
        filter.add(AnotherPredicate.instance);
        filter.add(SubType.ALLY.getPredicate());
        filter.add(TappedPredicate.UNTAPPED);
    }

    public VeteranWarleader(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.subtype.add(SubType.ALLY);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Veteran Warleader's power and toughness are each equal to the number of creatures you control.
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new SetPowerToughnessSourceEffect(
                CreaturesYouControlCount.instance, Duration.EndOfGame))
                .addHint(CreaturesYouControlHint.instance));

        // Tap another untapped Ally you control: Veteran Warleader gains your choice of first strike, vigilance, or trample until end of turn.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD,
                new VeteranWarleaderEffect(), new TapTargetCost(new TargetControlledPermanent(1, 1, filter, true))));
    }

    private VeteranWarleader(final VeteranWarleader card) {
        super(card);
    }

    @Override
    public VeteranWarleader copy() {
        return new VeteranWarleader(this);
    }
}

class VeteranWarleaderEffect extends OneShotEffect {

    VeteranWarleaderEffect() {
        super(Outcome.AddAbility);
        staticText = "{this} gains your choice of first strike, vigilance, or trample until end of turn";
    }

    VeteranWarleaderEffect(final VeteranWarleaderEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = game.getObject(source);
        if (sourceObject != null && controller != null) {
            Choice abilityChoice = new ChoiceImpl();
            abilityChoice.setMessage("Choose an ability to add");

            Set<String> abilities = new HashSet<>();
            abilities.add(FirstStrikeAbility.getInstance().getRule());
            abilities.add(VigilanceAbility.getInstance().getRule());
            abilities.add(TrampleAbility.getInstance().getRule());
            abilityChoice.setChoices(abilities);
            if (!controller.choose(Outcome.AddAbility, abilityChoice, game)) {
                return false;
            }

            String chosen = abilityChoice.getChoice();
            Ability ability = null;
            if (FirstStrikeAbility.getInstance().getRule().equals(chosen)) {
                ability = FirstStrikeAbility.getInstance();
            } else if (VigilanceAbility.getInstance().getRule().equals(chosen)) {
                ability = VigilanceAbility.getInstance();
            } else if (TrampleAbility.getInstance().getRule().equals(chosen)) {
                ability = TrampleAbility.getInstance();
            }

            if (ability != null) {
                game.informPlayers(sourceObject.getLogName() + ": " + controller.getLogName() + " has chosen: " + chosen);
                ContinuousEffect effect = new GainAbilitySourceEffect(ability, Duration.EndOfTurn);
                game.addEffect(effect, source);
                return true;
            }
        }
        return false;
    }

    @Override
    public VeteranWarleaderEffect copy() {
        return new VeteranWarleaderEffect(this);
    }

}
