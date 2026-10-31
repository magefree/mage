package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.CostAdjuster;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.StrangeNewWorldsValue;
import mage.abilities.effects.common.InfoEffect;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.hint.common.StrangeNewWorldsHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.Game;
import mage.target.common.TargetCreaturePermanent;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author muz
 */
public final class SuusMahnaPractitioner extends CardImpl {

    public SuusMahnaPractitioner(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");
        this.subtype.add(SubType.VULCAN);
        this.subtype.add(SubType.OFFICER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Strange new worlds -- {4}{U}, {T}: Tap target creature. This ability costs {X} less to activate, where X is the number of differently named lands you control.
        Ability ability = new SimpleActivatedAbility(
            new TapTargetEffect(),
            new ManaCostsImpl<>("{4}{U}")
        );
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetCreaturePermanent());
        ability.addEffect(new InfoEffect("This ability costs {X} less to activate, "
            + "where X is the number of differently named lands you control"));
        ability.setCostAdjuster(SuusMahnaPractitionerAdjuster.instance);
        ability.setAbilityWord(AbilityWord.STRANGE_NEW_WORLDS);
        ability.addHint(StrangeNewWorldsHint.instance);
        this.addAbility(ability);
    }

    private SuusMahnaPractitioner(final SuusMahnaPractitioner card) {
        super(card);
    }

    @Override
    public SuusMahnaPractitioner copy() {
        return new SuusMahnaPractitioner(this);
    }
}

enum SuusMahnaPractitionerAdjuster implements CostAdjuster {
    instance;

    @Override
    public void reduceCost(Ability ability, Game game) {
        CardUtil.reduceCost(ability, StrangeNewWorldsValue.instance.calculate(game, ability, null));
    }
}
