package mage.cards.v;

import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.cards.AdventureCard;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.ManaType;
import mage.constants.Outcome;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ManaEvent;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCardInYourGraveyard;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class VirtueOfStrength extends AdventureCard {

    private static final FilterCard filter = new FilterCard("creature or land card from your graveyard");

    static {
        filter.add(Predicates.or(
                CardType.CREATURE.getPredicate(),
                CardType.LAND.getPredicate()
        ));
    }

    public VirtueOfStrength(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, new CardType[]{CardType.SORCERY}, "{5}{G}{G}", "Garenbrig Growth", "{G}");

        // If you tap a basic land for mana, it produces three times as much of that mana instead.
        this.addAbility(new SimpleStaticAbility(new VirtueOfStrengthReplacementEffect()));

        // Garenbrig Growth
        // Return target creature or land card from your graveyard to your hand.
        this.getSpellCard().getSpellAbility().addEffect(new ReturnFromGraveyardToHandTargetEffect());
        this.getSpellCard().getSpellAbility().addTarget(new TargetCardInYourGraveyard(filter));

        this.finalizeAdventure();
    }

    private VirtueOfStrength(final VirtueOfStrength card) {
        super(card);
    }

    @Override
    public VirtueOfStrength copy() {
        return new VirtueOfStrength(this);
    }
}

class VirtueOfStrengthReplacementEffect extends ReplacementEffectImpl {

    VirtueOfStrengthReplacementEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "if you tap a basic land for mana, it produces three times as much of that mana instead";
    }

    private VirtueOfStrengthReplacementEffect(VirtueOfStrengthReplacementEffect effect) {
        super(effect);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Mana mana = ((ManaEvent) event).getMana();
        if (mana.getBlack() > 0) {
            mana.set(ManaType.BLACK, CardUtil.overflowMultiply(mana.getBlack(), 3));
        }
        if (mana.getBlue() > 0) {
            mana.set(ManaType.BLUE, CardUtil.overflowMultiply(mana.getBlue(), 3));
        }
        if (mana.getWhite() > 0) {
            mana.set(ManaType.WHITE, CardUtil.overflowMultiply(mana.getWhite(), 3));
        }
        if (mana.getGreen() > 0) {
            mana.set(ManaType.GREEN, CardUtil.overflowMultiply(mana.getGreen(), 3));
        }
        if (mana.getRed() > 0) {
            mana.set(ManaType.RED, CardUtil.overflowMultiply(mana.getRed(), 3));
        }
        if (mana.getColorless() > 0) {
            mana.set(ManaType.COLORLESS, CardUtil.overflowMultiply(mana.getColorless(), 3));
        }
        return false;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.TAPPED_FOR_MANA;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Permanent permanent = game.getPermanentOrLKIBattlefield(event.getTargetId());
        return source.isControlledBy(event.getPlayerId())
                && permanent != null
                && permanent.isBasic(game)
                && permanent.isLand(game);
    }

    @Override
    public VirtueOfStrengthReplacementEffect copy() {
        return new VirtueOfStrengthReplacementEffect(this);
    }
}
