package mage.cards.w;

import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.SacrificeTargetEffect;
import mage.abilities.effects.common.combat.CantBeBlockedTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author LevelX2
 */
public final class WingsOfHubris extends CardImpl {

    public WingsOfHubris(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");

        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature has flying.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GainAbilityAttachedEffect(FlyingAbility.getInstance(), AttachmentType.EQUIPMENT)));

        // Sacrifice Wings of Hubris: Equipped creature can't be blocked this turn. Sacrifice it at the beginning of the next end step.
        this.addAbility(new SimpleActivatedAbility(new WingsOfHubrisEffect(), new SacrificeSourceCost()));

        // Equip {1}
        this.addAbility(new EquipAbility(Outcome.AddAbility, new GenericManaCost(1)));
    }

    private WingsOfHubris(final WingsOfHubris card) {
        super(card);
    }

    @Override
    public WingsOfHubris copy() {
        return new WingsOfHubris(this);
    }
}

class WingsOfHubrisEffect extends OneShotEffect {

    public WingsOfHubrisEffect() {
        super(Outcome.Detriment);
        this.staticText = "Equipped creature can't be blocked this turn. Sacrifice it at the beginning of the next end step";
    }

    private WingsOfHubrisEffect(final WingsOfHubrisEffect effect) {
        super(effect);
    }

    @Override
    public WingsOfHubrisEffect copy() {
        return new WingsOfHubrisEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        MageObject equipment = game.getLastKnownInformation(source.getSourceId(), Zone.BATTLEFIELD);
        if (equipment instanceof Permanent && ((Permanent) equipment).getAttachedTo() != null) {
            Permanent attachedToCreature = game.getPermanent(((Permanent) equipment).getAttachedTo());
            if (attachedToCreature != null) {
                ContinuousEffect effect = new CantBeBlockedTargetEffect(Duration.EndOfTurn);
                effect.setTargetPointer(new FixedTarget(attachedToCreature, game));
                game.addEffect(effect, source);
                SacrificeTargetEffect sacrificeEffect = new SacrificeTargetEffect("sacrifice this", source.getControllerId());
                sacrificeEffect.setTargetPointer(new FixedTarget(attachedToCreature, game));
                DelayedTriggeredAbility delayedAbility = new AtTheBeginOfNextEndStepDelayedTriggeredAbility(sacrificeEffect);
                game.addDelayedTriggeredAbility(delayedAbility, source);
            }
            return true;
        }
        return false;
    }
}
