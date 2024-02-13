package mage.cards.n;

import java.util.Objects;
import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.DrawCardTargetEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.keyword.HexproofFromMulticoloredAbility;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPlayer;
import mage.target.common.TargetAnyTarget;
import mage.target.targetpointer.SecondTargetPointer;

/**
 *
 * @author DominionSpy
 */
public final class NivMizzetGuildpact extends CardImpl {

    public NivMizzetGuildpact(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{W}{U}{B}{R}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.DRAGON);
        this.subtype.add(SubType.AVATAR);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Hexproof from multicolored
        this.addAbility(HexproofFromMulticoloredAbility.getInstance());

        // Whenever Niv-Mizzet, Guildpact deals combat damage to a player,
        // it deals X damage to any target, target player draws X cards, and you gain X life,
        // where X is the number of different color pairs among permanents you control that are exactly two colors.
        Ability ability = new DealsCombatDamageToAPlayerTriggeredAbility(
                new DamageTargetEffect(NivMizzetGuildpactCount.instance)
                        .setText("it deals X damage to any target"), false);
        ability.addTarget(new TargetAnyTarget());
        ability.addEffect(new DrawCardTargetEffect(NivMizzetGuildpactCount.instance)
                .setTargetPointer(new SecondTargetPointer())
                .setText(", target player draws X cards"));
        ability.addTarget(new TargetPlayer());
        ability.addEffect(new GainLifeEffect(NivMizzetGuildpactCount.instance)
                .setText(", and you gain X life, where X is the number of different color pairs " +
                        "among permanents you control that are exactly two colors."));

        this.addAbility(ability);
    }

    private NivMizzetGuildpact(final NivMizzetGuildpact card) {
        super(card);
    }

    @Override
    public NivMizzetGuildpact copy() {
        return new NivMizzetGuildpact(this);
    }
}

enum NivMizzetGuildpactCount implements DynamicValue {
    instance;

    @Override
    public NivMizzetGuildpactCount copy() {
        return instance;
    }

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        return (int) game.getBattlefield()
                .getAllActivePermanents(
                        StaticFilters.FILTER_CONTROLLED_PERMANENT,
                        sourceAbility.getControllerId(), game)
                .stream()
                .filter(Objects::nonNull)
                .map(Permanent::getColor)
                .filter(color -> color.getColorCount() == 2)
                .distinct()
                .count();
    }

    @Override
    public String toString() {
        return "X";
    }

    @Override
    public String getMessage() {
        return "the number of different color pairs among permanents you control that are exactly two colors";
    }
}
