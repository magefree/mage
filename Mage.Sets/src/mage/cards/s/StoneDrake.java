package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.effects.common.discard.DiscardTargetEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.Game;
import mage.target.TargetPlayer;
import mage.target.common.TargetLandPermanent;
import mage.watchers.common.CastSpellLastTurnWatcher;

import java.util.UUID;

/**
 * @author Cguy7777
 */
public final class StoneDrake extends CardImpl {

    private static final Hint hint
            = new ValueHint("Spells you've cast this turn", StoneDrakeDynamicValue.instance);

    public StoneDrake(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}{B}");

        this.subtype.add(SubType.DRAKE);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Stone Drake enters the battlefield, choose one —
        //• Distract — Tap target land. Draw a card.
        Ability ability = new EntersBattlefieldTriggeredAbility(new TapTargetEffect());
        ability.addEffect(new DrawCardSourceControllerEffect(1));
        ability.addTarget(new TargetLandPermanent());
        ability.withFirstModeFlavorWord("Distract");

        //• Enthrall — Target player discards a card for each spell you've cast this turn.
        ability.addMode(new Mode(new DiscardTargetEffect(StoneDrakeDynamicValue.instance))
                .addTarget(new TargetPlayer())
                .withFlavorWord("Enthrall"));
        this.addAbility(ability.addHint(hint));
    }

    private StoneDrake(final StoneDrake card) {
        super(card);
    }

    @Override
    public StoneDrake copy() {
        return new StoneDrake(this);
    }
}

enum StoneDrakeDynamicValue implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        return game
                .getState()
                .getWatcher(CastSpellLastTurnWatcher.class)
                .getAmountOfSpellsPlayerCastOnCurrentTurn(sourceAbility.getControllerId());
    }

    @Override
    public StoneDrakeDynamicValue copy() {
        return this;
    }

    @Override
    public String toString() {
        return "1";
    }

    @Override
    public String getMessage() {
        return "spell you've cast this turn";
    }
}
