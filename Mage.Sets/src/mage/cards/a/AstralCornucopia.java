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
import mage.constants.ManaType;
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

    public AstralCornucopiaManaEffect() {
        super();
        this.staticText = "Choose a color. Add one mana of that color for each charge counter on {this}";
    }

    public AstralCornucopiaManaEffect(final AstralCornucopiaManaEffect effect) {
        super(effect);
    }

    @Override
    public AstralCornucopiaManaEffect copy() {
        return new AstralCornucopiaManaEffect(this);
    }

    @Override
    public List<Mana> getNetMana(Game game, Ability source) {
        List<Mana> netMana = new ArrayList<>();
        if (game == null) {
            return netMana;
        }
        Permanent sourcePermanent = game.getPermanent(source.getSourceId());
        if (sourcePermanent == null) {
            return netMana;
        }

        int counters = sourcePermanent.getCounters(game).getCount(CounterType.CHARGE.getName());
        if (counters > 0) {
            netMana.add(Mana.WhiteMana(counters));
            netMana.add(Mana.BlueMana(counters));
            netMana.add(Mana.BlackMana(counters));
            netMana.add(Mana.GreenMana(counters));
            netMana.add(Mana.RedMana(counters));
        }
        return netMana;
    }

    @Override
    public Mana produceMana(Game game, Ability source) {
        if (game == null) {
            return null;
        }
        Permanent sourcePermanent = game.getPermanent(source.getSourceId());
        Player controller = game.getPlayer(source.getControllerId());
        if (sourcePermanent == null || controller == null) {
            return null;
        }
        int counters = sourcePermanent.getCounters(game).getCount(CounterType.CHARGE.getName());
        if (counters == 0) {
            return null;
        }

        ChoiceColor choice = new ChoiceColor();
        choice.setMessage("Choose a color to add mana of that color");
        if (!controller.choose(outcome, choice, game)) {
            return null;
        }

        ManaType chosenType = ManaType.findByName(choice.getChoice());
        return chosenType == null ? null : new Mana(chosenType, counters);
    }
}
