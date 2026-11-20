package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.LimitedTimesPerTurnActivatedAbility;
import mage.abilities.costs.CostAdjuster;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.dynamicvalue.common.StrangeNewWorldsValue;
import mage.abilities.effects.common.InfoEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.hint.common.StrangeNewWorldsHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.game.Game;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author muz
 */
public final class AstuteTerraformer extends CardImpl {

    public AstuteTerraformer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");
        this.subtype.add(SubType.VULCAN);
        this.subtype.add(SubType.SCIENTIST);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Strange new worlds -- {6}: This creature gets +2/+2 until end of turn. This ability costs {X} less to activate, where X is the number of differently named lands you control. Activate only once each turn.
        Ability ability = new LimitedTimesPerTurnActivatedAbility(
            new BoostSourceEffect(2, 2, Duration.EndOfTurn), new GenericManaCost(6)
        );
        ability.addEffect(new InfoEffect("This ability costs {X} less to activate, "
            + "where X is the number of differently named lands you control"));
        ability.setCostAdjuster(AstuteTerraformerAdjuster.instance);
        ability.setAbilityWord(AbilityWord.STRANGE_NEW_WORLDS);
        this.addAbility(ability.addHint(StrangeNewWorldsHint.instance));
    }

    private AstuteTerraformer(final AstuteTerraformer card) {
        super(card);
    }

    @Override
    public AstuteTerraformer copy() {
        return new AstuteTerraformer(this);
    }
}

enum AstuteTerraformerAdjuster implements CostAdjuster {
    instance;

    @Override
    public void reduceCost(Ability ability, Game game) {
        CardUtil.reduceCost(ability, StrangeNewWorldsValue.instance.calculate(game, ability, null));
    }
}
