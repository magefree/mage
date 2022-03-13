package mage.cards.r;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ReturnToBattlefieldUnderOwnerControlTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author NinthWorld
 */
public final class RangeTrooper extends CardImpl {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("Trooper creatures");

    static {
        filter.add(SubType.TROOPER.getPredicate());
    }

    public RangeTrooper(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.TROOPER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Trooper creatures you control have "When this creature enters that battlefield, you may exile target creature. Return that creature to the battlefield at the beginning of the next end step."
        Ability ability = new EntersBattlefieldTriggeredAbility(new RangeTrooperEffect(), true);
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD,
                new GainAbilityControlledEffect(ability, Duration.WhileOnBattlefield, filter, false)));
    }

    private RangeTrooper(final RangeTrooper card) {
        super(card);
    }

    @Override
    public RangeTrooper copy() {
        return new RangeTrooper(this);
    }
}

class RangeTrooperEffect extends OneShotEffect {

    public RangeTrooperEffect() {
        super(Outcome.Detriment);
        staticText = "exile target creature. Return that creature to the battlefield at the beginning of the next end step";
    }

    public RangeTrooperEffect(final RangeTrooperEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        MageObject sourceObject = game.getObject(source);
        if (permanent != null && sourceObject != null) {
            if (permanent.moveToExile(source.getSourceId(), sourceObject.getIdName(), source, game)) {
                Effect effect = new ReturnToBattlefieldUnderOwnerControlTargetEffect(false, false);
                effect.setText("Return that card to the battlefield under its owner's control at the beginning of the next end step");
                effect.setTargetPointer(new FixedTarget(source.getFirstTarget(), game));
                game.addDelayedTriggeredAbility(new AtTheBeginOfNextEndStepDelayedTriggeredAbility(effect), source);
                return true;
            }
        }
        return false;
    }

    @Override
    public RangeTrooperEffect copy() {
        return new RangeTrooperEffect(this);
    }
}