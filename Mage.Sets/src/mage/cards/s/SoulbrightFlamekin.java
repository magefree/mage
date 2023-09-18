package mage.cards.s;

import mage.MageInt;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.IfAbilityHasResolvedXTimesEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.hint.common.AbilityResolutionCountHint;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;
import mage.watchers.common.AbilityResolvedWatcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SoulbrightFlamekin extends CardImpl {

    public SoulbrightFlamekin(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");
        this.subtype.add(SubType.ELEMENTAL);
        this.subtype.add(SubType.SHAMAN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // {2}: Target creature gains trample until end of turn. If this is the third time this ability has resolved this turn, you may add {R}{R}{R}{R}{R}{R}{R}{R}.
        Ability ability = new SimpleActivatedAbility(new GainAbilityTargetEffect(
                TrampleAbility.getInstance(), Duration.EndOfTurn
        ), new ManaCostsImpl<>("{2}"));
        ability.addEffect(new IfAbilityHasResolvedXTimesEffect(Outcome.PutManaInPool, 3, new SoulbrightFlamekinEffect()));
        ability.addTarget(new TargetCreaturePermanent());
        ability.addHint(AbilityResolutionCountHint.instance);
        this.addAbility(ability, new AbilityResolvedWatcher());
    }

    private SoulbrightFlamekin(final SoulbrightFlamekin card) {
        super(card);
    }

    @Override
    public SoulbrightFlamekin copy() {
        return new SoulbrightFlamekin(this);
    }
}

class SoulbrightFlamekinEffect extends OneShotEffect {

    SoulbrightFlamekinEffect() {
        super(Outcome.Damage);
        this.staticText = "you may add {R}{R}{R}{R}{R}{R}{R}{R}";
    }

    private SoulbrightFlamekinEffect(final SoulbrightFlamekinEffect effect) {
        super(effect);
    }

    @Override
    public SoulbrightFlamekinEffect copy() {
        return new SoulbrightFlamekinEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null && controller.chooseUse(Outcome.PutManaInPool, "Add {R}{R}{R}{R}{R}{R}{R}{R}?", source, game)) {
            controller.getManaPool().addMana(Mana.RedMana(8), game, source);
            return true;
        }
        return false;
    }
}
