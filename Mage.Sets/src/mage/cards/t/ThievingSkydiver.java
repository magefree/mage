package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.KickedCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.KickerAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetArtifactPermanent;
import mage.target.targetadjustment.XManaValueTargetAdjuster;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ThievingSkydiver extends CardImpl {

    public ThievingSkydiver(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}");

        this.subtype.add(SubType.MERFOLK);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Kicker {X}. X can't be 0.
        KickerAbility kickerAbility = new KickerAbility("{X}");
        kickerAbility.getKickerCosts().forEach(cost -> {
            cost.setMinimumCost(1);
            cost.setReminderText(". X can't be 0. <i>(You may pay an additional {X} as you cast this spell.)</i>");
        });
        this.addAbility(kickerAbility);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Thieving Skydiver enters the battlefield, if it was kicked, gain control of target artifact with converted mana cost X or less. If that artifact is an Equipment, attach it to Thieving Skydiver.
        Ability ability = new ConditionalInterveningIfTriggeredAbility(
                new EntersBattlefieldTriggeredAbility(new GainControlTargetEffect(Duration.Custom), false),
                KickedCondition.ONCE, "When {this} enters the battlefield, if it was kicked, " +
                "gain control of target artifact with mana value X or less. " +
                "If that artifact is an Equipment, attach it to {this}."
        );
        ability.addEffect(new ThievingSkydiverEffect());
        ability.setTargetAdjuster(new XManaValueTargetAdjuster(ComparisonType.OR_LESS));
        ability.addTarget(new TargetArtifactPermanent());
        this.addAbility(ability);
    }

    private ThievingSkydiver(final ThievingSkydiver card) {
        super(card);
    }

    @Override
    public ThievingSkydiver copy() {
        return new ThievingSkydiver(this);
    }
}

class ThievingSkydiverEffect extends OneShotEffect {

    ThievingSkydiverEffect() {
        super(Outcome.Benefit);
    }

    private ThievingSkydiverEffect(final ThievingSkydiverEffect effect) {
        super(effect);
    }

    @Override
    public ThievingSkydiverEffect copy() {
        return new ThievingSkydiverEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = source.getSourcePermanentIfItStillExists(game);
        Permanent artifact = game.getPermanent(source.getFirstTarget());
        if (permanent == null
                || artifact == null
                || !artifact.isArtifact(game)
                || !artifact.hasSubtype(SubType.EQUIPMENT, game)) {
            return false;
        }
        game.getState().processAction(game);
        permanent.addAttachment(artifact.getId(), source, game);
        return true;
    }
}
