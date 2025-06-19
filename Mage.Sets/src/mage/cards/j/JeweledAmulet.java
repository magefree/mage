package mage.cards.j;

import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.SourceHasCounterCondition;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.effects.mana.ManaEffect;
import mage.abilities.mana.SimpleManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class JeweledAmulet extends CardImpl {

    private static final Condition condition = new SourceHasCounterCondition(CounterType.CHARGE, ComparisonType.EQUAL_TO, 0);

    public JeweledAmulet(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{0}");

        // {1}, {tap}: Put a charge counter on Jeweled Amulet. Note the type of mana spent to pay this activation cost. Activate this ability only if there are no charge counters on Jeweled Amulet.
        Ability ability = new ActivateIfConditionActivatedAbility(
                new AddCountersSourceEffect(CounterType.CHARGE.createInstance(), true), new GenericManaCost(1), condition
        );
        ability.addEffect(new JeweledAmuletAddCounterEffect());
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);

        // {tap}, Remove a charge counter from Jeweled Amulet: Add one mana of Jeweled Amulet's last noted type.
        Ability ability2 = new SimpleManaAbility(Zone.BATTLEFIELD, new JeweledAmuletAddManaEffect(), new TapSourceCost());
        ability2.addCost(new RemoveCountersSourceCost(CounterType.CHARGE.createInstance()));
        this.addAbility(ability2);
    }

    private JeweledAmulet(final JeweledAmulet card) {
        super(card);
    }

    @Override
    public JeweledAmulet copy() {
        return new JeweledAmulet(this);
    }
}

class JeweledAmuletAddCounterEffect extends OneShotEffect {

    private String manaUsedString;

    public JeweledAmuletAddCounterEffect() {
        super(Outcome.Benefit);
        this.staticText = "Note the type of mana spent to pay this activation cost";
    }

    private JeweledAmuletAddCounterEffect(final JeweledAmuletAddCounterEffect effect) {
        super(effect);
        manaUsedString = effect.manaUsedString;
    }

    @Override
    public JeweledAmuletAddCounterEffect copy() {
        return new JeweledAmuletAddCounterEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent jeweledAmulet = game.getPermanent(source.getSourceId());
        if (controller != null
                && jeweledAmulet != null) {
            game.getState().setValue("JeweledAmulet" + source.getSourceId().toString(), source.getManaCostsToPay().getUsedManaToPay()); //store the mana used to pay
            manaUsedString = source.getManaCostsToPay().getUsedManaToPay().toString();
            jeweledAmulet.addInfo("MANA USED", CardUtil.addToolTipMarkTags("Mana used last: " + manaUsedString), game);
            return true;
        }
        return false;
    }
}

class JeweledAmuletAddManaEffect extends ManaEffect {

    private Mana storedMana;

    JeweledAmuletAddManaEffect() {
        super();
        staticText = "Add one mana of {this}'s last noted type";
    }

    private JeweledAmuletAddManaEffect(final JeweledAmuletAddManaEffect effect) {
        super(effect);
        storedMana = effect.storedMana == null ? null : effect.storedMana.copy();
    }

    @Override
    public JeweledAmuletAddManaEffect copy() {
        return new JeweledAmuletAddManaEffect(this);
    }

    @Override
    public Mana produceMana(Game game, Ability source) {
        Mana mana = new Mana();
        if (game == null) {
            return mana;
        }
        Permanent jeweledAmulet = game.getPermanent(source.getSourceId());
        Player controller = game.getPlayer(source.getControllerId());
        if (jeweledAmulet != null && controller != null) {
            storedMana = (Mana) game.getState().getValue("JeweledAmulet" + source.getSourceId().toString());
            if (storedMana != null) {
                return storedMana.copy();
            }
        }
        return mana;
    }

}
