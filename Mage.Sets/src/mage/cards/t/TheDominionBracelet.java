package mage.cards.t;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.CostAdjuster;
import mage.abilities.costs.common.ExileAttachmentCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.Effects;
import mage.abilities.effects.common.InfoEffect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.effects.common.continuous.GainAbilityWithAttachmentEffect;
import mage.abilities.effects.common.turn.ControlTargetPlayerNextTurnEffect;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.TimingRule;
import mage.game.Game;
import mage.target.Targets;
import mage.target.common.TargetOpponent;
import mage.util.CardUtil;

import java.util.Optional;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TheDominionBracelet extends CardImpl {

    public TheDominionBracelet(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature gets +1/+1 and has "{15}, Exile The Dominion Bracelet: You control target opponent during their next turn. This ability costs {X} less to activate, where X is this creature's power. Activate only as a sorcery."
        Ability ability = new SimpleStaticAbility(new BoostEquippedEffect(1, 1));
        ability.addEffect(new GainAbilityWithAttachmentEffect(
                "and has \"{15}, Exile {this}: You control target opponent during their next turn. This ability " +
                        "costs {X} less to activate, where X is this creature's power. Activate only as a sorcery.\"",
                new Effects(
                        new ControlTargetPlayerNextTurnEffect(), new InfoEffect("This ability costs {X} " +
                        "less to activate, where X is this creature's power. Activate only as a sorcery")
                ), new Targets(new TargetOpponent()), new ExileAttachmentCost(),
                activatedAbility -> {
                    activatedAbility.setCostAdjuster(TheDominionBraceletAdjuster.instance);
                    activatedAbility.setTiming(TimingRule.SORCERY);
                },
                new GenericManaCost(15)
        ));
        this.addAbility(ability);

        // Equip {1}
        this.addAbility(new EquipAbility(1));
    }

    private TheDominionBracelet(final TheDominionBracelet card) {
        super(card);
    }

    @Override
    public TheDominionBracelet copy() {
        return new TheDominionBracelet(this);
    }
}

enum TheDominionBraceletAdjuster implements CostAdjuster {
    instance;

    @Override
    public void reduceCost(Ability ability, Game game) {
        Optional.ofNullable(ability.getSourcePermanentIfItStillExists(game))
                .map(MageObject::getPower)
                .map(MageInt::getValue)
                .filter(x -> x > 0)
                .ifPresent(x -> CardUtil.reduceCost(ability, x));
    }
}
