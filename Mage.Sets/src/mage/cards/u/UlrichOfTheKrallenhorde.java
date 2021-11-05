package mage.cards.u;

import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.WerewolfFrontTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.keyword.TransformAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class UlrichOfTheKrallenhorde extends CardImpl {

    public UlrichOfTheKrallenhorde(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}{G}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WEREWOLF);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);


        this.secondSideCardClazz = UlrichUncontestedAlpha.class;

        // Whenever this creature enters the battlefield or transforms into Ulrich of the Krallenhorde, target creature gets +4/+4 until end of turn.
        this.addAbility(new UlrichOfTheKrallenhordeAbility());

        // At the beginning of each upkeep, if no spells were cast last turn, transform Ulrich of the Krallenhorde.
        this.addAbility(new TransformAbility());
        this.addAbility(new WerewolfFrontTriggeredAbility());
    }

    private UlrichOfTheKrallenhorde(final UlrichOfTheKrallenhorde card) {
        super(card);
    }

    @Override
    public UlrichOfTheKrallenhorde copy() {
        return new UlrichOfTheKrallenhorde(this);
    }
}

class UlrichOfTheKrallenhordeAbility extends TriggeredAbilityImpl {

    public UlrichOfTheKrallenhordeAbility() {
        super(Zone.BATTLEFIELD, new BoostTargetEffect(4, 4, Duration.EndOfTurn), false);
        this.addTarget(new TargetCreaturePermanent());
    }

    public UlrichOfTheKrallenhordeAbility(final UlrichOfTheKrallenhordeAbility ability) {
        super(ability);
    }

    @Override
    public UlrichOfTheKrallenhordeAbility copy() {
        return new UlrichOfTheKrallenhordeAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.TRANSFORMED || event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.TRANSFORMED && event.getTargetId().equals(this.getSourceId())) {
            Permanent permanent = game.getPermanent(sourceId);
            if (permanent != null && !permanent.isTransformed()) {
                return true;
            }
        }
        if (event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD && event.getTargetId().equals(this.getSourceId())) {
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever this creature enters the battlefield or transforms into Ulrich of the Krallenhorde, target creature gets +4/+4 until end of turn.";
    }
}
