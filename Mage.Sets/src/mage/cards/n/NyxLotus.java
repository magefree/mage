package mage.cards.n;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.dynamicvalue.common.DevotionCount;
import mage.abilities.effects.mana.ManaEffect;
import mage.abilities.mana.ActivatedManaAbilityImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.choices.ChoiceColor;
import mage.constants.CardType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;

/**
 * @author TheElk801
 */
public final class NyxLotus extends CardImpl {

    public NyxLotus(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{4}");

        this.supertype.add(SuperType.LEGENDARY);

        // Nyx Lotus enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());

        // {T}: Choose a color. Add an amount of mana of that color equal to your devotion to that color.
        this.addAbility(new NyxLotusManaAbility());
    }

    private NyxLotus(final NyxLotus card) {
        super(card);
    }

    @Override
    public NyxLotus copy() {
        return new NyxLotus(this);
    }
}

class NyxLotusManaAbility extends ActivatedManaAbilityImpl {

    NyxLotusManaAbility() {
        super(Zone.BATTLEFIELD, new NyxLotusDynamicManaEffect(), new TapSourceCost());
        this.addHint(DevotionCount.W.getHint());
        this.addHint(DevotionCount.U.getHint());
        this.addHint(DevotionCount.B.getHint());
        this.addHint(DevotionCount.R.getHint());
        this.addHint(DevotionCount.G.getHint());
    }

    private NyxLotusManaAbility(final NyxLotusManaAbility ability) {
        super(ability);
    }

    @Override
    public NyxLotusManaAbility copy() {
        return new NyxLotusManaAbility(this);
    }

}

class NyxLotusDynamicManaEffect extends ManaEffect {

    NyxLotusDynamicManaEffect() {
        super();
        this.staticText = "Choose a color. Add an amount of mana of that color equal to your devotion to that color. <i>(Your devotion to a color is the number of mana symbols of that color in the mana costs of permanents you control.)</i>";
    }

    private NyxLotusDynamicManaEffect(final NyxLotusDynamicManaEffect effect) {
        super(effect);
    }

    @Override
    public NyxLotusDynamicManaEffect copy() {
        return new NyxLotusDynamicManaEffect(this);
    }

    @Override
    public List<Mana> getNetMana(Game game, Ability source) {
        if (game != null) {
            return ChoiceColor.getBaseColors()
                    .stream()
                    .map(s -> computeMana(s, game, source))
                    .filter(mana -> mana.count() > 0)
                    .collect(Collectors.toList());
        } else {
            return new ArrayList<>();
        }
    }

    @Override
    public Mana produceMana(Game game, Ability source) {
        Mana mana = new Mana();
        if (game == null) {
            return mana;
        }
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return mana;
        }
        ChoiceColor choice = new ChoiceColor();
        choice.setMessage("Choose a color for devotion of Nyx Lotus");
        if (!controller.choose(outcome, choice, game)) {
            return mana;
        }
        return computeMana(choice.getChoice(), game, source);
    }

    private Mana computeMana(String color, Game game, Ability source) {
        Mana mana = new Mana();
        if (game == null || color == null || color.isEmpty()) {
            return mana;
        }
        switch (color) {
            case "White":
                mana.setWhite(DevotionCount.W.calculate(game, source, this));
                break;
            case "Blue":
                mana.setBlue(DevotionCount.U.calculate(game, source, this));
                break;
            case "Black":
                mana.setBlack(DevotionCount.B.calculate(game, source, this));
                break;
            case "Red":
                mana.setRed(DevotionCount.R.calculate(game, source, this));
                break;
            case "Green":
                mana.setGreen(DevotionCount.G.calculate(game, source, this));
                break;
        }
        return mana;
    }
}
