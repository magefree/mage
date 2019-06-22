package mage.cards.s;

import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.CantBeCounteredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.ProtectionAbility;
import mage.abilities.keyword.ReachAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.choices.Choice;
import mage.choices.ChoiceImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.game.Game;
import mage.players.Player;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ShiftingCeratops extends CardImpl {

    public ShiftingCeratops(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}{G}");

        this.subtype.add(SubType.DINOSAUR);
        this.power = new MageInt(5);
        this.toughness = new MageInt(4);

        // This spell can't be countered.
        this.addAbility(new CantBeCounteredAbility());

        // Protection from blue
        this.addAbility(ProtectionAbility.from(ObjectColor.BLUE));

        // {G}: Shifting Ceratops gains your choice of reach, trample, or haste until end of turn.
        this.addAbility(new SimpleActivatedAbility(new ShiftingCeratopsEffect(), new ManaCostsImpl("{G}")));
    }

    private ShiftingCeratops(final ShiftingCeratops card) {
        super(card);
    }

    @Override
    public ShiftingCeratops copy() {
        return new ShiftingCeratops(this);
    }
}

class ShiftingCeratopsEffect extends OneShotEffect {
    private static final Set<String> choices = new HashSet();

    static {
        choices.add("Reach");
        choices.add("Trample");
        choices.add("Haste");
    }

    ShiftingCeratopsEffect() {
        super(Outcome.Benefit);
        staticText = "{this} gains your choice of reach, trample, or haste until end of turn.";
    }

    private ShiftingCeratopsEffect(final ShiftingCeratopsEffect effect) {
        super(effect);
    }

    @Override
    public ShiftingCeratopsEffect copy() {
        return new ShiftingCeratopsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Choice choice = new ChoiceImpl(true);
        choice.setMessage("Choose an ability");
        choice.setChoices(choices);
        if (!player.choose(outcome, choice, game)) {
            return false;
        }
        Ability ability = null;
        switch (choice.getChoice()) {
            case "Reach":
                ability = ReachAbility.getInstance();
                break;
            case "Trample":
                ability = TrampleAbility.getInstance();
                break;
            case "Haste":
                ability = HasteAbility.getInstance();
                break;
        }
        if (ability != null) {
            game.addEffect(new GainAbilitySourceEffect(ability, Duration.EndOfTurn), source);
        }
        return true;
    }
}
