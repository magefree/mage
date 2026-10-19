package mage.cards.l;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BecomesTargetSourceTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CastSourceTriggeredAbility;
import mage.abilities.effects.common.ExileAndReturnSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.abilities.keyword.SpecializeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.ManaType;
import mage.constants.Outcome;
import mage.constants.PutCards;
import mage.constants.SetTargetPointer;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.target.targetpointer.FixedTarget;

import java.util.EnumMap;
import java.util.Map;
import java.util.UUID;

public final class LaezelGithyankiWarrior extends CardImpl {

    private static final Map<ManaType, String> forms = new EnumMap<>(ManaType.class);

    static {
        forms.put(ManaType.WHITE, "Lae'zel, Blessed Warrior");
        forms.put(ManaType.BLUE, "Lae'zel, Illithid Thrall");
        forms.put(ManaType.BLACK, "Lae'zel, Callous Warrior");
        forms.put(ManaType.RED, "Lae'zel, Wrathful Warrior");
        forms.put(ManaType.GREEN, "Lae'zel, Primal Warrior");
    }

    public LaezelGithyankiWarrior(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.GITH);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        this.addAbility(DoubleStrikeAbility.getInstance());
        this.addAbility(new SpecializeAbility("{1}", forms));
        this.addAbility(new CastSourceTriggeredAbility(new LaezelGithyankiWarriorShieldEffect()));
    }

    private LaezelGithyankiWarrior(final LaezelGithyankiWarrior card) {
        super(card);
    }

    @Override
    public LaezelGithyankiWarrior copy() {
        return new LaezelGithyankiWarrior(this);
    }
}

class LaezelGithyankiWarriorShieldEffect extends OneShotEffect {

    LaezelGithyankiWarriorShieldEffect() {
        super(Outcome.Benefit);
        staticText = "it gains \"When this creature becomes the target of a spell or ability an opponent controls, you may exile this creature, then return it to the battlefield under its owner's control.\"";
    }

    private LaezelGithyankiWarriorShieldEffect(final LaezelGithyankiWarriorShieldEffect effect) {
        super(effect);
    }

    @Override
    public LaezelGithyankiWarriorShieldEffect copy() {
        return new LaezelGithyankiWarriorShieldEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Ability shield = new BecomesTargetSourceTriggeredAbility(new ExileAndReturnSourceEffect(PutCards.BATTLEFIELD),
                StaticFilters.FILTER_SPELL_OR_ABILITY_OPPONENTS, SetTargetPointer.NONE, true);
        game.addEffect(new GainAbilityTargetEffect(shield, Duration.Custom, null, true)
                .setTargetPointer(new FixedTarget(source.getSourceId(), game)), source);
        return true;
    }
}
