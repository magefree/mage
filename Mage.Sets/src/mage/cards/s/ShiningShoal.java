package mage.cards.s;

import mage.MageObject;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.costs.AlternativeCostSourceAbility;
import mage.abilities.costs.common.ExileFromHandCost;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.ExileFromHandCostCardConvertedMana;
import mage.abilities.effects.common.RedirectDamageFromSourceToTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.common.FilterOwnedCard;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetSource;
import mage.target.common.TargetAnyTarget;
import mage.target.common.TargetCardInHand;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class ShiningShoal extends CardImpl {

    private static final FilterOwnedCard filter
            = new FilterOwnedCard("a white card with mana value X from your hand");

    static {
        filter.add(new ColorPredicate(ObjectColor.WHITE));
    }

    public ShiningShoal(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{X}{W}{W}");
        this.subtype.add(SubType.ARCANE);

        // You may exile a white card with converted mana cost X from your hand rather than pay Shining Shoal's mana cost
        this.addAbility(new AlternativeCostSourceAbility(new ExileFromHandCost(
                new TargetCardInHand(filter), true
        )));

        // The next X damage that a source of your choice would deal to you and/or creatures you control this turn is dealt to any target instead.
        this.getSpellAbility().addEffect(new ShiningShoalRedirectDamageTargetEffect(
                Duration.EndOfTurn, ExileFromHandCostCardConvertedMana.instance
        ));
        this.getSpellAbility().addTarget(new TargetSource());
        this.getSpellAbility().addTarget(new TargetAnyTarget());
    }

    private ShiningShoal(final ShiningShoal card) {
        super(card);
    }

    @Override
    public ShiningShoal copy() {
        return new ShiningShoal(this);
    }
}

class ShiningShoalRedirectDamageTargetEffect extends RedirectDamageFromSourceToTargetEffect {

    private final DynamicValue dynamicAmount;

    public ShiningShoalRedirectDamageTargetEffect(Duration duration, DynamicValue dynamicAmount) {
        super(duration, 0, UsageType.ONE_USAGE_AT_THE_SAME_TIME);
        this.dynamicAmount = dynamicAmount;
        staticText = "The next X damage that a source of your choice would deal to you and/or creatures you control this turn is dealt to any target instead";
    }

    public ShiningShoalRedirectDamageTargetEffect(final ShiningShoalRedirectDamageTargetEffect effect) {
        super(effect);
        this.dynamicAmount = effect.dynamicAmount;
    }

    @Override
    public ShiningShoalRedirectDamageTargetEffect copy() {
        return new ShiningShoalRedirectDamageTargetEffect(this);
    }

    @Override
    public void init(Ability source, Game game) {
        amountToRedirect = dynamicAmount.calculate(game, source, this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (!this.used && event.getFlag()) {

            // get source of the damage event
            MageObject sourceObject = game.getObject(event.getSourceId());
            // get the chosen damage source
            MageObject chosenSourceObject = game.getObject(source.getFirstTarget());
            // does the source of the damage exist?
            if (sourceObject == null) {
                game.informPlayers("Couldn't find source of damage");
                return false;
            }
            // do the 2 objects match?
            if (chosenSourceObject == null || !sourceObject.getId().equals(chosenSourceObject.getId())) {
                return false;
            }

            // check target
            //   check creature first
            Permanent permanent = game.getPermanent(event.getTargetId());
            if (permanent != null && permanent.isCreature(game)) {
                if (permanent.isControlledBy(source.getControllerId())) {
                    // it's your creature
                    redirectTarget = source.getTargets().get(1);
                    return true;
                }
            }
            //   check player
            Player player = game.getPlayer(event.getTargetId());
            if (player != null) {
                if (player.getId().equals(source.getControllerId())) {
                    // it is you
                    redirectTarget = source.getTargets().get(1);
                    return true;
                }
            }
        }
        return false;
    }
}
