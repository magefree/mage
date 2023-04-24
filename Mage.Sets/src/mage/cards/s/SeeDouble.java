package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.CardsInOpponentGraveyardCondition;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.CopyTargetSpellEffect;
import mage.abilities.effects.common.CreateTokenCopyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.target.TargetSpell;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SeeDouble extends CardImpl {

    public SeeDouble(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{U}{U}");

        // This spell can't be copied.
        this.addAbility(new SimpleStaticAbility(Zone.STACK, new SeeDoubleEffect()).setRuleAtTheTop(true));

        // Choose one. If an opponent has eight or more cards in their graveyard, you may choose both.
        this.getSpellAbility().getModes().setChooseText(
                "Choose one. If an opponent has eight or more cards in their graveyard, you may choose both."
        );
        this.getSpellAbility().getModes().setMoreCondition(CardsInOpponentGraveyardCondition.EIGHT);
        this.getSpellAbility().addHint(CardsInOpponentGraveyardCondition.EIGHT.getHint());

        // * Copy target spell. You may choose new targets for the copy.
        this.getSpellAbility().addEffect(new CopyTargetSpellEffect());
        this.getSpellAbility().addTarget(new TargetSpell());

        // * Create a token that's a copy of target creature.
        this.getSpellAbility().addMode(new Mode(new CreateTokenCopyTargetEffect())
                .addTarget(new TargetCreaturePermanent()));
    }

    private SeeDouble(final SeeDouble card) {
        super(card);
    }

    @Override
    public SeeDouble copy() {
        return new SeeDouble(this);
    }
}

class SeeDoubleEffect extends ReplacementEffectImpl {

    SeeDoubleEffect() {
        super(Duration.WhileOnStack, Outcome.Benefit);
        staticText = "this spell can't be copied";
    }

    private SeeDoubleEffect(final SeeDoubleEffect effect) {
        super(effect);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        return true;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.COPY_STACKOBJECT;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return event.getTargetId().equals(source.getSourceId());
    }

    @Override
    public SeeDoubleEffect copy() {
        return new SeeDoubleEffect(this);
    }
}