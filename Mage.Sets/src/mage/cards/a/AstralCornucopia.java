package mage.cards.a;

import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.EntersBattlefieldWithXCountersEffect;
import mage.abilities.effects.mana.ManaEffect;
import mage.abilities.mana.SimpleManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.choices.ChoiceColor;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author LevelX2
 */
public final class AstralCornucopia extends CardImpl {

    public AstralCornucopia(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{X}{X}{X}");

        // Astral Cornucopia enters the battlefield with X charge counters on it.
        this.addAbility(new EntersBattlefieldAbility(new EntersBattlefieldWithXCountersEffect(CounterType.CHARGE.createInstance())));

        // {T}: Choose a color. Add one mana of that color for each charge counter on Astral Cornucopia.
        this.addAbility(new SimpleManaAbility(Zone.BATTLEFIELD, new AstralCornucopiaManaEffect(), new TapSourceCost()));
    }

    private AstralCornucopia(final AstralCornucopia card) {
        super(card);
    }

    @Override
    public AstralCornucopia copy() {
        return new AstralCornucopia(this);
    }
}

class AstralCornucopiaManaEffect extends ManaEffect {

    private final Mana computedMana;

    public AstralCornucopiaManaEffect() {
        super();
        computedMana = new Mana();
        this.staticText = "Choose a color. Add one mana of that color for each charge counter on {this}";
    }

    public AstralCornucopiaManaEffect(final AstralCornucopiaManaEffect effect) {
        super(effect);
        this.computedMana = effect.computedMana.copy();
    }

    @Override
    public AstralCornucopiaManaEffect copy() {
        return new AstralCornucopiaManaEffect(this);
    }

    @Override
    public List<Mana> getNetMana(Game game, Ability source) {
        List<Mana> netMana = new ArrayList<>();
        if (game != null) {
            Permanent sourcePermanent = game.getPermanent(source.getSourceId());
            if (sourcePermanent != null) {
                int counters = sourcePermanent.getCounters(game).getCount(CounterType.CHARGE.getName());
                if (counters > 0) {
                    netMana.add(Mana.AnyMana(counters));
                }
            }
        }
        return netMana;
    }

    @Override
    public Mana produceMana(Game game, Ability source) {
        Mana mana = new Mana();
        if (game == null) {
            return mana;
        }
        Permanent sourcePermanent = game.getPermanent(source.getSourceId());
        if (sourcePermanent != null) {
            int counters = sourcePermanent.getCounters(game).getCount(CounterType.CHARGE.getName());
            if (counters > 0) {
                Player controller = game.getPlayer(source.getControllerId());
                if (controller != null) {
                    ChoiceColor choice = new ChoiceColor();
                    choice.setMessage("Choose a color to add mana of that color");
                    if (controller.choose(outcome, choice, game)) {
                        if (choice.getChoice() != null) {
                            String color = choice.getChoice();
                            switch (color) {
                                case "Red":
                                    mana.setRed(counters);
                                    break;
                                case "Blue":
                                    mana.setBlue(counters);
                                    break;
                                case "White":
                                    mana.setWhite(counters);
                                    break;
                                case "Black":
                                    mana.setBlack(counters);
                                    break;
                                case "Green":
                                    mana.setGreen(counters);
                                    break;
                            }
                        }
                    }
                }
            }
        }

        return mana;
    }

}
