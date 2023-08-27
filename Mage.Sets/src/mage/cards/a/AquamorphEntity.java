package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.SelectCopiableCharacteristicsSourceEffect;
import mage.abilities.keyword.MorphAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.EntersTheBattlefieldEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.TokenImpl;

import java.util.UUID;

/**
 * @author xenohedron
 */
public final class AquamorphEntity extends CardImpl {

    public AquamorphEntity(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}{U}");
        this.subtype.add(SubType.SHAPESHIFTER);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // As Aquamorph Entity enters the battlefield or is turned face up, it becomes your choice of 5/1 or 1/5.
        Ability ability = new SimpleStaticAbility(Zone.ALL, new AquamorphEntityReplacementEffect());
        ability.setWorksFaceDown(true);
        this.addAbility(ability);

        // Morph {2}{U}
        this.addAbility(new MorphAbility(new ManaCostsImpl<>("{2}{U}")));
    }

    private AquamorphEntity(final AquamorphEntity card) {
        super(card);
    }

    @Override
    public AquamorphEntity copy() {
        return new AquamorphEntity(this);
    }
}

class AquamorphEntityReplacementEffect extends ReplacementEffectImpl {

    AquamorphEntityReplacementEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "as {this} enters the battlefield or is turned face up, it becomes your choice of 5/1 or 1/5";
    }

    private AquamorphEntityReplacementEffect(AquamorphEntityReplacementEffect effect) {
        super(effect);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        switch (event.getType()) {
            case ENTERS_THE_BATTLEFIELD:
            case TURNFACEUP:
                return true;
            default:
                return false;
        }
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        switch (event.getType()) {
            case ENTERS_THE_BATTLEFIELD:
                if (!event.getTargetId().equals(source.getSourceId())) {
                    return false;
                }
                Permanent sourcePermanent = ((EntersTheBattlefieldEvent) event).getTarget();
                return sourcePermanent != null && !sourcePermanent.isFaceDown(game);
            case TURNFACEUP:
                return event.getTargetId().equals(source.getSourceId());
            default:
                return false;
        }
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Permanent permanent;
        if (event instanceof EntersTheBattlefieldEvent) {
            permanent = ((EntersTheBattlefieldEvent) event).getTarget();
        } else {
            permanent = game.getPermanent(event.getTargetId());
        }
        if (permanent == null) {
            return false;
        }
        new SelectCopiableCharacteristicsSourceEffect(
                new AquamorphEntity51Token(), new AquamorphEntity15Token()
        ).apply(game, source);
        return false;
    }

    @Override
    public AquamorphEntityReplacementEffect copy() {
        return new AquamorphEntityReplacementEffect(this);
    }

}

class AquamorphEntity51Token extends TokenImpl {

    AquamorphEntity51Token() {
        super("", "5/1");
        power = new MageInt(5);
        toughness = new MageInt(1);
    }

    private AquamorphEntity51Token(final AquamorphEntity51Token token) {
        super(token);
    }

    public AquamorphEntity51Token copy() {
        return new AquamorphEntity51Token(this);
    }
}

class AquamorphEntity15Token extends TokenImpl {

    AquamorphEntity15Token() {
        super("", "1/5");
        power = new MageInt(1);
        toughness = new MageInt(5);
    }

    private AquamorphEntity15Token(final AquamorphEntity15Token token) {
        super(token);
    }

    public AquamorphEntity15Token copy() {
        return new AquamorphEntity15Token(this);
    }
}
