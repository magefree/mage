package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.ActivateOncePerGameActivatedAbility;
import mage.abilities.common.DealtDamageToSourceTriggeredAbility;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.dynamicvalue.common.SavedDamageValue;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.SacrificeControllerEffect;
import mage.abilities.effects.common.continuous.AddCardSubTypeSourceEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.LifelinkAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 * @author Svyatoslav28
 */
public final class SubversiveAcolyte extends CardImpl {

    public SubversiveAcolyte(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");

        this.subtype.add(SubType.HUMAN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // {2}: Pay 2 life: Choose one. Activate only once.
        // *  Subversive Acolyte becomes a Human Cleric. It gets +1/+1 and gains lifelink.
        Ability ability = new ActivateOncePerGameActivatedAbility(Zone.BATTLEFIELD, new AddCardSubTypeSourceEffect(Duration.WhileOnBattlefield, SubType.HUMAN, SubType.CLERIC).setText("{this} becomes a Human Cleric"),
                new GenericManaCost(2), TimingRule.INSTANT);
        ability.addEffect(new BoostSourceEffect(1, 1, Duration.WhileOnBattlefield).setText("It gains +1/+1"));
        ability.addEffect(new GainAbilitySourceEffect(LifelinkAbility.getInstance(), Duration.WhileOnBattlefield).setText(" and gains lifelink"));
        ability.addCost(new PayLifeCost(2));

        // * Subversive Acolyte becomes a Phyrexian. It gets +3/+2 and gains trample and “Whenever this creature is dealt damage, sacrifice that many permanents.”
        ability.addMode(new Mode(new PhyrexianModeEffect()));
        this.addAbility(ability);
    }

    private SubversiveAcolyte(final SubversiveAcolyte card) {
        super(card);
    }

    @Override
    public SubversiveAcolyte copy() {
        return new SubversiveAcolyte(this);
    }
}

class PhyrexianModeEffect extends OneShotEffect {

    PhyrexianModeEffect() {
        super(Outcome.Benefit);
        staticText = "{this} becomes a Phyrexian. It gets +3/+2 " +
                "and gains trample and \"Whenever this creature is dealt damage, sacrifice that many permanents.\"";
    }

    private PhyrexianModeEffect(final PhyrexianModeEffect effect) {
        super(effect);
    }

    @Override
    public PhyrexianModeEffect copy() {
        return new PhyrexianModeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = source.getSourcePermanentIfItStillExists(game);
        if (permanent == null) {
            return false;
        }
        game.addEffect(new AddCardSubTypeSourceEffect(Duration.WhileOnBattlefield, SubType.PHYREXIAN), source);
        game.addEffect(new BoostSourceEffect(3, 2, Duration.WhileOnBattlefield), source);
        game.addEffect(new GainAbilitySourceEffect(TrampleAbility.getInstance(), Duration.WhileOnBattlefield), source);
        game.addEffect(new GainAbilitySourceEffect(
                new DealtDamageToSourceTriggeredAbility(
                        new SacrificeControllerEffect(StaticFilters.FILTER_PERMANENTS, SavedDamageValue.MANY, ""),false),
                Duration.WhileOnBattlefield), source);
        return true;
    }
}
