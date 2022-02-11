package mage.cards.f;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.game.Game;
import mage.game.permanent.token.TreasureToken;
import mage.target.common.TargetCreaturePermanent;
import mage.watchers.common.ManaPaidSourceWatcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ForswornPaladin extends CardImpl {

    public ForswornPaladin(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{B}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Menace
        this.addAbility(new MenaceAbility(false));

        // {1}{B}, {T}, Pay 1 life: Create a Treasure token.
        Ability ability = new SimpleActivatedAbility(new CreateTokenEffect(new TreasureToken()), new ManaCostsImpl<>("{1}{B}"));
        ability.addCost(new TapSourceCost());
        ability.addCost(new PayLifeCost(1));
        this.addAbility(ability);

        // {2}{B}: Target creature gets +2/+0 until end of turn. If mana from a Treasure was spent to activate this ability, that creature also gains deathtouch until end of turn.
        ability = new SimpleActivatedAbility(
                new BoostTargetEffect(2, 0), new ManaCostsImpl<>("{2}{B}")
        );
        ability.addEffect(new ForswornPaladinEffect());
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private ForswornPaladin(final ForswornPaladin card) {
        super(card);
    }

    @Override
    public ForswornPaladin copy() {
        return new ForswornPaladin(this);
    }
}

class ForswornPaladinEffect extends OneShotEffect {

    ForswornPaladinEffect() {
        super(Outcome.Benefit);
        staticText = "If mana from a Treasure was spent to activate this ability, " +
                "that creature also gains deathtouch until end of turn.";
    }

    private ForswornPaladinEffect(final ForswornPaladinEffect effect) {
        super(effect);
    }

    @Override
    public ForswornPaladinEffect copy() {
        return new ForswornPaladinEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        if (ManaPaidSourceWatcher.getTreasurePaid(source.getId(), game) > 0) {
            game.addEffect(new GainAbilityTargetEffect(DeathtouchAbility.getInstance(), Duration.EndOfTurn), source);
        }
        return true;
    }
}
