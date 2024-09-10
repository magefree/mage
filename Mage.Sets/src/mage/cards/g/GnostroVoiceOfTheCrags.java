package mage.cards.g;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;
import mage.watchers.common.CastSpellLastTurnWatcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GnostroVoiceOfTheCrags extends CardImpl {

    public GnostroVoiceOfTheCrags(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}{R}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.CHIMERA);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // {T}: Choose one. X is the number of spells you've cast this turn.
        // • Scry X.
        Ability ability = new SimpleActivatedAbility(new GnostroVoiceOfTheCragsEffect(), new TapSourceCost());
        ability.getModes().setChooseText("choose one. X is the number of spells you've cast this turn.");

        // • Gnostro, Voice of the Crags deals X damage to target creature.
        Mode mode = new Mode(new DamageTargetEffect(GnostroVoiceOfTheCragsValue.instance)
                .setText("{this} deals X damage to target creature"));
        mode.addTarget(new TargetCreaturePermanent());
        ability.addMode(mode);

        // • You gain X life.
        ability.addMode(new Mode(new GainLifeEffect(GnostroVoiceOfTheCragsValue.instance).setText("you gain X life")));
        this.addAbility(ability);
    }

    private GnostroVoiceOfTheCrags(final GnostroVoiceOfTheCrags card) {
        super(card);
    }

    @Override
    public GnostroVoiceOfTheCrags copy() {
        return new GnostroVoiceOfTheCrags(this);
    }
}

enum GnostroVoiceOfTheCragsValue implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        CastSpellLastTurnWatcher watcher = game.getState().getWatcher(CastSpellLastTurnWatcher.class);
        return watcher == null ? 0 : watcher.getAmountOfSpellsPlayerCastOnCurrentTurn(sourceAbility.getControllerId());
    }

    @Override
    public GnostroVoiceOfTheCragsValue copy() {
        return instance;
    }

    @Override
    public String getMessage() {
        return "";
    }
}

class GnostroVoiceOfTheCragsEffect extends OneShotEffect {

    GnostroVoiceOfTheCragsEffect() {
        super(Outcome.Benefit);
        staticText = "scry X";
    }

    private GnostroVoiceOfTheCragsEffect(final GnostroVoiceOfTheCragsEffect effect) {
        super(effect);
    }

    @Override
    public GnostroVoiceOfTheCragsEffect copy() {
        return new GnostroVoiceOfTheCragsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        return player.scry (
                GnostroVoiceOfTheCragsValue.instance.calculate(game, source, this), source, game
        );
    }
}
