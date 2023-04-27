package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ChooseCreatureEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;
import mage.util.CardUtil;

/**
 *
 * @author weirddan455
 */
public final class DauntlessBodyguard extends CardImpl {

    public DauntlessBodyguard(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // As Dauntless Bodyguard enters the battlefield, choose another creature you control.
        this.addAbility(new AsEntersBattlefieldAbility(new ChooseCreatureEffect()));

        // Sacrifice Dauntless Bodyguard: The chosen creature gains indestructible until end of turn.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new DauntlessBodyguardGainAbilityEffect(), new SacrificeSourceCost()));
    }

    private DauntlessBodyguard(final DauntlessBodyguard card) {
        super(card);
    }

    @Override
    public DauntlessBodyguard copy() {
        return new DauntlessBodyguard(this);
    }
}

class DauntlessBodyguardGainAbilityEffect extends OneShotEffect {

    public DauntlessBodyguardGainAbilityEffect() {
        super(Outcome.AddAbility);
        this.staticText = "The chosen creature gains indestructible until end of turn";
    }

    public DauntlessBodyguardGainAbilityEffect(final DauntlessBodyguardGainAbilityEffect effect) {
        super(effect);
    }

    @Override
    public DauntlessBodyguardGainAbilityEffect copy() {
        return new DauntlessBodyguardGainAbilityEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Object chosenCreature = game.getState().getValue(CardUtil.getCardZoneString("chosenCreature", source.getSourceId(), game, true));
        if (!(chosenCreature instanceof MageObjectReference)) {
            return false;
        }
        Permanent permanent = ((MageObjectReference) chosenCreature).getPermanent(game);
        if (permanent == null) {
            return false;
        }
        ContinuousEffect effect = new GainAbilityTargetEffect(IndestructibleAbility.getInstance(), Duration.EndOfTurn);
        effect.setTargetPointer(new FixedTarget(permanent, game));
        game.addEffect(effect, source);
        return true;
    }
}
