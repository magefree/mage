package mage.cards.e;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfCombatTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.CompletedDungeonCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.keyword.VentureIntoTheDungeonEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;
import mage.watchers.common.CompletedDungeonWatcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class EccentricApprentice extends CardImpl {

    public EccentricApprentice(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");

        this.subtype.add(SubType.TIEFLING);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Eccentric Apprentice enters the battlefield, venture into the dungeon.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new VentureIntoTheDungeonEffect()));

        // At the beginning of combat on your turn, if you've completed a dungeon, up to one target creature becomes a Bird with base power and toughness 1/1 and flying until end of turn.
        Ability ability = new ConditionalInterveningIfTriggeredAbility(
                new BeginningOfCombatTriggeredAbility(
                        new EccentricApprenticeEffect(), TargetController.YOU, false
                ), CompletedDungeonCondition.instance, "At the beginning of combat on your turn, " +
                "if you've completed a dungeon, up to one target creature becomes a Bird " +
                "with base power and toughness 1/1 and flying until end of turn."
        ).addHint(CompletedDungeonCondition.getHint());
        ability.addTarget(new TargetCreaturePermanent(0, 1));
        this.addAbility(ability, new CompletedDungeonWatcher());
    }

    private EccentricApprentice(final EccentricApprentice card) {
        super(card);
    }

    @Override
    public EccentricApprentice copy() {
        return new EccentricApprentice(this);
    }
}

class EccentricApprenticeEffect extends ContinuousEffectImpl {

    EccentricApprenticeEffect() {
        super(Duration.EndOfTurn, Outcome.Benefit);
    }

    private EccentricApprenticeEffect(final EccentricApprenticeEffect effect) {
        super(effect);
    }

    @Override
    public EccentricApprenticeEffect copy() {
        return new EccentricApprenticeEffect(this);
    }

    @Override
    public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (permanent == null) {
            discard();
            return false;
        }
        switch (layer) {
            case TypeChangingEffects_4:
                permanent.removeAllCreatureTypes(game);
                permanent.removeSubType(game, SubType.BIRD);
                return true;
            case AbilityAddingRemovingEffects_6:
                permanent.addAbility(FlyingAbility.getInstance(), source.getSourceId(), game);
                return true;
            case PTChangingEffects_7:
                if (sublayer == SubLayer.SetPT_7b) {
                    permanent.getPower().setModifiedBaseValue(1);
                    permanent.getToughness().setModifiedBaseValue(1);
                    return true;
                }
        }
        return false;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public boolean hasLayer(Layer layer) {
        switch (layer) {
            case TypeChangingEffects_4:
            case AbilityAddingRemovingEffects_6:
            case PTChangingEffects_7:
                return true;
        }
        return false;
    }
}
